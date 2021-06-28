import bagel.util.Point;

/**
 * parent class of moving entities including player and bullet
 */
public abstract class MovingEntity {
    protected Point position;
    protected double directionX;
    protected double directionY;

    // constructor, set position and initialize direction
    public MovingEntity(double x, double y){
        this.position = new Point(x, y);
        this.directionX = 0;
        this.directionY = 0;
    }

    /**
     * getter of position
     * @return position, which is of type Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * set direction
     * @param dest, the destination where the entity points to, which is of type Point
     */
    public void pointTo(Point dest) {
        this.directionX = dest.x-this.position.x;
        this.directionY = dest.y-this.position.y;
        normalizeD();
    }

    // normalize direction
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    // check if the entity has met another entity
    public abstract boolean meets(Point entityPos);

    // update visibility of the entity when it meets zombie
    public abstract void meetZombie();

    // draw the image of the entity
    public abstract void draw();

    // Performs a state update
    public abstract void update(ShadowTreasure tomb);

}
