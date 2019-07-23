package game.lisiners;


import game.GameLevel;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    /**
     * Add hit listener.
     *
     * @param hl the hl
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hit listener.
     *
     * @param hl the hl
     */
    void removeHitListener(HitListener hl);

    /**
     * Gets hit num.
     *
     * @return the hit num
     */
    int getHitNum();

    /**
     * Remove from game.
     *
     * @param game the game
     */
    void removeFromGame(GameLevel game);
}
