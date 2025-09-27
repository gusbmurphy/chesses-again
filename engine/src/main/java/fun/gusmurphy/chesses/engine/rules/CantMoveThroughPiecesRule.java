package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class CantMoveThroughPiecesRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        if (destinationIsOccupied(boardState, move)) {
            return Legality.UNCONCERNED;
        }

        return Legality.ILLEGAL;
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType != PieceType.KNIGHT;
    }

    private static boolean destinationIsOccupied(BoardState boardState, Move move) {
        // TODO: Would a Null Object be better than an Optional for this state?
        return boardState
            .coordinateStates()
            .forCoordinates(move.coordinates())
            .map(BoardCoordinateState::isOccupied)
            .orElse(false);
    }
}
