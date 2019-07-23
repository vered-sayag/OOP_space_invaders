package geometry.objacts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import game.Sprite;
import game.Collidable;
import game.SpriteCollection;
import game.GameEnvironment;
import game.GameLevel;
import game.lisiners.HitListener;
import game.lisiners.HitNotifier;
import geometry.primitives.Velocity;
import geometry.primitives.Rectangle;
import geometry.primitives.Point;

/**
 * Paddle control by the user.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class Paddle implements Sprite, Collidable, HitNotifier {

    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private Frame frame;
    private int velocity;
    private SpriteCollection spriteCollection;
    private GameEnvironment gameEnvironment;
    private long time = System.currentTimeMillis();

    /**
     * constractor.
     *
     * @param upperLeft        the point upp Left  of the paddle
     * @param frame            the frame the paddle live into
     * @param velocity         the velocity of the paddle
     * @param spriteCollection the sprite collection
     * @param gameEnvironment  the game environment
     */
    public Paddle(Point upperLeft, Frame frame, int velocity,
                  SpriteCollection spriteCollection, GameEnvironment gameEnvironment) {
        this.rectangle = new Rectangle(upperLeft, 91, 110);
        this.rectangle.setImage("block_images/spaceship.jpg");
        this.keyboard = frame.getGUI().getKeyboardSensor();
        this.frame = frame;
        this.velocity = velocity;
        this.spriteCollection = spriteCollection;
        this.gameEnvironment = gameEnvironment;

    }

    /**
     * make the paddle move left.
     *
     * @param dt the dt
     */
    public void moveLeft(double dt) {

        this.rectangle.setUpperLeft(new Point(
                this.rectangle.getUpperLeft().getX() - velocity * dt, this.rectangle.getUpperLeft().getY()));

    }

    /**
     * make the paddle move right.
     *
     * @param dt the dt
     */
    public void moveRight(double dt) {

        this.rectangle.setUpperLeft(new Point(this.rectangle.getUpperLeft().getX()
                + velocity * dt, this.rectangle.getUpperLeft().getY()));

    }

    /**
     * Shoot.
     */
    public void shoot() {
        time = System.currentTimeMillis();
        Ball ball = new Ball(rectangle.getUpperLeft().getX(),
                rectangle.getUpperLeft().getY() - 1, 2, Color.red);
        ball.setVelocity(Velocity.fromAngleAndSpeed(180, 600));
        ball.setGameEnvironment(gameEnvironment);
        spriteCollection.addSprite(ball);

        ball = new Ball(rectangle.getUpperLeft().getX() + rectangle.getWidth(),
                rectangle.getUpperLeft().getY() - 1, 2, Color.red);
        ball.setVelocity(Velocity.fromAngleAndSpeed(180, 600));
        ball.setGameEnvironment(gameEnvironment);
        spriteCollection.addSprite(ball);

    }

    // Sprite

    /**
     * move the paddle.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {


        if (keyboard.isPressed("left") && this.rectangle.getUpperLeft().getX()
                >= this.frame.getRectangle().getUpperLeft().getX() + velocity * dt) {
            moveLeft(dt);
        }
        if (keyboard.isPressed("right") && this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()
                <= this.frame.getRectangle().getUpperLeft().getX() + this.frame.getRectangle().getWidth()
                - velocity * dt) {
            moveRight(dt);
        }
        if (keyboard.isPressed("space") && System.currentTimeMillis() - time > 350) {
            shoot();
        }

    }

    /**
     * draw the paddle on the given DrawSurface.
     *
     * @param d given DrawSurface
     */
    public void drawOn(DrawSurface d) {
        this.rectangle.drwoRectangle(d);
    }


    /**
     * return the rectangle of this paddle.
     *
     * @return the rectangle of this paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * when a hit happened move the given velocity.
     *
     * @param hitter          the ball
     * @param collisionPoint  collision Point - data on the hit
     * @param currentVelocity current Velocity - the given velocity.
     * @return a new velocity after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null || currentVelocity == null) {
            return null;
        }

        this.notifyHit(hitter);
        if (collisionPoint.getX() == this.rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        // chance dy
        if ((collisionPoint.getY() == this.rectangle.getUpperLeft().getY())) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        // chance the angle of the spide according to  the hit point
        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY()) {
            if (collisionPoint.getX() >= this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() * 4 / 5) {
                currentVelocity = Velocity.fromAngleAndSpeed(120, currentVelocity.getSize());
            }
            if ((collisionPoint.getX() <= this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() / 5)) {
                currentVelocity = Velocity.fromAngleAndSpeed(240, currentVelocity.getSize());
            }
            if (collisionPoint.getX() > this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() * 3 / 5
                    && collisionPoint.getX() < this.rectangle.getUpperLeft().getX()
                    + this.rectangle.getWidth() * 4 / 5) {
                currentVelocity = Velocity.fromAngleAndSpeed(150, currentVelocity.getSize());
            }
            if ((collisionPoint.getX() > this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() / 5
                    && collisionPoint.getX() < this.rectangle.getUpperLeft().getX()
                    + this.rectangle.getWidth() * 2 / 5)) {
                currentVelocity = Velocity.fromAngleAndSpeed(210, currentVelocity.getSize());
            }
        }
        return currentVelocity;

    }


    /**
     * Add this paddle to the game.
     *
     * @param g given game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * set location.
     *
     * @param location point up left.
     */
    public void setLocation(Point location) {
        this.rectangle.setUpperLeft(location);
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