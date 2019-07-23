package animation.art;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * The type Backround high scores.
 */
public class BackroundHighScores {
    private BufferedImage image;

    /**
     * the constructor.
     */
    public BackroundHighScores() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images_game/high_score.png"));
        } catch (Exception e) {
            System.out.println("cant read image high scores");
        }
    }

    /**
     * Draw on.
     *
     * @param d a drawSurface          draw the sprite to the screen
     */
    public void drawOn(DrawSurface d) {

        d.drawImage(0, 0, this.image);
    }

    /**
     * Time passed.
     *
     * @param dt dt           notify the sprite that time has passed.
     */
    public void timePassed(double dt) {

    }
}
