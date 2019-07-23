package game;

import animation.PauseScreen;
import animation.KeyPressStoppableAnimation;
import animation.AnimationRunner;
import animation.Animation;
import biuoop.DrawSurface;
import animation.CountdownAnimation;

import java.awt.Color;
import java.util.List;

import biuoop.KeyboardSensor;
import game.indicators.Counter;
import game.indicators.LevelIndicator;
import game.indicators.LivesIndicator;
import game.indicators.ScoreIndicator;
import game.levels.LevelInformation;
import geometry.objacts.Frame;
import geometry.objacts.Paddle;
import geometry.objacts.Block;
import geometry.primitives.Point;
import game.lisiners.BallRemover;
import game.lisiners.BlockRemover;
import game.lisiners.HitListener;
import game.lisiners.ScoreTrackingListener;


/**
 * game - initialize and run.
 *
 * @author Vered Sayag
 * @version 15.04.2018
 */
public class GameLevel implements Animation {


    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private Frame frame;
    private int width = 800;
    private int height = 600;
    private int heightPaddel;
    private Counter score;
    private Counter lives;
    private Paddle paddle;
    private boolean lose = false;
    private boolean win = false;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private LevelInformation levelInfo;
    private Board board;
    private double speedBlocks;


    /**
     * Instantiates a new Game level.
     *
     * @param levelInfo       the level info
     * @param keyboardSensor  the keyboard sensor
     * @param animationRunner the animation runner
     * @param frame           the frame
     * @param score           the score
     * @param lives           the lives
     * @param speedBlocks     the speed blocks
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboardSensor,
                     AnimationRunner animationRunner, Frame frame, Counter score, Counter lives, double speedBlocks) {

        this.heightPaddel = 110;
        this.score = score;
        this.lives = lives;
        this.frame = frame;
        this.levelInfo = levelInfo;
        this.runner = animationRunner;
        this.keyboard = keyboardSensor;
        this.speedBlocks = speedBlocks;


    }

    /**
     * remove collidable.
     *
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove Sprite.
     *
     * @param s Sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * add a Collidable to the game.
     *
     * @param c Collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add a Sprite to the game.
     *
     * @param s Sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        paddle = new Paddle(new Point(width / 2 - levelInfo.paddleWidth() / 2,
                height - heightPaddel), this.frame, levelInfo.paddleSpeed(), sprites, environment);
        GameEnvironment gameEnvironmentAlien = new GameEnvironment();
        gameEnvironmentAlien.addCollidable(paddle);

        frame.addToGame(this);
        HitListener listenerBlackAlien = new BlockRemover(this, new Counter(levelInfo.numberOfBlocksToRemove()));
        HitListener listenerScore = new ScoreTrackingListener(score);
        HitListener listenerRemoveBall = new BallRemover(this, new Counter(0));
        HitListener listenerBlackNoAlien = new BlockRemover(this, new Counter(0));
        addSprite(levelInfo.getBackground());
        frame.addHitListener(listenerRemoveBall);


        for (int i = 350; i < 450; i = i + 4) {
            for (int j = 440; j < 450; j = j + 4) {
                Block shield = new Block(new Point(i, j), 4, 4, Color.WHITE);
                shield.addToGame(this);
                shield.addHitListener(listenerRemoveBall);
                shield.addHitListener(listenerBlackNoAlien);
                gameEnvironmentAlien.addCollidable(shield);
            }
        }
        for (int i = 100; i < 200; i = i + 4) {
            for (int j = 440; j < 450; j = j + 4) {
                Block shield = new Block(new Point(i, j), 4, 4, Color.WHITE);
                shield.addToGame(this);
                shield.addHitListener(listenerRemoveBall);
                shield.addHitListener(listenerBlackNoAlien);
                gameEnvironmentAlien.addCollidable(shield);
            }
        }
        for (int i = 600; i < 700; i = i + 4) {
            for (int j = 440; j < 450; j = j + 4) {
                Block shield = new Block(new Point(i, j), 4, 4, Color.WHITE);
                shield.addToGame(this);
                shield.addHitListener(listenerRemoveBall);
                shield.addHitListener(listenerBlackNoAlien);
                gameEnvironmentAlien.addCollidable(shield);
            }
        }

        this.board = new Board(speedBlocks, sprites, gameEnvironmentAlien, this, lives);
        List<Block> blocks = levelInfo.blocks();
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i).copy();
            block.addHitListener(listenerBlackAlien);
            block.addHitListener(listenerRemoveBall);
            block.addHitListener(listenerScore);
            board.addAlien(block);
            block.addToGame(this);
        }

        Block block;
        block = new Block(new Point(0, 0), width, 20, Color.lightGray);
        block.addToGame(this);
        block.addHitListener(listenerRemoveBall);
        //create balls
        addSprite(new ScoreIndicator(score));
        addSprite(new LivesIndicator(lives));
        addSprite(new LevelIndicator(levelInfo.levelName()));

        //create paddle

        paddle.addToGame(this);
        HitListener listenerRemoveLives = new BallRemover(this, lives);
        paddle.addHitListener(listenerRemoveLives);

    }

    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {

        createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, sprites));

        runner.run(this);

    }


    /**
     * Gets ennvironment.
     *
     * @return the GameEnvironment of the game.
     */
    public GameEnvironment getEnnvironment() {
        return this.environment;
    }


    /**
     * Lose.
     */
    public void lose() {
        lose = true;
        board.initialize();
    }

    /**
     * Try again.
     */
    public void tryAgain() {
        if (lives.getValue() > 0) {
            lose = false;
        }
    }

    /**
     * Win.
     */
    public void win() {
        win = true;
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return win || lose;
    }

    /**
     * Do one frame.
     *
     * @param d  the d
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
        sprites.notifyAllTimePassed(dt);
        sprites.drawAllOn(d);
        board.moveAll();

    }

    /**
     * Create balls on top Of paddle.
     */
    private void createBallsOnTopOfPaddle() {
        paddle.setLocation(new Point(width / 2 - levelInfo.paddleWidth() / 2, height - heightPaddel));
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public SpriteCollection getSprites() {
        return sprites;
    }

}

