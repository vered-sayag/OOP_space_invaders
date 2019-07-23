package game;

import geometry.objacts.Ball;
import geometry.primitives.Rectangle;
import geometry.primitives.Point;
import geometry.primitives.Velocity;

/**
 * Collidable.
 *
 * @author Vered Sayag
 * @version 09.04.2018
 */
public interface Collidable {
    /**
     * return the rectangle of the Collision.
     *
     * @return the rectangle of the Collision.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the hitter
     * @param collisionPoint  collision Point
     * @param currentVelocity current Velocity
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}
