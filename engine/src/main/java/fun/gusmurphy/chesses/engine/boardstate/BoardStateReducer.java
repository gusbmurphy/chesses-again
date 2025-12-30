package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.events.*;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import java.util.Optional;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        BoardState newBoardState = boardState.copy();

        if (event instanceof PieceMovedEvent) {
            return handlePieceMovedEvent((PieceMovedEvent) event, boardState);
        } else if (event instanceof PieceRemovedEvent) {
            handlePieceRemovedEvent((PieceRemovedEvent) event, newBoardState);
        } else if (event instanceof TurnChangeEvent) {
            handleTurnChangeEvent((TurnChangeEvent) event, newBoardState);
        }

        return newBoardState;
    }

    private static BoardState handlePieceMovedEvent(PieceMovedEvent event, BoardState boardState) {
        return boardState.movePiece(event.pieceId(), event.newCoordinates());
    }

    private static void handlePieceRemovedEvent(PieceRemovedEvent event, BoardState boardState) {
        PieceId pieceId = event.pieceId();
        Optional<Piece> pieceToRemove =
                boardState.pieces.stream().filter(p -> p.id() == pieceId).findFirst();

        if (pieceToRemove.isPresent()) {
            boardState.pieces.remove(pieceToRemove.get());
            boardState.coordinatesForPieces.remove(pieceId);
        } else {
            throw new UnknownPieceException();
        }
    }

    private static void handleTurnChangeEvent(TurnChangeEvent event, BoardState boardState) {
        boardState.currentTurnColor = event.newTurnColor();
    }
}
