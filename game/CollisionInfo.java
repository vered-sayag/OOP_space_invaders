package game;


import geometry.primitives.Point;

/**
 * class of data of collision.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * contractor.
     *
     * @param collisionPoint  the point at which the collision occurs
     * @param collisionObject collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * return the point at which the collision occurs.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * return the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
