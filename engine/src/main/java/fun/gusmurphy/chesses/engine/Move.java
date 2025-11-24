package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceId;

// TODO: What if there was a decorated move or something that contained the moving piece and the
// state of the coordinates?
public class Move {

    private final PieceId pieceId;
    private final Coordinates coordinates;

    public Move(PieceId pieceId, Coordinates coordinates) {
        this.pieceId = pieceId;
        this.coordinates = coordinates;
    }

    public PieceId pieceId() {
        return pieceId;
    }

    public Coordinates coordinates() {
        return coordinates;
    }
}
