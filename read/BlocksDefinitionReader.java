
package read;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;

import static java.util.regex.Pattern.compile;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    private int defaultWidth = 0;
    private int defaultHitPoints = 0;
    private Color defaultFill = null;
    private Color defaultStroke = null;
    private String defaultImage = null;
    private int defaultHeight = 0;
    private java.io.Reader reader;
    private BlocksFromSymbolsFactory bfsf = new BlocksFromSymbolsFactory();
    private Map<Integer, Color> defaultHitMapColor = new HashMap<Integer, Color>();
    private Map<Integer, String> defaultHitMapImage = new HashMap<Integer, String>();

    /**
     * Instantiates a new Blocks definition reader.
     *
     * @param reader the reader
     */
    public BlocksDefinitionReader(java.io.Reader reader) {
        this.reader = reader;
    }

    /**
     * Read blocks blocks from symbols factory.
     *
     * @return the blocks from symbols factory
     */
    public BlocksFromSymbolsFactory readBlocks() {
        bfsf = new BlocksFromSymbolsFactory();
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        try {
            Pattern defultPattern = compile("(default) (.*)");
            Pattern bdefPattern = compile("(bdef) (.*)");
            Pattern sdefPattern = compile("(sdef) (.*)");

            while ((line = bufferedReader.readLine()) != null) {

                Matcher defultMatcher = defultPattern.matcher(line);
                Matcher bdefMatcher = bdefPattern.matcher(line);
                Matcher sdefMatcher = sdefPattern.matcher(line);

                if (defultMatcher.matches()) {
                    readDefault(line);
                } else if (bdefMatcher.matches()) {
                    readBlock(line);
                } else if (sdefMatcher.matches()) {
                    readSpace(line);

                }
            }
        } catch (IOException e) {
            System.out.println("can't read blocks file");

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("can't close blocks file");
            }
        }
        return bfsf;
    }

    /**
     * read Default.
     *
     * @param line line.
     */
    private void readDefault(String line) {
        ColorsParser colorsParser = new ColorsParser();
        readHitPointChange(line, defaultHitMapColor, defaultHitMapImage);
        defaultWidth = readWidth(line);
        defaultHeight = readHeight(line);
        defaultHitPoints = readHitPoints(line);
        defaultImage = readImage(line);
        Pattern pattern1 = compile(" stroke:color\\(([^ ]+)\\)");
        Matcher matcher1 = pattern1.matcher(line);
        if (matcher1.find()) {
            String subLine = line.substring(matcher1.start(), matcher1.end());
            defaultStroke = colorsParser.colorFromString(subLine);
        }
        pattern1 = compile(" fill:color\\(([^ ]+)\\)");
        matcher1 = pattern1.matcher(line);
        if (matcher1.find()) {
            String subLine = line.substring(matcher1.start(), matcher1.end());
            defaultFill = colorsParser.colorFromString(subLine);
        }
    }

    /**
     * read Block.
     *
     * @param line line.
     */
    private void readBlock(String line) {
        ColorsParser colorsParser = new ColorsParser();
        String simbol = readSymbol(line);
        int width = 0;
        int hitPoints = 0;
        Color fill = null;
        Color stroke = null;
        String image = null;
        int height = 0;
        Map<Integer, Color> hitMapColor = new HashMap<Integer, Color>();
        Map<Integer, String> hitMapImage = new HashMap<Integer, String>();

        width = readWidth(line);
        if (width <= 0) {
            width = defaultWidth;
        }

        height = readHeight(line);
        if (height <= 0) {
            height = defaultHeight;
        }

        hitPoints = readHitPoints(line);
        if (hitPoints <= 0) {
            hitPoints = defaultHitPoints;
        }


        Pattern pattern1 = compile(" fill:color\\(([^ ]+)\\)");
        Matcher matcher1 = pattern1.matcher(line);
        Pattern pattern2 = compile(" fill:image\\(([^ ]+)\\)");
        Matcher matcher2 = pattern2.matcher(line);
        if (matcher1.find()) {
            String subLine = line.substring(matcher1.start(), matcher1.end());
            fill = colorsParser.colorFromString(subLine);
        }
        if (matcher2.find()) {
            image = readImage(line);
        }
        if (image == null && fill == null) {
            image = defaultImage;
            fill = defaultFill;
        }

        pattern1 = compile(" stroke:color\\(([^ ]+)\\)");
        matcher1 = pattern1.matcher(line);
        if (matcher1.find()) {
            String subLine = line.substring(matcher1.start(), matcher1.end());
            stroke = colorsParser.colorFromString(subLine);
        }
        if (stroke == null) {
            stroke = defaultStroke;
        }


        readHitPointChange(line, hitMapColor, hitMapImage);
        hitMapColor.putAll(defaultHitMapColor);
        hitMapImage.putAll(defaultHitMapImage);


        if (simbol != null && width > 0 && height > 0 && hitPoints > 0 && (fill != null || image != null
                || hitMapColor.size() + hitMapImage.size() >= hitPoints)) {
            BlockCreator blockCreator = new BlockCreator();
            blockCreator.setHitMapImage(hitMapImage);
            blockCreator.setFill(fill);
            blockCreator.setHeight(height);
            blockCreator.setHitMapColor(hitMapColor);
            blockCreator.setSymbol(simbol);
            blockCreator.setWidth(width);
            blockCreator.setHitPoints(hitPoints);
            blockCreator.setImage(image);
            blockCreator.setStroke(stroke);
            bfsf.addBlockCreator(blockCreator);
        }


    }

    /**
     * read Space.
     *
     * @param line line.
     */
    private void readSpace(String line) {
        String simbol = readSymbol(line);
        int width = readWidth(line);
        if (simbol != null && width > 0) {
            bfsf.spacerWidths(simbol, width);
        }

    }

    /**
     * read Width.
     *
     * @param line line.
     * @return width
     */
    private int readWidth(String line) {
        Pattern pattern1 = compile("(width:)([1-9])([0-9]*)");
        Pattern pattern2 = compile("([1-9])([0-9]*)");
        Matcher matcher1 = pattern1.matcher(line);
        String subLine;
        if (matcher1.find()) {
            subLine = line.substring(matcher1.start(), matcher1.end());
            Matcher matcher2 = pattern2.matcher(subLine);
            if (matcher2.find()) {
                return Integer.parseInt(subLine.substring(matcher2.start(), matcher2.end()));
            }

        }
        return 0;
    }

    /**
     * read height.
     *
     * @param line line
     * @return num height.
     */
    private int readHeight(String line) {
        int output = 0;
        Pattern pattern1 = compile("(height:)([1-9])([0-9]*)");
        Pattern pattern2 = compile("([1-9])([0-9]*)");
        Matcher matcher1 = pattern1.matcher(line);
        String subLine;
        if (matcher1.find()) {
            subLine = line.substring(matcher1.start(), matcher1.end());
            Matcher matcher2 = pattern2.matcher(subLine);
            if (matcher2.find()) {
                return Integer.parseInt(subLine.substring(matcher2.start(), matcher2.end()));
            }

        }
        return 0;
    }

    /**
     * read Hit Points.
     *
     * @param line line
     * @return num Hit Points
     */
    private int readHitPoints(String line) {
        int output = 0;
        Pattern pattern1 = compile("(hit_points:)([1-9])([0-9]*)");
        Pattern pattern2 = compile("([1-9])([0-9]*)");
        Matcher matcher1 = pattern1.matcher(line);
        String subLine;
        if (matcher1.find()) {
            subLine = line.substring(matcher1.start(), matcher1.end());
            Matcher matcher2 = pattern2.matcher(subLine);
            if (matcher2.find()) {
                return Integer.parseInt(subLine.substring(matcher2.start(), matcher2.end()));
            }

        }
        return 0;
    }

    /**
     * read Symbol.
     *
     * @param line line
     * @return Symbol.
     */
    private String readSymbol(String line) {
        String output = null;
        Pattern pattern1 = compile("(symbol:). ");
        Matcher matcher1 = pattern1.matcher(line);

        if (matcher1.find()) {
            output = line.substring(matcher1.end() - 2, matcher1.end() - 1);
        }
        return output;
    }

    /**
     * read Image.
     *
     * @param line line
     * @return Image
     */
    private String readImage(String line) {
        String output = null;

        Pattern pattern1 = Pattern.compile(" fill:image\\(([^ ]+)\\)");
        Matcher matcher1 = pattern1.matcher(line);

        if (matcher1.find()) {
            output = line.substring(matcher1.start() + 12, matcher1.end() - 1);
        }
        return output;

    }

    /**
     * read Image for fill - num.
     *
     * @param line line
     * @return Image for fill - num
     */
    private String readImage2(String line) {
        String output = null;

        Pattern pattern1 = Pattern.compile(" fill-[1-9]([0-9]*):image\\(([^ ]+)\\)");
        Matcher matcher1 = pattern1.matcher(line);
        Pattern pattern2 = Pattern.compile("\\(([^ ]+)\\)");

        String subLine;
        if (matcher1.find()) {
            subLine = line.substring(matcher1.start(), matcher1.end());
            Matcher matcher2 = pattern2.matcher(subLine);
            if (matcher2.find()) {
                output = subLine.substring(matcher2.start() + 1, matcher2.end() - 1);
            }
        }
        return output;

    }

    /**
     * read Hit Point Change.
     *
     * @param line        line
     * @param hitMapColor hitMap of color
     * @param hitMapImage hitMap of images
     */
    private void readHitPointChange(String line, Map<Integer, Color> hitMapColor, Map<Integer, String> hitMapImage) {
        ColorsParser colorsParser = new ColorsParser();
        Pattern pattern1 =
                compile(" fill-[1-9]([0-9]*):color\\(([^ ]+)\\)");
        Matcher matcher1 = pattern1.matcher(line);
        Pattern pattern2 = compile(" fill-[1-9]([0-9]*):image\\(([^ ]+)\\)");
        Matcher matcher2 = pattern2.matcher(line);
        Pattern pattern3 = compile("-[1-9]([0-9]*):");
        String subLine;
        Boolean matcher1Find;
        Boolean matcher2Find;
        do {
            matcher1Find = matcher1.find();
            if (matcher1Find) {
                subLine = line.substring(matcher1.start(), matcher1.end());
                Matcher matcher3 = pattern3.matcher(subLine);
                if (matcher3.find()) {
                    int num = Integer.parseInt(subLine.substring(matcher3.start() + 1, matcher3.end() - 1));
                    Color color = colorsParser.colorFromString(subLine);
                    hitMapColor.put(num, color);
                }
            }
            matcher2Find = matcher2.find();
            if (matcher2Find) {
                subLine = line.substring(matcher2.start(), matcher2.end());
                Matcher matcher3 = pattern3.matcher(subLine);
                if (matcher3.find()) {
                    int num = Integer.parseInt(subLine.substring(matcher3.start() + 1, matcher3.end() - 1));
                    String image = readImage2(subLine);
                    hitMapImage.put(num, image);
                }
            }
        } while (matcher1Find || matcher2Find);
    }


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

        BlocksDefinitionReader blocksReader = new BlocksDefinitionReader(reader);
        return blocksReader.readBlocks();
    }

}
