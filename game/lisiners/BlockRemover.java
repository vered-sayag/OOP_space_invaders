package game.lisiners;

import game.indicators.Counter;
import game.GameLevel;
import geometry.objacts.Ball;

/**
 * The type Block remover.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param game          the game
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Hit event.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(HitNotifier beingHit, Ball hitter) {
            if (beingHit.getHitNum() == 0) {
                beingHit.removeFromGame(game);
                beingHit.removeHitListener(this);
                remainingBlocks.decrease(1);
            }
            if (remainingBlocks.getValue() == 0) {
                game.win();
            }
        }
    }
