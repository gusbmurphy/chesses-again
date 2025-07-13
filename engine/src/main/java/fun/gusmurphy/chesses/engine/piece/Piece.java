package fun.gusmurphy.chesses.engine.piece;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class Piece {

    private final PlayerColor color;

    public Piece(PlayerColor color, PieceType type) {
        this.color = color;
    }

    public PieceId id() {
        return null;
    }

    public PlayerColor color() {
        return color;
    }

}
