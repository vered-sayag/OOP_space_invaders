package animation;

import animation.art.BackroundHighScores;
import biuoop.DrawSurface;
import game.indicators.HighScoresTable;
import game.indicators.ScoreInfo;
import java.awt.Color;
import java.util.List;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {

    private boolean stop = false;
    private HighScoresTable scores;
    private String endKey;


    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     * @param endKey the end key
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey) {
        this.scores = scores;
        this.endKey = endKey;

    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //background
        new BackroundHighScores().drawOn(d);

        //title
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 2 - 250, 100, "High Score", 100);

        d.drawText(d.getWidth() / 2 - 230, 585, "press " + endKey + " to continue", 40);

        //table
        d.drawText(50, 150, "Player Name", 40);
        d.drawText(450, 150, "Score", 40);

        d.fillRectangle(30, 175, 700, 2);
        List<ScoreInfo> list = scores.getHighScores();
        int hgith = 200;

        for (int i = 0; i < list.size(); i++) {
            d.drawText(50, hgith, list.get(i).getName(), 30);
            d.drawText(450, hgith, Long.toString(list.get(i).getScore()), 30);
            hgith = hgith + 40;
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return stop;
    }

}
