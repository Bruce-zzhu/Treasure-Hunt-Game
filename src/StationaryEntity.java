import bagel.util.Point;

/**
 * parent class of stationary entities including zombie, sandwich and treasure
 */
public abstract class StationaryEntity {
    protected Point position;
    protected boolean isVisible;

    // constructor, set position and visibility
    public StationaryEntity(double x, double y){
        this.isVisible = true;
        this.position = new Point(x, y);
    }

    /**
     * getter of position
     * @return position, of type Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * update visibility
     * @param visible, of type boolean
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    // draw the image of the character
    public abstract void draw();


}
