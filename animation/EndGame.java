package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.SpriteCollection;
import game.indicators.Counter;

import java.awt.Color;

/**
 * The type End game.
 */
abstract class EndGame implements Animation {
    private SpriteCollection gameScreen;
    private Counter score;
    private KeyboardSensor k;
    private boolean stop = false;

    /**
     * Instantiates a new End game.
     *
     * @param gameScreen the game screen
     * @param score      the score
     */
    EndGame(SpriteCollection gameScreen, Counter score) {
        this.score = score;
        this.gameScreen = gameScreen;
    }

    /**
     * Kind string.
     *
     * @return the string
     */
    abstract String kind();

    /**
     * do One Frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        gameScreen.drawAllOn(d);
        d.setColor(Color.white);
        d.fillOval(d.getWidth() / 2 - 240, d.getHeight() / 2 - 250, 470, 300);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 200, d.getHeight() / 2 - 100, kind(), 80);
        d.drawText(d.getWidth() / 2 - 180, d.getHeight() / 2 - 50,
                "Your score is : " + Long.toString(score.getValue()), 40);

    }

    /**
     * return Boolean - stop.
     *
     * @return Boolean - stop
     */
    public boolean shouldStop() {

        return stop;
    }

}
