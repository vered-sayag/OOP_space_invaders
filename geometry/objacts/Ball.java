package geometry.objacts;

import biuoop.DrawSurface;

import java.awt.Color;

import geometry.primitives.Point;
import geometry.primitives.Velocity;
import geometry.primitives.Line;
import game.Sprite;
import game.GameLevel;
import game.GameEnvironment;
import game.CollisionInfo;

/**
 * Ball (actually, a circle).
 * Balls have size (radius), color, and location (a Point).
 * Balls also know how to draw themselves on a DrawSurface.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class Ball implements Sprite {

    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;
    private int itar = 0;

    /**
     * constructor.
     *
     * @param x     coordinate of the center point in axis x.
     * @param y     coordinate of the center point in axis y.
     * @param r     the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }

    /**
     * return the coordinate of the center point in axis x.
     *
     * @return type int - the coordinate of the center point in axis x.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * return the coordinate of the center point in axis y.
     *
     * @return type int - the coordinate of the center point in axis y.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * return the radius of the ball.
     *
     * @return type int - the radius of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * return the color of the ball.
     *
     * @return type int - the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * add to the ball given GameEnvironment.
     *
     * @param newGameEnvironment given GameEnvironment
     */
    public void setGameEnvironment(GameEnvironment newGameEnvironment) {
        this.gameEnvironment = newGameEnvironment;
    }

    /**
     * return the ball GameEnvironment.
     *
     * @return the ball GameEnvironment
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        if (surface != null) {
            surface.setColor(this.color);
            surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.r);
        }
    }

    /**
     * set a velocity to the ball.
     *
     * @param v velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * set a velocity to the ball.
     *
     * @param dx the change in position on the `x`.
     * @param dy the change in position on the `y`.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * return the velocity to the ball.
     *
     * @return type velocity the velocity to the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * Move one step.
     *
     * @throws Exception the exception
     */
    public void moveOneStep() throws Exception {
        itar = itar + 1;
        if (itar >= 10) {
            throw new Exception();
        }
        if (this.velocity != null) {
            // find a Collision info;
            CollisionInfo collisionInfoX;
            CollisionInfo collisionInfoY;
            CollisionInfo collisionInfo;
            double xEnd = this.center.getX() + this.velocity.getDx();
            double yEnd = this.center.getY() + this.velocity.getDy();

            if (this.velocity.getDx() > 0) {
                xEnd = xEnd + this.r;
            } else {
                xEnd = xEnd - this.r;
            }
            if (this.velocity.getDy() > 0) {
                yEnd = yEnd + this.r;
            } else {
                yEnd = yEnd - this.r;
            }

            Point endBall = new Point(xEnd, yEnd);
            Line ballLine = new Line(this.center, endBall);
            collisionInfo = this.gameEnvironment.getClosestCollision(ballLine);

            Point endXBall = new Point(xEnd, this.center.getY());
            Line ballXLine = new Line(this.center, endXBall);
            collisionInfoX = this.gameEnvironment.getClosestCollision(ballXLine);

            Point endYBall = new Point(this.center.getX(), yEnd);
            Line ballYLine = new Line(this.center, endYBall);
            collisionInfoY = this.gameEnvironment.getClosestCollision(ballYLine);

            //create a booleans.
            boolean existCollisionInfo = collisionInfo != null;
            boolean existCollisionInfoX = collisionInfoX != null;
            boolean existCollisionInfoY = collisionInfoY != null;

            boolean theBallCollis = existCollisionInfo && ((!existCollisionInfoY && !existCollisionInfoX)
                    //the Ball Collis x
                    || (!existCollisionInfoY && center.distance(collisionInfoX.collisionPoint())
                    >= new Line(center, collisionInfo.collisionPoint()).distanceX())
                    //the Ball Collis y
                    || (!existCollisionInfoX && center.distance(collisionInfoY.collisionPoint())
                    >= new Line(center, collisionInfo.collisionPoint()).distanceY())
                    //the Ball Collis x and y
                    || (existCollisionInfoX && existCollisionInfoY
                    && center.distance(collisionInfoX.collisionPoint())
                    >= new Line(center, collisionInfo.collisionPoint()).distanceX()
                    && center.distance(collisionInfoY.collisionPoint())
                    >= new Line(center, collisionInfo.collisionPoint()).distanceY()));

            // if no Collision
            if (!existCollisionInfoX && !existCollisionInfoY && !existCollisionInfo) {
                this.center = this.velocity.applyToPoint(this.center);
                //if have a Collision
            } else if (theBallCollis) {

                // chance the location to the if  collision point.
                ballLine = new Line(center, collisionInfo.collisionPoint());
                double distanceX = ballLine.distanceX();
                double distanceY = ballLine.distanceY();

                distanceX = this.distancedWithoutRadios(distanceX);
                distanceY = this.distancedWithoutRadios(distanceY);
                double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                this.center = new Point(this.center.getX() + distanceX, this.center.getY() + distanceY);

                // chance the velocity
                this.velocity = collisionInfo.collisionObject().hit(this,
                        collisionInfo.collisionPoint(), this.velocity);

                distanceX = Math.sin(Math.toRadians(this.velocity.getAngle())) * distance;
                distanceY = Math.cos(Math.toRadians(this.velocity.getAngle())) * distance;

                //chance the location to the location after the collision
                distanceX = this.chanchDistanceByVelosityX(distanceX);
                distanceY = this.chanchDistanceByVelosityY(distanceY);

                this.velocity = new Velocity(this.velocity.getDx() - distanceX, this.velocity.getDy() - distanceY);
                this.moveOneStep();

                distanceX = this.chanchDistanceByVelosityX(distanceX);
                distanceY = this.chanchDistanceByVelosityY(distanceY);

                this.velocity = new Velocity(this.velocity.getDx() + distanceX, this.velocity.getDy() + distanceY);

                //if the ball touch x
            } else if (existCollisionInfoX && (!existCollisionInfoY
                    || (this.center.distance(collisionInfoX.collisionPoint()) / this.velocity.getDx()
                    < this.center.distance(collisionInfoY.collisionPoint()) / this.velocity.getDy()))) {

                // chance the location to the if  collision point
                double distanceX = new Line(this.center, collisionInfoX.collisionPoint()).distanceX();
                double oldDx = this.velocity.getDx();
                distanceX = this.distancedWithoutRadios(distanceX);
                this.center = new Point(this.center.getX() + distanceX, this.center.getY());

                // chance the velocity
                this.velocity = collisionInfoX.collisionObject().hit(this,
                        collisionInfoX.collisionPoint(), this.velocity);

                //chance the location to the location after the collision
                distanceX = (this.velocity.getDx() / oldDx) * distanceX;
                distanceX = this.chanchDistanceByVelosityX(distanceX);

                this.velocity = new Velocity(this.velocity.getDx() - distanceX, this.velocity.getDy());
                this.moveOneStep();
                distanceX = this.chanchDistanceByVelosityX(distanceX);
                this.velocity = new Velocity(this.velocity.getDx() + distanceX, this.velocity.getDy());

                //if the ball touch y
            } else if (existCollisionInfoY && (!existCollisionInfoX
                    || this.center.distance(collisionInfoY.collisionPoint()) / this.velocity.getDy()
                    < this.center.distance(collisionInfoX.collisionPoint()) / this.velocity.getDx())) {

                // chance the location to the if  collision point
                double distanceY = new Line(this.center, collisionInfoY.collisionPoint()).distanceY();
                double oldDy = this.velocity.getDy();
                distanceY = this.distancedWithoutRadios(distanceY);
                this.center = new Point(this.center.getX(), this.center.getY() + distanceY);

                // chance the velocity
                this.velocity = collisionInfoY.collisionObject().hit(
                        this, collisionInfoY.collisionPoint(), this.velocity);

                //chance the location to the location after the collision
                distanceY = (this.velocity.getDy() / oldDy) * distanceY;
                distanceY = this.chanchDistanceByVelosityY(distanceY);

                this.velocity = new Velocity(this.velocity.getDx(), this.velocity.getDy() - distanceY);
                this.moveOneStep();

                distanceY = this.chanchDistanceByVelosityY(distanceY);

                this.velocity = new Velocity(this.velocity.getDx(), this.velocity.getDy() + distanceY);
            }
        }
        itar = 0;
    }

    /**
     * reduce the radios from the distance.
     *
     * @param distance the distance .
     * @return the distance after the reducing of the radios.
     */
    public double distancedWithoutRadios(double distance) {
        if (distance > 0) {
            distance = distance - this.r;
        } else {
            distance = distance + this.r;
        }
        return distance;
    }

    /**
     * chanch the sighn of the distans in y the ball need to move to the sighn of the velosity y.
     *
     * @param distance the distans the ball need to move in y.
     * @return the distans tin the sighn of the velosity y.
     */
    public double chanchDistanceByVelosityY(double distance) {

        if (this.velocity.getDy() > 0) {
            distance = Math.abs(distance);
        } else {
            distance = -Math.abs(distance);
        }
        return distance;
    }

    /**
     * chanch the sighn of the distans in x the ball need to move to the sighn of the velosity x.
     *
     * @param distance the distans the ball need to move in x.
     * @return the distans tin the sighn of the velosity x.
     */
    public double chanchDistanceByVelosityX(double distance) {

        if (this.velocity.getDx() > 0) {
            distance = Math.abs(distance);
        } else {
            distance = -Math.abs(distance);
        }
        return distance;
    }

    /**
     * chance thing thet need to chance in the ball when the time passed.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
        this.velocity = new Velocity(velocity.getDx() * dt, velocity.getDy() * dt);
        try {
            this.moveOneStep();
        } catch (Exception e) {
            System.out.print("");
        }

        this.velocity = new Velocity(velocity.getDx() / dt, velocity.getDy() / dt);
    }

    /**
     * add to a given game  add ass Sprite and to the list of the ball of the game.
     *
     * @param g given game
     */
    public void addToGame(GameLevel g) {
        this.setGameEnvironment(g.getEnnvironment());
        g.addSprite(this);
    }

    /**
     * remove from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

}
