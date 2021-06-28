import bagel.Image;

/**
 * Zombie class, a child class of StationaryEntity, one of the characters in the game
 */
public class Zombie extends StationaryEntity {
    // the image of the zombie in the game
    private final Image image = new Image("res/images/zombie.png");
    // the reduction of energy which the zombie can bring to the player
    public static final int KILL_ENERGY = 3;

    // constructor, set position
    public Zombie(double x, double y) {
        super(x, y);
    }

    // draw zombie image
    @Override
    public void draw() {
        if (isVisible) {
            image.drawFromTopLeft(position.x, position.y);
        }
    }



}
