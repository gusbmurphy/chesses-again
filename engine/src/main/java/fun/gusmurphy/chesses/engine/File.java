package fun.gusmurphy.chesses.engine;

import java.util.Arrays;

/** A vertical column on a board. */
public enum File {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7);

    /** A 0-based value as if the board's origin was the upper-left corner. */
    public final int xValue;

    public static final int MAX_X_VALUE = 7;

    File(int xValue) {
        this.xValue = xValue;
    }

    public static File withXValue(int xValue) {
        return Arrays.stream(values())
                .filter(rank -> rank.xValue == xValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
