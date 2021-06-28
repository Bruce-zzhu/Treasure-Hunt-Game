import bagel.Image;
import bagel.util.Point;

/**
 * Bullet class, a child class of MovingEntity
 */
public class Bullet extends MovingEntity {
    // the image of the bullet in the game
    private final Image image = new Image("res/images/shot.png");
    // moving speed
    public static final int STEP_SIZE = 25;
    // the reduction of energy which the bullet can bring to the player
    public static final int SHOT_ENERGY = 3;
    // visibility
    private boolean isVisible;
    // object that the bullet is moving to
    private Zombie objectZombie;

    // constructor, set position and initialize the visibility and shooting object
    // print the initial position of bullet
    public Bullet(Point position, Zombie objectZombie) {
        super(position.x, position.y);
        this.isVisible = true;
        this.objectZombie = objectZombie;
        // print bullet's position to output.csv
        ShadowTreasure.printBulletPos(this.position.x, this.position.y);
    }

    // draw the image of the bullet
    @Override
    public void draw() {
        if (isVisible) {
            image.drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * check if bullet has met another entity
     * @param entityPos, the entity's position, which is of type Point
     * @return boolean, true if they met
     */
    @Override
    public boolean meets(Point entityPos) {
        boolean hasMet = false;
        double distanceToPlayer = position.distanceTo(entityPos);
        if (distanceToPlayer < ShadowTreasure.HIT_RANGE) {
            hasMet = true;
        }
        return hasMet;
    }

    // update the visibility of bullet
    @Override
    public void meetZombie() {
        isVisible = false;
    }


    /**
     * bullet state update
     * @param tomb, which is of type ShadowTreasure
     */
    @Override
    public void update(ShadowTreasure tomb) {
        Player player = tomb.getPlayer();
        Point zombiePos = objectZombie.getPosition();

        // if bullet reaches target, update visibility of the bullet and the target
        if (this.meets(zombiePos)) {
            // make zombie and bullet invisible
            objectZombie.setVisible(false);
            this.meetZombie();
            tomb.getZombies().remove(zombiePos);
            // player finished shooting
            player.setShot(false);
        } else {
            // set direction
            this.pointTo(zombiePos);
            // move one step
            this.position = new Point(this.position.x + STEP_SIZE * directionX,
                                      this.position.y + STEP_SIZE * directionY);
            // print bullet's position to output.csv
            ShadowTreasure.printBulletPos(position.x, position.y);
        }


    }


}
