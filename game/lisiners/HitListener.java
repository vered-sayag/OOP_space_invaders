package game.lisiners;

import geometry.objacts.Ball;

/**
 * The interface Hit listener.
 */
public interface HitListener {
    /**
     * Hit event.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    void hitEvent(HitNotifier beingHit, Ball hitter);
}
