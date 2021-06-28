import bagel.Image;

/**
 * Treasure class, a child class of StationaryEntity, one of the characters in the game
 */
public class Sandwich extends StationaryEntity {
    // the image of the zombie in the game
    private final Image image = new Image("res/images/sandwich.png");
    // the amount of energy which the sandwich can give to the player
    public static final int GIVE_ENERGY = 5;

    // constructor, set position
    public Sandwich(double x, double y) {
        super(x, y);
    }

    // draw sandwich image
    @Override
    public void draw() {
        if (isVisible) {
            image.drawFromTopLeft(position.x, position.y);
        }
    }


}
