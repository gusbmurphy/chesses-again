package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class BishopMovementRule extends SinglePieceMovementRule {

    public BishopMovementRule() {
        super(PieceType.BISHOP, BishopMovementRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, MoveOnBoard move) {
        PieceOnBoard piece = move.pieceOnBoard();
        Coordinates moveCoordinates = move.coordinates();

        if (moveCoordinates.sameRankAs(piece.coordinates())) {
            return ILLEGAL;
        }

        if (moveCoordinates.sameFileAs(piece.coordinates())) {
            return ILLEGAL;
        }

        if (!moveCoordinates.isDiagonalFrom(piece.coordinates())) {
            return ILLEGAL;
        }

        return LEGAL;
    }
}
