package read;

import geometry.objacts.Block;
import geometry.primitives.Point;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


/**
 * The type Block creator.
 */
public class BlockCreator {
    private String symbol = null;
    private int width = 0;
    private int hitPoints = 0;
    private Color fill = null;
    private Color stroke = null;
    private String image = null;
    private int height = 0;
    private Map<Integer, Color> hitMapColor = new HashMap<Integer, Color>();
    private Map<Integer, String> hitMapImage = new HashMap<Integer, String>();


    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets fill.
     *
     * @param newFill the fill
     */
    public void setFill(Color newFill) {
        this.fill = newFill;
    }

    /**
     * Sets hit points.
     *
     * @param newHitPoints the hit points
     */
    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }

    /**
     * Sets image.
     *
     * @param newImage the image
     */
    public void setImage(String newImage) {
        this.image = newImage;
    }

    /**
     * Sets stroke.
     *
     * @param newStroke the stroke
     */
    public void setStroke(Color newStroke) {
        this.stroke = newStroke;
    }

    /**
     * Sets symbol.
     *
     * @param newSymbol the symbol
     */
    public void setSymbol(String newSymbol) {
        this.symbol = newSymbol;
    }

    /**
     * Sets width.
     *
     * @param newWidth the width
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    /**
     * Sets height.
     *
     * @param newHeight the height
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Sets hit map image.
     *
     * @param newHitMapImage the hit map image
     */
    public void setHitMapImage(Map<Integer, String> newHitMapImage) {
        this.hitMapImage = newHitMapImage;
    }

    /**
     * Sets hit map color.
     *
     * @param newHitMapColor the hit map color
     */
    public void setHitMapColor(Map<Integer, Color> newHitMapColor) {
        this.hitMapColor = newHitMapColor;
    }

    /**
     * Create block.
     *
     * @param x the x
     * @param y the y
     * @return the block
     */
    public Block create(int x, int y) {
        Block block = null;
        if (symbol != null && width > 0 && height > 0 && hitPoints > 0 && (fill != null || image != null
                || !hitMapColor.isEmpty() || !hitMapImage.isEmpty())) {
            block = new Block(new Point(x, y), width, height, fill, hitPoints);
            block.setImage(image);
            block.setStroke(stroke);
            block.setHitMapColor(hitMapColor);
            block.setHitMapImage(hitMapImage);
        }
        return block;
    }
}
