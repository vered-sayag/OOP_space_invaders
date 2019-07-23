package game.lisiners;

import game.indicators.Counter;
import game.GameLevel;
import geometry.objacts.Ball;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game          the game
     * @param removedBlocks the removed blocks
     */
    public BallRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBalls = removedBlocks;
    }

    /**
     * Hit event.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
        if (remainingBalls.getValue() >= 0) {
            game.lose();
        }
    }
}
