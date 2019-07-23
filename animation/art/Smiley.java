package animation.art;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * create a animation of smiley.
 *
 * @author Vered Sayag
 * @version 29.03.2018
 */
public class Smiley {

    private int starX;
    private int arrangeX;
    private int endY;
    private Color colorShirt;
    private int i;
    private int velocity;
    private int velocityHands;

    /**
     * constructor.
     *
     * @param starX      were in 'x' to start tpo draw the Smiley
     * @param endY       were in 'y' to start tpo draw the Smiley
     * @param colorShirt the color of the Smileys' shirt
     * @param arrangeX   the arrange of the Smiley moves.
     */
    public Smiley(int starX, int endY, int arrangeX, Color colorShirt) {
        this.starX = starX;
        this.endY = endY;
        this.arrangeX = arrangeX;
        this.colorShirt = colorShirt;
        velocity = 1;
        i = 0;
        velocityHands = 30;
    }

    /**
     * onstructor.
     *
     * @param endY     were in 'y' to start tpo draw the Smiley
     * @param arrangeX the arrange of the Smiley moves.
     */
    public Smiley(int endY, int arrangeX) {
        this.starX = 0;
        this.endY = endY;
        this.colorShirt = Color.blue;
        this.arrangeX = arrangeX;
        i = 0;
        velocity = 1;
        velocityHands = 10;
    }

    /**
     * onstructor.
     *
     * @param starX    were in 'x' to start tpo draw the Smiley
     * @param endY     were in 'y' to start tpo draw the Smiley
     * @param arrangeX the arrange of the Smiley moves.
     */
    public Smiley(int starX, int endY, int arrangeX) {
        this.starX = starX;
        this.endY = endY;
        this.arrangeX = arrangeX;
        this.colorShirt = Color.blue;
        velocity = 1;
        i = 0;
        velocityHands = 10;
    }

    /**
     *chance the velocity 'x' of the smiley.
     *
     * @param velocitySmiley      he velocity 'x' of the smiley.
     */
    public void setVelocity(int velocitySmiley) {
        this.velocity = velocitySmiley;
    }

    /**
     * chance the number of steps in 'x' of the hands chance their direction.
     *
     * @param velocitySmileyHands    int the number of steps in 'x' of the hands chance their direction
     */
    public void setVelocityHands(int velocitySmileyHands) {
        this.velocityHands = velocitySmileyHands;
    }

    /**
     * draw the Smiley on the surfac.
     *
     * @param d Surface  to draw on.
     */
    public void drawSmiley(DrawSurface d) {
        d.setColor(Color.yellow);
        d.fillCircle(starX + i + 60, endY - 110, 30);

        d.setColor(Color.black);
        d.fillCircle(starX + i + 50, endY - 120, 5);
        d.fillCircle(starX + i + 70, endY - 120, 5);
        d.drawLine(starX + i + 55, endY - 95, starX + i + 65, endY - 95);
        d.drawLine(starX + i + 45, endY - 105, starX + i + 50, endY - 98);
        d.drawLine(starX + i + 70, endY - 98, starX + i + 75, endY - 105);
        d.drawLine(starX + i + 65, endY - 95, starX + i + 70, endY - 98);
        d.drawLine(starX + i + 50, endY - 98, starX + i + 55, endY - 95);
        d.fillRectangle(starX + i + 30, endY - 20, 60, 60);
        d.setColor(colorShirt);
        d.fillRectangle(starX + i + 30, endY - 80, 60, 60);

        //move hands
        if (i % (2 * velocityHands) < velocityHands) {
            d.setColor(Color.yellow);
            d.fillCircle(starX + i + 10, endY - 100, 10);
            d.fillCircle(starX + i + 110, endY - 40, 10);

            d.setColor(colorShirt);
            d.fillRectangle(starX + i + 90, endY - 80, 30, 20);
            d.fillRectangle(starX + i + 100, endY - 80, 20, 40);
            d.fillRectangle(starX + i, endY - 80, 30, 20);
            d.fillRectangle(starX + i, endY - 100, 20, 20);
        } else {
            d.setColor(Color.yellow);
            d.fillCircle(starX + i + 10, endY - 40, 10);
            d.fillCircle(starX + i + 110, endY - 100, 10);

            d.setColor(colorShirt);
            d.fillRectangle(starX + i, endY - 80, 30, 20);
            d.fillRectangle(starX + i, endY - 80, 20, 40);
            d.fillRectangle(starX + i + 90, endY - 80, 30, 20);
            d.fillRectangle(starX + i + 100, endY - 100, 20, 20);
        }
    }

    /**
     * chance the Smiley location and draw it.
     *
     * @param d Surface  to draw on.
     */
    public void moveOneStep(DrawSurface d) {
        i = i + velocity;
        if (i >= arrangeX || i <= starX) {
            velocity = -velocity;
        }
        drawSmiley(d);
    }
}
