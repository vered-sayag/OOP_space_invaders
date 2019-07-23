package game.levels;

import game.Sprite;
import geometry.objacts.Block;
import geometry.primitives.Point;
import geometry.primitives.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type File level.
 */
public class FileLevel implements LevelInformation {
    private int numberOfBalls;
    private List<Velocity> ballVelocities = new ArrayList<Velocity>();
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Block background;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;


    /**
     * Number of balls int.
     *
     * @return the int
     */
    public int numberOfBalls() {
        return numberOfBalls;
    }

    /**
     * Initial ball velocities list.
     *
     * @return the list
     */
    public List<Velocity> initialBallVelocities() {
        return ballVelocities;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    public int paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    public int paddleWidth() {
        return paddleWidth;
    }

    /**
     * Level name string.
     *
     * @return the string
     */
    public String levelName() {
        return levelName;
    }

    /**
     * Gets background.
     *
     * @return the background
     */
    public Sprite getBackground() {
        return background;
    }

    /**
     * Blocks list.
     *
     * @return the list
     */
    public List<Block> blocks() {
        return blocks;
    }

    /**
     * Number of blocks to remove int.
     *
     * @return the int
     */
    public int numberOfBlocksToRemove() {
        return numberOfBlocksToRemove;
    }

    /**
     * Sets background.
     *
     * @param color the color
     * @param image the image
     */
    public void setBackground(Color color, String image) {
        Block block = new Block(new Point(0, 0), 800, 600, color, 0);
        block.setImage(image);
        this.background = block;
    }

    /**
     * Sets ball velocities.
     *
     * @param newBallVelocities the ball velocities
     */
    public void setBallVelocities(List<Velocity> newBallVelocities) {
        this.ballVelocities = newBallVelocities;
    }

    /**
     * Sets blocks.
     *
     * @param newBlocks the blocks
     */
    public void setBlocks(List<Block> newBlocks) {
        this.blocks = newBlocks;
    }

    /**
     * Sets number of blocks to remove.
     *
     * @param newNumberOfBlocksToRemove the number of blocks to remove
     */
    public void setNumberOfBlocksToRemove(int newNumberOfBlocksToRemove) {
        this.numberOfBlocksToRemove = newNumberOfBlocksToRemove;
    }

    /**
     * Sets number of balls.
     *
     * @param newNumberOfBalls the number of balls
     */
    public void setNumberOfBalls(int newNumberOfBalls) {
        this.numberOfBalls = newNumberOfBalls;
    }

    /**
     * Sets paddle width.
     *
     * @param newPaddleWidth the paddle width
     */
    public void setPaddleWidth(int newPaddleWidth) {
        this.paddleWidth = newPaddleWidth;
    }

    /**
     * Sets paddle speed.
     *
     * @param newPaddleSpeed the paddle speed
     */
    public void setPaddleSpeed(int newPaddleSpeed) {
        this.paddleSpeed = newPaddleSpeed;
    }

    /**
     * Sets level name.
     *
     * @param  newLevelName the level name
     */
    public void setLevelName(String newLevelName) {
        this.levelName =  newLevelName;
    }


}
