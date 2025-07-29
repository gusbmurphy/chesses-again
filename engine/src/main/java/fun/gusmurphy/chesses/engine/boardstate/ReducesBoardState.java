package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.events.BoardStateEvent;

public interface ReducesBoardState {

    BoardState reduce(BoardState boardState, BoardStateEvent event);

}
