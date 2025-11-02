package fun.gusmurphy.chesses.engine.piece;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class Piece {

    private final PlayerColor color;
    private final PieceType type;
    private final PieceId id;
    private final boolean hasMoved;

    public Piece() {
        color = PlayerColor.WHITE;
        type = PieceType.BISHOP;
        id = new PieceId();
        hasMoved = false;
    }

    public Piece(PlayerColor color) {
        this.color = color;
        this.type = PieceType.PAWN;
        this.id = new PieceId();
        hasMoved = false;
    }

    public Piece(PlayerColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.id = new PieceId();
        hasMoved = false;
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

    public boolean hasMoved() {
        return hasMoved;
    }

    public Piece afterMove() {
        return new Piece(this.color, this.type, this.id, true);
    }

    protected Piece(Piece other) {
        this.color = other.color;
        this.id = other.id;
        this.type = other.type;
        this.hasMoved = other.hasMoved;
    }

    private Piece(PlayerColor color, PieceType type, PieceId id, boolean hasMoved) {
        this.color = color;
        this.type = type;
        this.id = id;
        this.hasMoved = hasMoved;
    }
}
