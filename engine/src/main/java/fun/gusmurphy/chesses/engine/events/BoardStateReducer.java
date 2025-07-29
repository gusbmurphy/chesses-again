package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.UnknownPieceException;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;

import java.util.Optional;

public class BoardStateReducer implements ReducesBoardState {

    @Override
    public BoardState reduce(BoardState boardState, BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            PieceId pieceId = ((PieceMovedEvent) event).pieceId();

            if (boardState.pieces.stream().noneMatch(p -> p.id() == pieceId)) {
                throw new UnknownPieceException();
            }

            boardState.coordinatesForPieces.put(pieceId, ((PieceMovedEvent) event).newCoordinates());
        } else if (event instanceof PieceRemovedEvent) {
            PieceId pieceId = ((PieceRemovedEvent) event).pieceId();
            Optional<Piece> pieceToRemove = boardState.pieces.stream().filter(p -> p.id() == pieceId).findFirst();

            if (pieceToRemove.isPresent()) {
                boardState.pieces.remove(pieceToRemove.get());
            } else {
                throw new UnknownPieceException();
            }
        } else if (event instanceof TurnChangeEvent) {
            boardState.currentTurnColor = ((TurnChangeEvent) event).newTurnColor();
        }

        return boardState;
    }

}
