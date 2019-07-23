package animation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Sprite;


import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {

    private Map<String, String> mapTitles = new HashMap<String, String>();
    private Map<String, T> mapTask = new HashMap<String, T>();
    private Map<String, Menu<T>> subMapMenu = new HashMap<String, Menu<T>>();
    private T status;
    private String headLine;
    private boolean stop = false;
    private KeyboardSensor sensor;
    private List<String> titels = new ArrayList<String>();
    private Sprite backround;
    private AnimationRunner animationRunner;

    /**
     * Instantiates a new Menu animation.
     *
     * @param headLine        the head line
     * @param sensor          the sensor
     * @param backround       the backround
     * @param animationRunner the animation runner
     */
    public MenuAnimation(String headLine, KeyboardSensor sensor, Sprite backround, AnimationRunner animationRunner) {
        this.headLine = headLine;
        this.sensor = sensor;
        this.backround = backround;
        this.animationRunner = animationRunner;

    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public T getStatus() {
        return this.status;
    }


    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        if (stop) {
            stop = false;
            return true;
        }
        return stop;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //background
        backround.drawOn(d);
        backround.timePassed(dt);

        //title
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 2 - 350, 150, headLine, 100);

        int hgith = 210;
        for (String key : this.titels) {
            d.drawText(d.getWidth() / 2 - 150, hgith, "(" + this.mapTitles.get(key) + ")" + key, 45);
            hgith = hgith + 60;
        }

        for (String key : this.mapTask.keySet()) {
            if (sensor.isPressed(key)) {
                status = this.mapTask.get(key);
                stop = true;
            }
        }

        for (String key : this.subMapMenu.keySet()) {
            if (sensor.isPressed(key)) {
                animationRunner.run(subMapMenu.get(key));
                status = this.subMapMenu.get(key).getStatus();
                stop = true;
                subMapMenu.get(key).doOneFrame(d, dt);
                subMapMenu.get(key).shouldStop();
            }
        }
    }

    /**
     * Add selection.
     *
     * @param key       the key
     * @param message   the message
     * @param returnVal the return val
     */
    public void addSelection(String key, String message, T returnVal) {
        mapTask.put(key, returnVal);
        mapTitles.put(message, key);
        int place = titels.size();
        titels.add(place, message);
    }

    /**
     * Add sub menu.
     *
     * @param key     the key
     * @param message the message
     * @param subMenu the sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        subMapMenu.put(key, subMenu);
        mapTitles.put(message, key);
        int place = titels.size();
        titels.add(place, message);
    }

}

