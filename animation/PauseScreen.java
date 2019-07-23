package animation;


import animation.art.Smiley;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private Smiley smiley;

    /**
     * Instantiates a new Pause screen.
     *
     */
    public PauseScreen() {
        this.stop = false;
        smiley = new Smiley(0, 600, 680, Color.white);
    }

    /**
     * Do one frame.
     *
     * @param d the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.pink);
        d.fillRectangle(0, 0, d.getWidth(), d.getWidth());
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 4 + 50, d.getHeight() / 2, "paused ", 60);
        d.setColor(Color.black);
        d.drawText(d.getWidth() / 4, d.getHeight() / 2 + 40, "press space to continue", 32);
        smiley.moveOneStep(d);

    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
