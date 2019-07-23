package animation;


import biuoop.DrawSurface;
import game.SpriteCollection;

import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private SpriteCollection gameScreen;
    private double numOfSeconds;
    private int countFrom;
    private int i;


    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
        this.gameScreen = gameScreen;
        i = countFrom;


    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        gameScreen.drawAllOn(d);
        d.setColor(Color.white);
        d.fillOval(d.getWidth() / 2 - 100, d.getHeight() / 2 - 80, 200, 100);
        if (i == 0) {
            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2, "GO", 80);

        } else if (i > 0) {

            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 2 - 25, d.getHeight() / 2, Integer.toString(i), 80);
        }
        if (i != countFrom) {
            sleeper.sleepFor((int) ((1000 * numOfSeconds) / (countFrom + 1)));
        }
        i--;
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {

        if (i < -1) {
            return true;
        }
        return false;
    }
}
