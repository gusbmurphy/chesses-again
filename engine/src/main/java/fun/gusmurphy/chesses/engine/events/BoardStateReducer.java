package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            boardState.handlePieceMoved((PieceMovedEvent) event);
        } else if (event instanceof PieceRemovedEvent) {
            boardState.handlePieceRemoved((PieceRemovedEvent) event);
        } else if (event instanceof TurnChangeEvent) {
            boardState.handleTurnChange((TurnChangeEvent) event);
        }

        return boardState;
    }

}
