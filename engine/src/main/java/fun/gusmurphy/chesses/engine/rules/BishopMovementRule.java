package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import static fun.gusmurphy.chesses.engine.rules.MoveLegality.*;

public class BishopMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        Coordinates currentPieceCoordinates = boardState
            .allCoordinateStates()
            .forPieceId(move.pieceId())
            .get().coordinates();

        Coordinates moveCoordinates = move.coordinates();

        if (moveCoordinates.sameRankAs(currentPieceCoordinates)) {
            return ILLEGAL;
        }

        if (moveCoordinates.sameFileAs(currentPieceCoordinates)) {
            return ILLEGAL;
        }

        if (Math.abs(moveCoordinates.fileDifferenceTo(currentPieceCoordinates)) != Math.abs(currentPieceCoordinates.fileDifferenceTo(currentPieceCoordinates))) {
            return ILLEGAL;
        }

        return LEGAL;
    }
}
