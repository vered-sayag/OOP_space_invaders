package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop = false;
    private boolean isAlreadyPressed = true;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
    }

    /**
     * do One Frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (sensor.isPressed(key) && !isAlreadyPressed) {
            stop = true;
        }
        if (!sensor.isPressed(key)) {
            isAlreadyPressed = false;
        }

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
