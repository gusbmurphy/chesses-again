package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        boardState.apply(event);
        return boardState;
    }

}
