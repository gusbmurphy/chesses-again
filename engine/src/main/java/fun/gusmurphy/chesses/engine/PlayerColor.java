package fun.gusmurphy.chesses.engine;

public enum PlayerColor {
    WHITE, BLACK;

    public PlayerColor opposite() {
        if (this == PlayerColor.BLACK) {
            return WHITE;
        }

        return BLACK;
    }
}
