package game.indicators;

import biuoop.DrawSurface;

import java.awt.Color;

import game.Sprite;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;


    /**
     * Instantiates a new Score indicator.
     *
     * @param score the score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d given draw surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(d.getWidth() / 2 - 200, 17, "Score:" + Long.toString(score.getValue()), 20);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
    }

}
