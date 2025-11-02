package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceId;

public class PieceMovedEvent implements BoardStateEvent {

    private final PieceId pieceId;
    private final Coordinates newCoordinates;

    public PieceMovedEvent(PieceId pieceId, Coordinates newCoordinates) {
        this.pieceId = pieceId;
        this.newCoordinates = newCoordinates;
    }

    public static PieceMovedEvent from(Move move) {
        return new PieceMovedEvent(move.pieceId(), move.coordinates());
    }

    public PieceId pieceId() {
        return pieceId;
    }

    public Coordinates newCoordinates() {
        return newCoordinates;
    }
}
