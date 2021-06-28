import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import java.util.HashMap;

/**
 * Player class, a child class of MovingEntity, one of the characters in the game
 * The player can interact with zombies, sandwiches and treasure.
 */
public class Player extends MovingEntity {
    // the image of the player in the game
    private final Image image = new Image("res/images/player.png");
    // moving speed
    public static final int STEP_SIZE = 10;
    // energy level threshold
    public static final int LOW_ENERGY = 3;

    private int energy;
    private boolean shot;
    private Bullet bullet;

    // the font of the energy level characters
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

    // constructor, set position and energy level, initialize boolean var shot
    public Player(double x, double y, int energy){
        super(x, y);
        this.energy = energy;
        this.shot = false;
    }

    /**
     * getter of energy
     * @return int, current energy level of player
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * getter of shot
     * @return boolean, true if player has shot the bullet, false if not
     */
    public boolean getShot() {
        return shot;
    }

    /**
     * getter of bullet
     * @return bullet, which is of type Bullet
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * setter of shot
     * @param shot, which is a boolean var
     */
    public void setShot(boolean shot) {
        this.shot = shot;
    }


    // draw the image and the energy level of the player
    @Override
    public void draw() {
        image.drawFromTopLeft(position.x, position.y);
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }


    /**
     * check if player has met another entity
     * @param entityPos, entity's position, which is of type Point
     * @return boolean, true if they met
     */
    @Override
    public boolean meets(Point entityPos) {
        boolean hasMet = false;
        double distanceToPlayer = position.distanceTo(entityPos);
        if (distanceToPlayer < ShadowTreasure.MEETING_DISTANCE) {
            hasMet = true;
        }
        return hasMet;
    }

    // update energy level when player interacts with a zombie or a sandwich
    @Override
    public void meetZombie() {
        energy -= Zombie.KILL_ENERGY;
    }
    public void eatSandwich() {
        energy += Sandwich.GIVE_ENERGY;
    }

    /**
     * check if player is within the shot range
     * @param target, the shooting target, which is of type Point
     * @return boolean, true if player is allowed to shoot
     */
    public boolean inShootingRange(Point target) {
        if (this.position.distanceTo(target) < ShadowTreasure.SHOT_RANGE) {
            return true;
        } else {
            return false;
        }
    }

    // shoot the bullet to zombie
    public void shoot() {
        energy -= Bullet.SHOT_ENERGY;
        this.shot = true;
    }


    /**
     * Generic method to find the closest zombie or sandwich
     * @param objectHashMap either zombies or sandwiches, which is of type HashMap
     * @param <T> either Zombie or Sandwich, which is a reference type
     * @return a data type, same as param T, either Zombie or Sandwich
     */
    public <T> T findClosestZombieOrSandwich(HashMap<Point, T> objectHashMap) {
        if (objectHashMap == null || objectHashMap.isEmpty()) {
            return null;
        }

        // a hashmap to store minimum distance and object position (only one pair)
        HashMap<Double, Point> minDistance = new HashMap<>();

        // set the distance to the first object in the objectHashMap as min distance, store into minDistance
        Point position = (Point) objectHashMap.keySet().toArray()[0];
        double minD = this.getPosition().distanceTo(position);
        minDistance.put(minD, position);

        // find the minimum distance, replace the one in the minDistance
        for (Point pos: objectHashMap.keySet()) {
            double distance = this.getPosition().distanceTo(pos);
            if (distance < minD) {
                minDistance.remove(minD);
                minD = distance;
                minDistance.put(minD, pos);
            }
        }
        Point closest = minDistance.get(minD);

        return objectHashMap.get(closest);
    }


    /**
     * player state update
     * @param tomb, which is of type ShadowTreasure
     */
    @Override
    public void update(ShadowTreasure tomb) {
        // entities the player may interact with
        HashMap<Point, Sandwich> sandwiches = tomb.getSandwiches();
        HashMap<Point, Zombie> zombies = tomb.getZombies();
        Treasure treasure = tomb.getTreasure();

        // the closest sandwich/zombie for player
        Sandwich closestSandwich = this.findClosestZombieOrSandwich(sandwiches);;
        Zombie closestZombie = this.findClosestZombieOrSandwich(zombies);


        // terminate the game if player reached the treasure when there is no zombie in the game
        if (this.meets(treasure.getPosition()) && zombies.isEmpty()) {
            System.out.println(this.getEnergy() + ",success!" );
            tomb.setEndOfGame(true);

            // terminate the game if player's energy level is less
            // than LOW_ENERGY when there are zombies but no sandwiches
        } else if (!shot && this.energy < LOW_ENERGY && sandwiches.isEmpty() && !zombies.isEmpty()) {
            System.out.println(this.getEnergy());
            tomb.setEndOfGame(true);

        } else {
            // the player interacts with entities

            // in the case when player can go for sandwiches
            if (!sandwiches.isEmpty()) {
                Point sandwichPos = closestSandwich.getPosition();
                if (this.meets(sandwichPos)) {
                    // add energy
                    this.eatSandwich();
                    // make the sandwich invisible
                    closestSandwich.setVisible(false);
                    // remove the sandwich from the game
                    sandwiches.remove(sandwichPos);
                }
            }

            // in the case when player can go for zombies
            if (!zombies.isEmpty()) {
                Point zombiePos = closestZombie.getPosition();

                // shoot when the zombie is within the shot range
                if (!shot && this.inShootingRange(zombiePos)) {
                    // shoot the bullet, consume energy
                    this.shoot();
                    this.bullet = new Bullet(this.getPosition(), closestZombie);
                }

                // if he bullet has been shot, update its state
                if (shot) {
                    bullet.update(tomb);
                }
            }

            // set player's moving direction
            if (zombies.isEmpty()) {
                this.pointTo(treasure.getPosition());
            } else if (this.energy >= LOW_ENERGY && !zombies.isEmpty()) {
                this.pointTo(closestZombie.getPosition());
            } else if (!sandwiches.isEmpty()) {
                this.pointTo(closestSandwich.getPosition());
            }
            // move one step
            this.position = new Point(this.position.x + STEP_SIZE * this.directionX,
                                      this.position.y + STEP_SIZE * this.directionY);

        }



    }


}
