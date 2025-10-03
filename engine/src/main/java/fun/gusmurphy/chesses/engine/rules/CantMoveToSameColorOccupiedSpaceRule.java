package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.Piece;

import java.util.Optional;

public class CantMoveToSameColorOccupiedSpaceRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        Optional<Piece> occupyingPiece = boardState
            .coordinateStates().forCoordinates(move.coordinates()).flatMap(BoardCoordinateState::piece);

        if (!occupyingPiece.isPresent()) {
            return Legality.LEGAL;
        }

        PlayerColor occupyingColor = occupyingPiece.get().color();
        Piece movingPiece = boardState.pieceOnBoardForId(move.pieceId()).get();

        if (occupyingColor == movingPiece.color()) {
            return Legality.ILLEGAL;
        }

        return Legality.LEGAL;
    }
}
