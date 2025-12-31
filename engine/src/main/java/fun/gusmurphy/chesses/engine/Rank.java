package fun.gusmurphy.chesses.engine;

import java.util.Arrays;

/** A horizontal row on a board. */
public enum Rank {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7);

    /** A 0-based value as if the board's origin was the upper-left corner. */
    public final int yValue;

    public static final int MAX_Y_VALUE = 7;

    Rank(int yValue) {
        this.yValue = yValue;
    }

    public static Rank withYValue(int yValue) {
        return Arrays.stream(values())
                .filter(rank -> rank.yValue == yValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
