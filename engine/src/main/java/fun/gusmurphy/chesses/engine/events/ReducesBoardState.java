package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public interface ReducesBoardState {

    BoardState reduce(BoardState boardState, BoardStateEvent event);

}
