import bagel.Image;

/**
 * Treasure class, a child class of StationaryEntity, one of the characters in the game
 */
public class Treasure extends StationaryEntity {
    // the image of the treasure in the game
    private final Image image = new Image("res/images/treasure.png");

    // constructor, set position
    public Treasure(double x, double y) {
        super(x, y);
    }

    // draw treasure image
    @Override
    public void draw() {
        image.drawFromTopLeft(position.x, position.y);
    }

}
