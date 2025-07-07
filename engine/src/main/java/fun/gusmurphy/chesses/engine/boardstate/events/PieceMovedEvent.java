package fun.gusmurphy.chesses.engine.boardstate.events;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceId;

public class PieceMovedEvent implements BoardStateEvent {

    private final PieceId pieceId;
    private final Coordinates newCoordinates;

    public PieceMovedEvent(PieceId pieceId, Coordinates newCoordinates) {
        this.pieceId = pieceId;
        this.newCoordinates = newCoordinates;
    }

    public PieceId pieceId() {
        return pieceId;
    }

    public Coordinates newCoordinates() {
        return newCoordinates;
    }

}
