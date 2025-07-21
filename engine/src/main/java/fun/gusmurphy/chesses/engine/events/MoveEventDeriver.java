package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.DerivesMoveEvents;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class MoveEventDeriver implements DerivesMoveEvents {

    @Override
    public BoardStateEvents deriveEventsFrom(Move move, BoardState boardState) {
        PieceMovedEvent event = new PieceMovedEvent(move.pieceId(), move.coordinates());
        return new BoardStateEvents(event);
    }

}
