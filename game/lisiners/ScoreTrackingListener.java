package game.lisiners;

import game.indicators.Counter;
import geometry.objacts.Ball;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Hit event.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {

        if (beingHit.getHitNum() == 0) {
            beingHit.removeHitListener(this);
            currentScore.increase(100);
        }

    }

}
