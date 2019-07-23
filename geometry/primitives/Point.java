package geometry.primitives;
/**
 * point class, A point has an x and a y value,
 * and can measure the distance to other points, and if its is equal to another point.
 *
 * @version 26.03.2018
 * @author Vered Sayag
 */
public class Point {

    private double x;
    private double y;

    /**
     * constructor of point.
     *
     * @param x     the coordinate of the point in axis x.
     * @param y     the coordinate of the point in axis y.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance - return the distance of this point to the other point.
     *
     * @param other     other point.
     * @return      double typ - he distance of this point to the other point.
     */
    public double distance(Point other) {
       if (other != null) {
           return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
       } else {
           return -1;
       }
    }

    /**
     * equals - return true is the points are equal, false otherwise.
     *
     * @param other   other point to check.
     * @return          type boolean - return true is the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        if (other != null) {
            return (this.x == other.getX() && this.y == other.getY());
        }
        return false;
    }

    /**
     * getX - Return the value of x.
     *
     * @return      type double - Return the value of x.
     */
    public double getX() {
        return this.x;
    }

    /**
     * getY - Return the value of y.
     *
     * @return      type double - Return the value of y.
     */
    public double getY() {
        return this.y;
    }
}