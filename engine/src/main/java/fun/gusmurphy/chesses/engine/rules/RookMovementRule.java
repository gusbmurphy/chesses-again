package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class RookMovementRule extends SinglePieceMovementRule {

    public RookMovementRule() {
        super(PieceType.ROOK, RookMovementRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, MoveOnBoard move) {
        Coordinates pieceCoordinates = move.pieceOnBoard().coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (moveCoordinates.sameRankAs(pieceCoordinates)) {
            return LEGAL;
        }

        if (moveCoordinates.sameFileAs(pieceCoordinates)) {
            return LEGAL;
        }

        return ILLEGAL;
    }
}
