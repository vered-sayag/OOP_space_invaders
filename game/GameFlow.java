package game;


import animation.GameOver;
import animation.Animation;
import animation.Menu;
import animation.KeyPressStoppableAnimation;
import animation.AnimationRunner;
import animation.MenuAnimation;
import animation.art.BackroundMenu;
import animation.HighScoresAnimation;
import biuoop.DialogManager;

import biuoop.KeyboardSensor;
import game.indicators.Counter;
import game.indicators.HighScoresTable;
import game.indicators.ScoreInfo;

import game.levels.LevelInformation;
import geometry.objacts.Frame;

import java.io.InputStreamReader;

import read.LevelSpecificationReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {

    private Counter score;
    private Counter lives;
    private Frame frame;
    private KeyboardSensor k;
    private AnimationRunner animationRunner;
    private int numLives;


    /**
     * Instantiates a new Game flow.
     *
     * @param lives the lives
     */
    public GameFlow(int lives) {
        this.frame = new Frame(800, 600);
        this.numLives = lives;
        this.k = frame.getGUI().getKeyboardSensor();
        this.animationRunner = new AnimationRunner(frame.getGUI(), 60);
        score = new Counter(0);
        this.lives = new Counter(numLives);
    }


    /**
     * Run levels.
     */
    public void runLevels() {

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Space Invaders",
                k, new BackroundMenu(), animationRunner);
        menu.addSelection("s", "start game",
                new LevelsTask(LevelSpecificationReader.levelsReader(new InputStreamReader(
                        ClassLoader.getSystemClassLoader().getResourceAsStream("Space_Invaders_definitions.txt"))),
                        1));
        Task<Void> hisoore = new Task<Void>() {
            public Void run() {
                showScore();
                return null;
            }
        };

        Task<Void> end = new Task<Void>() {
            public Void run() {
                frame.getGUI().close();
                System.exit(0);
                return null;
            }
        };

        menu.addSelection("h", "high scores", hisoore);
        menu.addSelection("q", "quit", end);


        while (true) {
            animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();

        }


    }

    /**
     * save the score.
     */
    private void saveScore() {
        HighScoresTable table = HighScoresTable.loadFromFile(new File("highscores.scr"));
        if (table.getRank(score.getValue()) < table.size()) {
            DialogManager dialog = frame.getGUI().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            table.add(new ScoreInfo(name, score.getValue()));
            try {
                table.save(new File("highscores.scr"));
            } catch (IOException e) {
                System.out.println(" Something went wrong while reading !");
            }
        }
    }

    /**
     * show scores.
     */
    private void showScore() {
        HighScoresTable table = HighScoresTable.loadFromFile(new File("highscores.scr"));
        Animation highScoresAnimation = new KeyPressStoppableAnimation(k,
                "space", new HighScoresAnimation(table, "space"));
        animationRunner.run(highScoresAnimation);

    }

    /**
     * task - run levels.
     */
    final class LevelsTask implements Task<Void> {
        private List<LevelInformation> levels;
        private int numLevel;


        /**
         * Instantiates a new Levels task.
         *
         * @param levels   the levels
         * @param numLevel the num level
         */
        private LevelsTask(List<LevelInformation> levels, int numLevel) {
            this.levels = levels;
            this.numLevel = numLevel;
        }

        /**
         * run.
         *
         * @return noting
         */
        public Void run() {

            GameLevel level = null;

            for (LevelInformation levelInfo : levels) {
                levelInfo.setLevelName("level " + Integer.toString(numLevel));

                level = new GameLevel(levelInfo, k, animationRunner, frame, score, lives, numLevel);

                level.initialize();

                while (!level.shouldStop()) {
                    level.playOneTurn();
                    level.tryAgain();
                }

                frame.removeAllHitListener();

                if (lives.getValue() <= 0) {
                    lives = new Counter(0);
                    animationRunner.run(new KeyPressStoppableAnimation(k,
                            "space", new GameOver(level.getSprites(), score)));
                    saveScore();
                    showScore();
                    score = new Counter(0);
                    lives = new Counter(numLives);
                    return null;
                }
            }
            if (level != null) {
                new LevelsTask(levels, numLevel + 1).run();

            }
            return null;
        }
    }
}
