package game;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;


/**
 * class of frame with a limits and color.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * add Sprite.
     *
     * @param s new Sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * remove Sprite.
     *
     * @param s Sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt the dt
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> spritesTemp = new ArrayList<Sprite>(sprites);
        for (int i = 0; i < spritesTemp.size(); i++) {
            spritesTemp.get(i).timePassed(dt);

        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d given a surface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesTemp = new ArrayList<Sprite>(sprites);

        for (int i = 0; i < spritesTemp.size(); i++) {

            spritesTemp.get(i).drawOn(d);

        }
    }
}
