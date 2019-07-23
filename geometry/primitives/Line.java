package geometry.primitives;
import java.util.List;

/**
 * A line (actually a line-segment) connects two points -- a start point and an end point.
 * Lines have lengths, and may intersect with other lines. It can also tell if it is the same as another line segment.
 *
 * @author Vered Sayag
 * @version 26.03.2018
 */
public class Line {

    private Point start;
    private Point end;

    /**
     * constructor of line.
     *
     * @param start start point of the line.
     * @param end   end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructor of line.
     *
     * @param x1 the coordinate of the start point in axis x.
     * @param y1 the coordinate of the start point in axis y.
     * @param x2 the coordinate of the end point in axis x.
     * @param y2 the coordinate of the end point in axis y.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * length - calculate the length of the line.
     *
     * @return type double - return the length of the line.
     */
    public double length() {
        if (this.start != null && this.end != null) {
            return this.start.distance(this.end);
        }
        return -1;
    }

    /**
     * distance in x from the start to end .
     *
     * @return distance in x from the start to end
     */
    public double distanceX() {
        return this.end.getX() - this.start.getX();
    }

    /**
     * distance in y from the start to end .
     *
     * @return distance in y from the start to end
     */
    public double distanceY() {
        return this.end.getY() - this.start.getY();
    }


    /**
     * middle - calculate the middle point of the line.
     *
     * @return type point - returns the middle point of the line.
     */
    public Point middle() {
        if (this.start != null && this.end != null) {
            double midX = Math.abs(this.start.getX() + this.end.getX()) / 2;
            double midY = Math.abs(this.start.getY() + this.end.getY()) / 2;
            Point middlePoint = new Point(midX, midY);
            return middlePoint;
        } else {
            return null;
        }
    }

    /**
     * start - Returns the start point of the line.
     *
     * @return type point- the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * end - Returns the end point of the line.
     *
     * @return type point- the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * isIntersecting - check if the line intersect with other line.
     *
     * @param other other line to check
     * @return type boolean - true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (other != null && other.start() != null && other.end() != null && this.start != null && this.end != null) {

            double intersectionX;
            double intersectionY;
            if (this.start.getX() == this.end.getX() && other.start().getX() == other.end().getX()) {
                return false;
            }

            if (this.start.getX() == this.end.getX()) {
                double m2 = other.incline();
                double b2 = other.intersectionY();
                intersectionX = this.start.getX();
                intersectionY = (intersectionX * m2) + b2;

            } else if (other.start().getX() == other.end().getX()) {
                double m1 = this.incline();
                double b1 = this.intersectionY();
                intersectionX = other.start().getX();
                intersectionY = (intersectionX * m1) + b1;
            } else {
                double m1 = this.incline();
                double m2 = other.incline();

                if (m1 == m2) {
                    return false;
                }

                double b1 = this.intersectionY();
                double b2 = other.intersectionY();

                intersectionX = (b1 - b2) / (m2 - m1);
                intersectionY = (intersectionX * m1) + b1;
            }
            return (Math.max(this.start.getX(), this.end.getX()) >= intersectionX
                    && Math.min(this.start.getX(), this.end.getX()) <= intersectionX
                    && Math.max(this.start.getY(), this.end.getY()) >= intersectionY
                    && Math.min(this.start.getY(), this.end.getY()) <= intersectionY
                    && Math.max(other.start().getX(), other.end().getX()) >= intersectionX
                    && Math.min(other.start().getX(), other.end().getX()) <= intersectionX
                    && Math.max(other.start().getY(), other.end().getY()) >= intersectionY
                    && Math.min(other.start().getY(), other.end().getY()) <= intersectionY);
        }
        return false;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.

    /**
     * Returns the intersection point if the lines intersect,and null otherwise.
     *
     * @param other other line to find the intersection point with.
     * @return type point the intersection point between the lines.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            double intersectionX;
            double intersectionY;
            if (this.start.getX() == this.end.getX()) {
                double m2 = other.incline();
                double b2 = other.intersectionY();
                intersectionX = this.start.getX();
                intersectionY = (intersectionX * m2) + b2;

            } else if (other.start().getX() == other.end().getX()) {
                double m1 = this.incline();
                double b1 = this.intersectionY();
                intersectionX = other.start().getX();
                intersectionY = (intersectionX * m1) + b1;
            } else {
                double m1 = this.incline();
                double m2 = other.incline();
                double b1 = this.intersectionY();
                double b2 = other.intersectionY();

                intersectionX = (b1 - b2) / (m2 - m1);
                intersectionY = intersectionX * m1 + b1;
            }
            return new Point(intersectionX, intersectionY);
        }
        return null;
    }

    /**
     * incline - calculate the incline of the line.
     *
     * @return type double - the incline of the line.
     */
    public double incline() {
        return (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
    }

    /**
     * find The intersection point with the y-axis for line withe incline.
     *
     * @return type double - the coordinate of the intersection point in axis y
     */
    public double intersectionY() {
        return this.start.getY() - (this.start.getX() * this.incline());
    }

    /**
     * equals - return true is the lines are equal, false otherwise.
     *
     * @param other other line to check
     * @return return true is the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (other != null && other.start() != null && other.end() != null && this.start != null && this.end != null) {
            return ((this.start.equals(other.start()) && this.end.equals(other.end()))
                    || (this.start.equals(other.end()) && this.end.equals(other.start())));
        }
        return false;
    }

    /**
     * find the most close intersection point to the start of the line to a given rectangle.
     *
     * @param rect      given rectangle
     * @return  the most close intersection point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point tempPoint;
        Point closesPoint;
        List<Point> intersection = rect.intersectionPoints(this);
        if (intersection == null || intersection.isEmpty()) {
            return null;
        }
        closesPoint = intersection.get(0);

        for (int i = 0; i < intersection.size(); i++) {
            tempPoint = intersection.get(i);
            if (tempPoint.distance(this.start()) < closesPoint.distance(this.start())) {
                closesPoint = tempPoint;
            }

        }
        return closesPoint;

    }
}
