package game;

import biuoop.DrawSurface;


/**
 * class of frame with a limits and color.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public interface Sprite {

    /**
     * draw the sprite to the screen.
     *
     * @param d given draw surface.
     */
        void drawOn(DrawSurface d);

        /**
         * notify the sprite that time has passed.
         *
         * @param dt the dt
         */
        void timePassed(double dt);
        }
