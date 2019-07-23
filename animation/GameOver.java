package animation;

import game.indicators.Counter;
import game.SpriteCollection;


/**
 * The type Game over.
 */
public class GameOver extends EndGame {

    /**
     * Instantiates a new Game over.
     *
     * @param gameScreen the game screen
     * @param score      the score
     */
    public GameOver(SpriteCollection gameScreen, Counter score) {
        super(gameScreen, score);

    }

    /**
     * return "Game Over".
     *
     * @return String kind
     */
    protected String kind() {
        return "Game Over";
    }
}
