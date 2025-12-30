package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.events.*;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        BoardState newBoardState = boardState.copy();

        if (event instanceof PieceMovedEvent) {
            return handlePieceMovedEvent((PieceMovedEvent) event, boardState);
        } else if (event instanceof PieceRemovedEvent) {
            return handlePieceRemovedEvent((PieceRemovedEvent) event, newBoardState);
        } else if (event instanceof TurnChangeEvent) {
            handleTurnChangeEvent((TurnChangeEvent) event, newBoardState);
        }

        return newBoardState;
    }

    private static BoardState handlePieceMovedEvent(PieceMovedEvent event, BoardState boardState) {
        return boardState.movePiece(event.pieceId(), event.newCoordinates());
    }

    private static BoardState handlePieceRemovedEvent(
            PieceRemovedEvent event, BoardState boardState) {
        return boardState.removePiece(event.pieceId());
    }

    private static void handleTurnChangeEvent(TurnChangeEvent event, BoardState boardState) {
        boardState.currentTurnColor = event.newTurnColor();
    }
}
