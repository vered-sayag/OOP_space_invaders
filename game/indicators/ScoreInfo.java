package game.indicators;

import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private long score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, long score) {
        this.score = score;
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public long getScore() {
        return score;
    }
}
