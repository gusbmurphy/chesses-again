package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvents;

public class MoveApplicator implements AppliesMoves {

    private final DerivesMoveEvents eventDeriver;
    private final ReducesBoardState boardStateReducer;

    public MoveApplicator(DerivesMoveEvents eventDeriver, ReducesBoardState boardStateReducer) {
        this.eventDeriver = eventDeriver;
        this.boardStateReducer = boardStateReducer;
    }

    @Override
    public BoardState applyMoveToBoard(Move move, BoardState boardState) {
        BoardStateEvents events = eventDeriver.deriveEventsFrom(move, boardState);
        return boardStateReducer.reduce(boardState, events);
    }

}
