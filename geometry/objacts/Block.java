package geometry.objacts;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import geometry.primitives.Point;
import geometry.primitives.Velocity;
import geometry.primitives.Rectangle;
import game.Sprite;
import game.Collidable;
import game.GameLevel;
import game.lisiners.HitNotifier;
import game.lisiners.HitListener;


/**
 * class of black that can collision white a ball an draw on surface .
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private int hitNum;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Map<Integer, Rectangle> hitMapRec = new HashMap<Integer, Rectangle>();
    private Color stroke = null;
    private String image = null;
    private Color fill = null;
    private Map<Integer, Color> saveHitMapColor;
    private Map<Integer, String> saveHitMapImage;


    /**
     * constructor.
     *
     * @param rectangle a rectangle that will be the block.
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
    }


    /**
     * Instantiates a new Block.
     *
     * @param rectangle the rectangle
     * @param color     the color
     * @param stroke    the stroke
     */
    public Block(Rectangle rectangle, Color color, Color stroke) {
        this.rectangle = rectangle;
        this.rectangle.setStroke(stroke);
        this.rectangle.setColor(color);
        fill = color;
    }


    /**
     * constructor.
     *
     * @param upperLeft start point
     * @param width     width block
     * @param height    height block
     */
    public Block(Point upperLeft, double width, double height) {
        this.rectangle = new Rectangle(upperLeft, width, height);
    }


    /**
     * constructor.
     *
     * @param upperLeft start point
     * @param width     width block
     * @param height    height block
     * @param color     the color for the block.
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.rectangle.setColor(color);
        fill = color;
    }

    /**
     * Instantiates a new Block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @param color     the color
     * @param stroke    the stroke
     */
    public Block(Point upperLeft, double width, double height, Color color, Color stroke) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.rectangle.setColor(color);
        this.rectangle.setStroke(stroke);
        fill = color;
    }

    /**
     * constructor.
     *
     * @param upperLeft start point
     * @param width     width block
     * @param height    height block
     * @param color     the color for the block.
     * @param hitNum    the number of the hits the black have before despair .
     */
    public Block(Point upperLeft, double width, double height, Color color, int hitNum) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.rectangle.setColor(color);
        this.hitNum = hitNum;
        fill = color;

    }


    /**
     * Instantiates a new Block.
     *
     * @param upperLeft   the upper left
     * @param width       the width
     * @param height      the height
     * @param color       the color
     * @param colorStroke the color stroke
     * @param hitNum      the hit num
     */
    public Block(Point upperLeft, double width, double height, Color color, Color colorStroke, int hitNum) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.rectangle.setColor(color);
        this.rectangle.setStroke(colorStroke);
        this.hitNum = hitNum;
        fill = color;

    }

    /**
     * Sets image.
     *
     * @param name the name
     */
    public void setImage(String name) {
        rectangle.setImage(name);
        image = name;
    }


    /**
     * Sets stroke.
     *
     * @param colorStroke the color stroke
     */
    public void setStroke(Color colorStroke) {
        rectangle.setStroke(colorStroke);
        stroke = colorStroke;


    }

    /**
     * return the rectangle of the block.
     *
     * @return the rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * chance the velocity according to hit and chance the numbers of the hits black.
     *
     * @param hitter          the ball
     * @param collisionPoint  collision Point
     * @param currentVelocity current Velocity to chance
     * @return the new Velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        if (collisionPoint == null || currentVelocity == null) {
            return null;
        }

        //change the numbers of the hits black
        if (this.hitNum > 0) {
            this.hitNum = this.hitNum - 1;
        }


        this.notifyHit(hitter);

        // change dx
        if ((collisionPoint.getX() == this.rectangle.getUpperLeft().getX())
                || (collisionPoint.getX() == this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()
        )) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        // change dy
        if ((collisionPoint.getY() == this.rectangle.getUpperLeft().getY())
                || (collisionPoint.getY() == this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight()
        )) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        return currentVelocity;
    }

    /**
     * draw the block on  given draw surface.
     *
     * @param d given draw surface.
     */
    public void drawOn(DrawSurface d) {
        if (hitMapRec.containsKey(hitNum)) {
            hitMapRec.get(hitNum).drwoRectangle(d);
        } else {
            rectangle.drwoRectangle(d);
        }

    }

    /**
     * do nothing.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {


    }

    /**
     * Gets rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * add the block to the Sprites and to the game environment.
     *
     * @param g the game to add the block in
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * remove from Game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
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
        return hitNum;
    }


    /**
     * Sets hit map color.
     *
     * @param hitMapColor the hit map color
     */
    public void setHitMapColor(Map<Integer, Color> hitMapColor) {
        for (Integer key : hitMapColor.keySet()) {
            Rectangle newRectangle = new Rectangle(rectangle.getUpperLeft(),
                    rectangle.getWidth(), rectangle.getHeight());
            newRectangle.setColor(hitMapColor.get(key));
            newRectangle.setStroke(stroke);
            hitMapRec.put(key, newRectangle);
        }
        saveHitMapColor = hitMapColor;
    }

    /**
     * Sets hit map image.
     *
     * @param hitMapImage the hit map image
     */
    public void setHitMapImage(Map<Integer, String> hitMapImage) {
        for (Integer key : hitMapImage.keySet()) {
            Rectangle newRectangle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(),
                    rectangle.getHeight());
            newRectangle.setImage(hitMapImage.get(key));
            newRectangle.setStroke(stroke);
            hitMapRec.put(key, newRectangle);
        }
        saveHitMapImage = hitMapImage;
    }

    /**
     * Copy block.
     *
     * @return the block
     */
    public Block copy() {
        Block block = new Block(rectangle.getUpperLeft(), rectangle.getWidth(),
                rectangle.getHeight(), fill, hitNum);
        block.setImage(image);
        block.setStroke(stroke);
        block.setHitMapColor(saveHitMapColor);
        block.setHitMapImage(saveHitMapImage);
        return block;
    }
}


