package read;

import game.levels.FileLevel;
import game.levels.LevelInformation;
import geometry.objacts.Block;
import geometry.primitives.Velocity;

import java.awt.Color;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private List<LevelInformation> fileLevels = new ArrayList<LevelInformation>();
    private String levelName;
    private List<Velocity> ballVelocities;
    private String backgroundImage;
    private Color backgroundColor;
    private int paddleSpeed;
    private int paddleWidth;
    private BlocksFromSymbolsFactory blocksFromSymbolsFactory;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numBlocks;
    private List<Block> blocks;

    /**
     * From read list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromRead(java.io.Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        ColorsParser colorsParser = new ColorsParser();
        String lineBlocks = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = compile("START_LEVEL( *)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    startOver();
                    continue;
                }
                pattern = compile("END_LEVEL( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    end();
                    continue;
                }

                pattern = compile("level_name:(.*)");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    levelName = line.substring(matcher.start() + 11, matcher.end());
                }

                if (line.startsWith("ball_velocities:")) {
                    readBallVelocities(line);
                }

                pattern = compile("paddle_speed:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    paddleSpeed = readInt(line);
                }

                pattern = compile("paddle_width:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    paddleWidth = readInt(line);
                }

                pattern = compile("blocks_start_x:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    blocksStartX = readInt(line);
                }

                pattern = compile("blocks_start_y:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    blocksStartY = readInt(line);
                }

                pattern = compile("row_height:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    rowHeight = readInt(line);
                }

                pattern = compile("num_blocks:([1-9])(\\d*)( *)");
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    numBlocks = readInt(line);
                }


                pattern = compile("block_definitions:(.*)\\.txt");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    readBlockDefinitions(line.substring(matcher.start() + 18, matcher.end()));
                }

                pattern = compile("background:color");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    backgroundColor = colorsParser.colorFromString(line);
                }
                pattern = compile("background:image([^ ]+)");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    backgroundImage = line.substring(matcher.start() + 17, matcher.end() - 1);
                }

                pattern = compile("START_BLOCKS( *)");
                matcher = pattern.matcher(line);
                Pattern pattern2 = compile("END_BLOCKS( *)");
                Matcher matcher2;
                if (matcher.matches() && blocksFromSymbolsFactory != null) {
                    int y = blocksStartY + 20;
                    while ((lineBlocks = bufferedReader.readLine()) != null) {
                        int x = blocksStartX;
                        matcher2 = pattern2.matcher(lineBlocks);
                        if (matcher2.matches()) {
                            break;
                        }
                        for (int i = 0; i < lineBlocks.length(); i++) {
                            if (blocksFromSymbolsFactory.isBlockSymbol(Character.toString(lineBlocks.charAt(i)))) {
                                blocks.add(blocksFromSymbolsFactory.getBlock(Character.toString(lineBlocks.charAt(i)),
                                        x, y));
                                x = x + blocksFromSymbolsFactory.getBlockWidth(
                                        Character.toString(lineBlocks.charAt(i)));
                            }
                            if (blocksFromSymbolsFactory.isSpaceSymbol(Character.toString(lineBlocks.charAt(i)))) {
                                x = x + blocksFromSymbolsFactory.getSpaceWidth(
                                        Character.toString(lineBlocks.charAt(i)));
                            }
                        }
                        y = y + rowHeight;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("can't read levels file");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("can't close levels file");
            }

        }
        return fileLevels;
    }

    /**
     * start Over.
     */
    private void startOver() {
        levelName = null;
        ballVelocities = new ArrayList<Velocity>();
        backgroundImage = null;
        backgroundColor = null;
        paddleSpeed = 0;
        paddleWidth = 0;
        blocksFromSymbolsFactory = new BlocksFromSymbolsFactory();
        blocksStartX = -1;
        blocksStartY = -1;
        rowHeight = 0;
        numBlocks = 0;

        blocks = new ArrayList<Block>();

    }

    /**
     * end -creat the level.
     */
    private void end() {
        if (paddleSpeed > 0 && levelName != null && ballVelocities.size() != 0 && paddleWidth > 0
                && blocks.size() > 0 && blocksStartX >= 0 && blocksStartY >= 0
                && rowHeight > 0 && numBlocks > 0
                && (backgroundColor != null || backgroundImage != null)) {
            FileLevel fileLevel = new FileLevel();
            fileLevel.setBackground(backgroundColor, backgroundImage);
            fileLevel.setBallVelocities(ballVelocities);
            fileLevel.setBlocks(blocks);
            fileLevel.setLevelName(levelName);
            fileLevel.setNumberOfBalls(ballVelocities.size());
            fileLevel.setPaddleSpeed(paddleSpeed);
            fileLevel.setNumberOfBlocksToRemove(numBlocks);
            fileLevel.setPaddleWidth(paddleWidth);
            fileLevels.add(fileLevel);

        }
    }

    /**
     * read ball velocities.
     *
     * @param line line
     */
    private void readBallVelocities(String line) {
        Pattern pattern = compile("(-?)(\\d+),(-?)([1-9])(\\d*)");
        Matcher matcher = pattern.matcher(line);
        Boolean notStop;
        do {
            notStop = matcher.find();
            if (notStop) {
                String subString = line.substring(matcher.start(), matcher.end());
                String[] arrayNames = subString.split(",");
                ballVelocities.add(Velocity.fromAngleAndSpeed(Integer.parseInt(arrayNames[0]),
                        Integer.parseInt(arrayNames[1])));
            }
        } while (notStop);
    }

    /**
     * read Int from string.
     *
     * @param line line
     * @return int
     */
    private int readInt(String line) {
        Pattern pattern = compile("([1-9])(\\d*)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(line.substring(matcher.start(), matcher.end()));
        }
        return 0;
    }

    /**
     * read blcck definitions.
     *
     * @param line line
     */
    private void readBlockDefinitions(String line) {

        Reader reader = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(line));
        blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(reader);
    }

    /**
     * Levels reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public static List<LevelInformation> levelsReader(java.io.Reader reader) {
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        return levelSpecificationReader.fromRead(reader);
    }
}
