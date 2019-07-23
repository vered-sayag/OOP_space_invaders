package game;

import java.util.ArrayList;
import java.util.List;

import geometry.primitives.Line;
import geometry.primitives.Point;
import geometry.objacts.Paddle;

/**
 * GameEnvironment - list of Collidable objacts
 * find the most closest collision that is going to occur.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class GameEnvironment {

    private List<Collidable> collidables = new ArrayList<Collidable>();


    /**
     * add the given collidable to the environment.
     *
     * @param c collidable to addto the environment
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * remove Collidable.
     *
     * @param c Collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the line of the object moving
     * @return return the information about the closest collision that is going to occur
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (trajectory == null) {
            return null;
        }
        List<Collidable> collidablesTemp = new ArrayList<Collidable>(this.collidables);
        Point temp;
        double minD = trajectory.length();
        Collidable closestCollision = null;
        Point closestPoint = null;
        for (int i = 0; i < collidablesTemp.size(); i++) {
            temp = trajectory.closestIntersectionToStartOfLine(collidablesTemp.get(i).getCollisionRectangle());

            if (temp != null && ((collidablesTemp.get(i) instanceof Paddle) && temp.getY()
                    == collidablesTemp.get(i).getCollisionRectangle().getUpperLeft().getY()
                    + collidablesTemp.get(i).getCollisionRectangle().getHeight())) {
                continue;
            }

            // find the most closest collision that is going to occur.
            if (temp != null && temp.distance(trajectory.start()) < minD) {
                minD = temp.distance(trajectory.start());
                closestPoint = temp;
                closestCollision = collidablesTemp.get(i);
            }
        }


        //If this object will not collide with any of the collidables in this collection, return null
        if (closestPoint == null || closestCollision == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, closestCollision);
    }
}
