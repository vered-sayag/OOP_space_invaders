package game.indicators;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable {
    private ScoreInfo[] scoresTable;
    private int size;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.size = size;
        scoresTable = new ScoreInfo[size];
        for (int i = 0; i < size; i++) {
            scoresTable[i] = null;
        }
    }

    /**
     * Add.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        int rank = getRank(score.getScore());
        for (int i = size - 2; i >= rank; i--) {
            scoresTable[i + 1] = scoresTable[i];
        }
        scoresTable[rank] = score;

    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return size;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */

    public ArrayList<ScoreInfo> getHighScores() {

        ArrayList<ScoreInfo> scores = new ArrayList<ScoreInfo>();

        for (int i = 0; i < size; i++) {
            if (scoresTable[i] != null) {
                scores.add(scoresTable[i]);
            }
        }
        return scores;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(long score) {

        for (int i = 0; i < size; i++) {
            if (scoresTable[i] == null) {
                return i;
            }
            if (scoresTable[i].getScore() < score) {
                return i;
            }
        }
        return size;
    }

    /**
     * Clear.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            scoresTable[i] = null;
        }
    }

    /**
     * Load.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void load(File fileName) throws IOException {
        clear();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            List<ScoreInfo> scoreList = new ArrayList<>();
            scoreList.addAll((List<ScoreInfo>) ois.readObject());
            for (int i = 0; i < Math.min(scoreList.size(), size); i++) {
                scoresTable[i] = scoreList.get(i);
            }
        } catch (Exception e) {
            System.out.println(" Something went wrong while reading !");
        } finally {
            if (ois != null) {
                ois.close();
            }
        }

    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(getHighScores());
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(5);
        try {
            table.load(filename);
        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
        }
        return table;
    }
}
