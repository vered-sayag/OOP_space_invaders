package animation.art;

import biuoop.DrawSurface;
import game.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/**
 * The type Backround menu.
 */
public class BackroundMenu implements Sprite {
    private BufferedImage image;

    /**
     * the constructor.
     */
    public BackroundMenu() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images_game/menu.png"));
        } catch (Exception e) {
            System.out.println("cant read image menu");
        }
    }

    /**
     * @param d a drawSurface
     *          draw the sprite to the screen
     */
    public void drawOn(DrawSurface d) {

        d.drawImage(0, 0, this.image);
    }

    /**
     * @param dt dt
     *           notify the sprite that time has passed.
     */
    public void timePassed(double dt) {

    }
}
