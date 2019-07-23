package read;

import geometry.objacts.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
    private Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Gets block.
     *
     * @param s    the s
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
// Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int xpos, int ypos) {
        return blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Get block width int.
     *
     * @param s the s
     * @return the int
     */
    public int getBlockWidth(String s) {
        return blockCreators.get(s).getWidth();
    }

    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
// Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return spacerWidths.get(s);
    }

    /**
     * Add block creator.
     *
     * @param bk the bk
     */
    public void addBlockCreator(BlockCreator bk) {
        blockCreators.put(bk.getSymbol(), bk);
    }

    /**
     * Spacer widths.
     *
     * @param key   the key
     * @param space the space
     */
    public void spacerWidths(String key, int space) {
        spacerWidths.put(key, space);
    }

}
