package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.events.*;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState board, BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            return handlePieceMovedEvent((PieceMovedEvent) event, board);
        }
        if (event instanceof PieceRemovedEvent) {
            return handlePieceRemovedEvent((PieceRemovedEvent) event, board);
        }
        if (event instanceof TurnChangeEvent) {
            return handleTurnChangeEvent((TurnChangeEvent) event, board);
        }

        throw new UnsupportedOperationException("Unknown event type");
    }

    private static BoardState handlePieceMovedEvent(PieceMovedEvent event, BoardState board) {
        return board.movePiece(event.pieceId(), event.newCoordinates());
    }

    private static BoardState handlePieceRemovedEvent(PieceRemovedEvent event, BoardState board) {
        return board.removePiece(event.pieceId());
    }

    private static BoardState handleTurnChangeEvent(TurnChangeEvent event, BoardState board) {
        return board.changeTurn(event.newTurnColor());
    }
}
