package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvents;

public interface ReducesBoardState {

    BoardState reduce(BoardState boardState, BoardStateEvents events);

}
