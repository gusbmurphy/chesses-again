package fun.gusmurphy.chesses.engine.piece;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;

public class PieceOnBoard extends Piece {

    private final Coordinates coordinates;

    public PieceOnBoard(Piece piece, Coordinates coordinates) {
        super(piece);
        this.coordinates = coordinates;
    }

    public Coordinates coordinates() {
        return coordinates;
    }

}
