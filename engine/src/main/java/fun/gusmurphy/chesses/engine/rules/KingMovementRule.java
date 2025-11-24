package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class KingMovementRule extends SinglePieceMovementRule {

    public KingMovementRule() {
        super(PieceType.KING, KingMovementRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, MoveOnBoard move) {
        PieceOnBoard pieceOnBoard = move.pieceOnBoard();

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates)) > 1) {
            return ILLEGAL;
        }

        if (Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates)) > 1) {
            return ILLEGAL;
        }

        return LEGAL;
    }
}
