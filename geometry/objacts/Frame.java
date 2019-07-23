package geometry.objacts;

import biuoop.DrawSurface;
import biuoop.GUI;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


import game.Collidable;
import game.GameLevel;
import game.Sprite;
import game.lisiners.HitNotifier;
import geometry.primitives.Velocity;
import geometry.primitives.Rectangle;
import geometry.primitives.Point;
import game.lisiners.HitListener;

/**
 * class of frame that can collision white a ball an draw on surface .
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class Frame implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private GUI gui;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();


    /**
     * constructor.
     *
     * @param width  the width of the frame
     * @param height the height of the frame
     */
    public Frame(double width, double height) {
        this.rectangle = new Rectangle(new Point(0, 0), width, height);
        this.gui = new GUI("VeredGame", (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    /**
     * constructor.
     *
     * @param width  the width of the frame
     * @param height the height of the frame
     * @param color  the color of the frame
     */
    public Frame(double width, double height, Color color) {
        this.rectangle = new Rectangle(new Point(0, 0), width, height);
        this.gui = new GUI("VeredGame", (int) width, (int) height);
        this.rectangle.setColor(color);

    }

    /**
     * return the rectangle of the frame.
     *
     * @return the rectangle of the frame
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * chance the velocity according to hit.
     *
     * @param hitter          the ball
     * @param collisionPoint  collision Point
     * @param currentVelocity current Velocity
     * @return the velocity after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        if (collisionPoint == null || currentVelocity == null) {
            return null;
        }
        notifyHit(hitter);

        if ((collisionPoint.getX() == this.rectangle.getUpperLeft().getX() && currentVelocity.getDx() < 0)
                || (collisionPoint.getX() == this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()
                && currentVelocity.getDx() > 0)) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY() && currentVelocity.getDy() < 0) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());

        }

        return currentVelocity;
    }

    /**
     * return the gui of the frame.
     *
     * @return the gui of the frame
     */
    public GUI getGUI() {
        return this.gui;
    }

    /**
     * return the rectangle.
     *
     * @return the rectangle.
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * draw the block on  given draw surface.
     *
     * @param d given draw surface.
     */
    public void drawOn(DrawSurface d) {
        this.rectangle.drwoRectangle(d);
    }

    /**
     * chance the color of the frame.
     *
     * @param color a color to the frame
     */
    public void setColorFrame(Color color) {
        this.rectangle.setColor(color);
    }

    /**
     * nothing.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }

    /**
     * add the frame to the Sprites and to the game environment.
     *
     * @param g the game to add the frame in
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * check if a ball in the frame.
     *
     * @param ball ball to check
     * @return a boolean - if a ball in the frame
     */
    public boolean isBallHere(Ball ball) {

        return (this.rectangle.getUpperLeft().getX() < ball.getX() + ball.getSize() && ball.getX()
                - ball.getSize() < this.rectangle.getUpperLeft().getX()
                + this.rectangle.getWidth() && this.rectangle.getUpperLeft().getY() < ball.getY()
                + ball.getSize() && ball.getY() - ball.getSize()
                < this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight());
    }

    /**
     * Add hit listener.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hit listener.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

    /**
     * remove all liseners.
     */
    public void removeAllHitListener() {
        this.hitListeners.clear();

    }

    /**
     * notify hit.
     *
     * @param hitter ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * get hit num.
     *
     * @return Hit Num
     */
    public int getHitNum() {
        return -1;
    }


    /**
     * remove from Game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
    }
}
