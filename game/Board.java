package game;

import game.indicators.Counter;
import geometry.objacts.Ball;
import geometry.objacts.Block;
import geometry.primitives.Point;
import geometry.primitives.Velocity;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Board.
 */
public class Board {
    private List<Block> alien = new ArrayList<Block>();
    private SpriteCollection spriteCollection;
    private GameEnvironment gameEnvironment;
    private double originalVelocity;
    private double velocity;
    private long time = System.currentTimeMillis();
    private GameLevel game;
    private Counter lives;


    /**
     * Instantiates a new Board.
     *
     * @param velocity         the velocity
     * @param spriteCollection the sprite collection to put in the shoots
     * @param gameEnvironment  the game environment for the aliens shoots
     * @param gameLevel        the game level to stop
     * @param lives            the lives to redoes
     */
    public Board(double velocity, SpriteCollection spriteCollection, GameEnvironment gameEnvironment,
                 GameLevel gameLevel, Counter lives) {
        this.gameEnvironment = gameEnvironment;
        this.spriteCollection = spriteCollection;
        this.game = gameLevel;
        this.lives = lives;
        this.velocity = velocity;
        this.originalVelocity = velocity;
    }

    /**
     * Add alien.
     *
     * @param block the block
     */
    public void addAlien(Block block) {
        alien.add(block);
    }

    /**
     * Move all.
     */
    public void moveAll() {
        if (edgeRhiteX() + velocity > 800) {

            double moving = 800 - edgeRhiteX() - velocity;
            velocity = -velocity * 1.1;
            for (Block block : alien) {

                block.getRectangle().setUpperLeft(new Point(block.getRectangle().getUpperLeft().getX() + moving,
                        block.getRectangle().getUpperLeft().getY() + 10));
            }

        } else if (edgeLeftX() + velocity < 0) {
            velocity = -velocity * 1.1;
            for (Block block : alien) {
                block.getRectangle().setUpperLeft(new Point(block.getRectangle().getUpperLeft().getX(),
                        block.getRectangle().getUpperLeft().getY() + 10));
            }

        } else {
            for (Block block : alien) {
                block.getRectangle().setUpperLeft(new Point(block.getRectangle().getUpperLeft().getX() + velocity,
                        block.getRectangle().getUpperLeft().getY()));
            }
        }
        if (System.currentTimeMillis() - time > 500) {
            time = System.currentTimeMillis();
            Random random = new Random();
            List<Block> blocks = findLestRow();
            if (blocks.size() > 0) {
                int rendomNum = random.nextInt(blocks.size());
                if (blocks.get(rendomNum) != null) {
                    Ball ball = new Ball(blocks.get(rendomNum).getRectangle().getUpperLeft().getX()
                            + blocks.get(rendomNum).getRectangle().getWidth() / 2,
                            blocks.get(rendomNum).getRectangle().getUpperLeft().getY()
                                    + blocks.get(rendomNum).getRectangle().getHeight() + 2, 2, Color.green);
                    ball.setVelocity(Velocity.fromAngleAndSpeed(0, 300));
                    ball.setGameEnvironment(gameEnvironment);
                    spriteCollection.addSprite(ball);
                }
            }
        }
        if (edgeYDuon() >= 430) {
            lives.decrease(1);
            game.lose();
        }
    }

    /**
     * Edge left x int.
     *
     * @return the int
     */
    private int edgeLeftX() {
        int min = 800;
        for (Block block : alien) {
            if (block.getHitNum() > 0 && block.getRectangle().getUpperLeft().getX() < min) {
                min = (int) (block.getRectangle().getUpperLeft().getX());
            }
        }
        return min;
    }

    /**
     * Edge rhite x int.
     *
     * @return the int
     */
    private int edgeRhiteX() {
        int max = 0;
        for (Block block : alien) {
            if (block.getHitNum() > 0
                    && block.getRectangle().getUpperLeft().getX() + block.getRectangle().getWidth() > max) {
                max = (int) (block.getRectangle().getUpperLeft().getX() + block.getRectangle().getWidth());
            }
        }
        return max;
    }

    /**
     * Edge y duon int.
     *
     * @return the int
     */
    private int edgeYDuon() {
        int max = 0;
        for (Block block : alien) {
            if (block.getHitNum() > 0
                    && block.getRectangle().getUpperLeft().getY() + block.getRectangle().getHeight() > max) {
                max = (int) (block.getRectangle().getUpperLeft().getY() + block.getRectangle().getHeight());
            }
        }
        return max;
    }

    /**
     * Edge y up int.
     *
     * @return the int
     */
    private int edgeYUp() {
        int min = 800;
        for (Block block : alien) {
            if (block.getHitNum() > 0 && block.getRectangle().getUpperLeft().getY() < min) {
                min = (int) (block.getRectangle().getUpperLeft().getY());
            }
        }
        return min;
    }

    /**
     * Find lest row list.
     *
     * @return the list
     */
    private List<Block> findLestRow() {
        List<Block> blocks = new ArrayList<Block>();
        Block block3 = null;
        for (Block block1 : alien) {
            if (block1.getHitNum() > 0) {
                double max = 0;
                for (Block block2 : alien) {
                    if (block1.getRectangle().getUpperLeft().getX() == block2.getRectangle().getUpperLeft().getX()
                            && block2.getRectangle().getUpperLeft().getY() > max && block2.getHitNum() > 0) {
                        max = block2.getRectangle().getUpperLeft().getY();
                        block3 = block2;

                    }

                }
                if (!blocks.contains(block3)) {
                    blocks.add(block3);
                }
            }
        }
        return blocks;
    }

    /**
     * Initialize.
     */
    public void initialize() {
        velocity = originalVelocity;
        double edgeX = edgeLeftX();
        double edgeY = edgeYUp();
        for (Block block : alien) {
            block.getRectangle().setUpperLeft(new Point(block.getRectangle().getUpperLeft().getX() - edgeX,
                    block.getRectangle().getUpperLeft().getY() - edgeY + 40));

        }


    }
}
