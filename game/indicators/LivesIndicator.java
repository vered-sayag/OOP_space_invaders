package game.indicators;

import biuoop.DrawSurface;
import game.Sprite;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param lives the lives
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;

    }

    /**
     * draw the sprite to the screen.
     *
     * @param d given draw surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(0, 17, "Lives:" + Long.toString(lives.getValue()), 20);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
    }

}
