package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnTakingRule extends SinglePieceMovementRule {

    public PawnTakingRule() {
        super(PieceType.PAWN, PawnTakingRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, MoveOnBoard move) {
        BoardCoordinateState coordinateState =
                boardState.coordinateStates().forCoordinates(move.coordinates()).get();

        if (coordinateState.isUnoccupied()) {
            return UNCONCERNED;
        }

        PieceOnBoard movingPiece = move.pieceOnBoard();
        Coordinates currentPieceCoordinates = movingPiece.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (!moveCoordinates.isDiagonalFrom(currentPieceCoordinates)) {
            return ILLEGAL;
        }

        int verticalMovement = moveCoordinates.rankDifferenceTo(currentPieceCoordinates);
        if (Math.abs(verticalMovement) > 1) {
            return ILLEGAL;
        }

        PlayerColor movingPieceColor = movingPiece.color();
        if (PawnMovementRule.verticalMovementDirectionIsOkayForColor(
                verticalMovement, movingPieceColor)) {
            return LEGAL;
        }

        return ILLEGAL;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }
}
