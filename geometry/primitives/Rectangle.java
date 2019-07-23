package geometry.primitives;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Rectangle.
 *
 * @author Vered Sayag
 * @version 09.04.2018
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Color color;
    private Color stroke = null;
    private BufferedImage image = null;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper-left point of the rectangle.
     * @param width     width of the rectangle.
     * @param height    height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }


    /**
     * set color to the frame.
     *
     * @param colorFrame color to the frame.
     */
    public void setColor(Color colorFrame) {
        this.color = colorFrame;
    }

    /**
     * Sets stroke.
     *
     * @param colorStroke the color stroke
     */
    public void setStroke(Color colorStroke) {
        this.stroke = colorStroke;
    }

    /**
     * chance the location of Up per Left.
     *
     * @param point new point to Up per Left
     */
    public void setUpperLeft(Point point) {
        this.upperLeft = point;
    }


    /**
     * Sets image.
     *
     * @param name the name
     */
    public void setImage(String name) {

        try {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(name));
        } catch (Exception e) {
            System.out.print("");
        }
    }

    /**
     * draw the frame on given surface.
     *
     * @param surface given surface.
     */
    public void drwoRectangle(DrawSurface surface) {


        if (this.color != null) {
            surface.setColor(this.color);
            surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(), (int) this.width,
                    (int) this.height);
        }

        if (this.image != null) {
            surface.drawImage((int) upperLeft.getX(), (int) upperLeft.getY(), this.image);
        }
        if (this.stroke != null) {
            surface.setColor(stroke);
            surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(), (int) this.width,
                    (int) this.height);
        }
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line line to chack intersection Points withe
     * @return List of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersection = new ArrayList<Point>();

        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point downperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        Point downperLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        if (line.isIntersecting(new Line(upperRight, this.upperLeft))) {
            intersection.add(line.intersectionWith(new Line(upperRight, this.upperLeft)));
        }
        if (line.isIntersecting(new Line(downperRight, downperLeft))) {
            intersection.add(line.intersectionWith(new Line(downperRight, downperLeft)));
        }
        if (line.isIntersecting(new Line(downperLeft, this.upperLeft))) {
            intersection.add(line.intersectionWith(new Line(downperLeft, this.upperLeft)));
        }
        if (line.isIntersecting(new Line(downperRight, upperRight))) {
            intersection.add(line.intersectionWith(new Line(downperRight, upperRight)));
        }

        return intersection;
    }

    /**
     * Return the width of the rectangle.
     *
     * @return type double - width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return type double - height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return type point- the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}