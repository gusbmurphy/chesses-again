package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class QueenMovementRule extends SinglePieceMovementRule {

    public QueenMovementRule() {
        super(PieceType.QUEEN, QueenMovementRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, MoveOnBoard move) {
        Coordinates currentPosition = move.pieceOnBoard().coordinates();
        Coordinates movePosition = move.coordinates();

        if (moveIsLegal(movePosition, currentPosition)) {
            return LEGAL;
        }

        return ILLEGAL;
    }

    private static boolean moveIsLegal(Coordinates movePosition, Coordinates currentPosition) {
        return movePosition.isDiagonalFrom(currentPosition)
                || movePosition.sameFileAs(currentPosition)
                || movePosition.sameRankAs(currentPosition);
    }
}
