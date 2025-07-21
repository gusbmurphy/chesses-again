package fun.gusmurphy.chesses.engine.piece;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class Piece {

    private final PlayerColor color;
    private final PieceType type;
    private final PieceId id;

    @Deprecated
    public Piece(PlayerColor color) {
        this.color = color;
        this.type = PieceType.PAWN;
        this.id = new PieceId();
    }

    public Piece(PlayerColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.id = new PieceId();
    }

    public PieceId id() {
        return id;
    }

    public PlayerColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

}
