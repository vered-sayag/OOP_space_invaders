package game.indicators;

/**
 * The type Counter.
 */
public class Counter {
    private long num;

    /**
     * Instantiates a new Counter.
     *
     * @param num the num
     */
    public Counter(long num) {
        this.num = num;
    }

    /**
     * Increase.
     *
     * @param number the number
     */
    public void increase(int number) {
        num = num + number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
    public void decrease(int number) {
        num = num - number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public long getValue() {
        return num;
    }
}
