package fun.gusmurphy.chesses.engine.piece;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class Piece {

    private final PlayerColor color;
    private final PieceType type;

    @Deprecated
    public Piece(PlayerColor color) {
        this.color = color;
        this.type = PieceType.PAWN;
    }

    public Piece(PlayerColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public PieceId id() {
        return null;
    }

    public PlayerColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

}
