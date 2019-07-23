package game.indicators;

import biuoop.DrawSurface;
import game.Sprite;

import java.awt.Color;

/**
 * The type Level indicator.
 */
public class LevelIndicator implements Sprite {
    private String name;


    /**
     * Instantiates a new Level indicator.
     *
     * @param name the name
     */
    public LevelIndicator(String name) {
        this.name = name;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d given draw surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(d.getWidth() - 200, 17, "Level Name:" + name, 20);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    public void timePassed(double dt) {
    }
}
