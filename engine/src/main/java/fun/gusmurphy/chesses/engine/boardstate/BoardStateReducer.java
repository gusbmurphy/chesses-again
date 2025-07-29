package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.events.*;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;

import java.util.Optional;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        BoardState result = boardState.copy();

        if (event instanceof PieceMovedEvent) {
            handlePieceMovedEvent((PieceMovedEvent) event, result);
        } else if (event instanceof PieceRemovedEvent) {
            handlePieceRemovedEvent((PieceRemovedEvent) event, result);
        } else if (event instanceof TurnChangeEvent) {
            handleTurnChangeEvent((TurnChangeEvent) event, result);
        }

        return result;
    }

    private static void handlePieceMovedEvent(PieceMovedEvent event, BoardState result) {
        PieceId pieceId = event.pieceId();

        if (result.pieces.stream().noneMatch(p -> p.id() == pieceId)) {
            throw new UnknownPieceException();
        }

        result.coordinatesForPieces.put(pieceId, event.newCoordinates());
    }

    private static void handlePieceRemovedEvent(PieceRemovedEvent event, BoardState result) {
        PieceId pieceId = event.pieceId();
        Optional<Piece> pieceToRemove = result.pieces.stream().filter(p -> p.id() == pieceId).findFirst();

        if (pieceToRemove.isPresent()) {
            result.pieces.remove(pieceToRemove.get());
        } else {
            throw new UnknownPieceException();
        }
    }

    private static void handleTurnChangeEvent(TurnChangeEvent event, BoardState result) {
        result.currentTurnColor = event.newTurnColor();
    }

}
