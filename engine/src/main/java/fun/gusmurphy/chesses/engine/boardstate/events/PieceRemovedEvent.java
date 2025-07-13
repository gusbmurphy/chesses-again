package fun.gusmurphy.chesses.engine.boardstate.events;

import fun.gusmurphy.chesses.engine.piece.PieceId;

public class PieceRemovedEvent implements BoardStateEvent {

    private final PieceId pieceId;

    public PieceRemovedEvent(PieceId pieceId) {
        this.pieceId = pieceId;
    }

    public PieceId pieceId() {
        return pieceId;
    }

}
