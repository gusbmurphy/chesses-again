package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.events.BoardStateEvents;
import fun.gusmurphy.chesses.engine.events.ReducesBoardState;

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
        BoardState resultingBoardState = boardState;

        for (BoardStateEvent event : events.inOrder()) {
            resultingBoardState = boardStateReducer.reduce(resultingBoardState, event);
        }

        return resultingBoardState;
    }

}
