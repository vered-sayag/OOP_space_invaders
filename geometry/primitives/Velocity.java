package geometry.primitives;
/**
 *Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @version 15.03.2018
 * @author Vered Sayag
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * constructor.
     *
     * @param dx    the change in position on the `x`.
     * @param dy    the change in position on the `y`.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     *constructor.
     *
     * @param angle     angle in degrees of the velocity.
     * @param speed     the speed in degrees of the velocity.
     * @return          type velocity - new velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * return the change in position on the `x`.
     *
     * @return      the change in position on the `x`.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * return the change in position on the `y`.
     *
     * @return      he change in position on the `y`.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p     the point to change this position .
     * @return      type point - apoint in the new position.
     */
    public Point applyToPoint(Point p) {
        if (p != null) {
            return new Point(p.getX() + this.dx, p.getY() + this.dy);
        } else {
            return null;
        }
    }

    /**
     * return the size of this velocity.
     *
     * @return  the size of this velocity.
     */
    public Double getSize() {
        return Math.sqrt(Math.pow(this.getDx(), 2) + Math.pow(this.getDy(), 2));
    }

    /**
     * return the angle of this velocity.
     *
     * @return  the angle of this velocity.
     */
    public Double getAngle() {
        return Math.toDegrees(Math.atan(this.getDx() / this.getDy()));
    }
}
