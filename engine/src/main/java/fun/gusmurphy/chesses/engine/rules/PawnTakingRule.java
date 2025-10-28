package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnTakingRule extends SinglePieceMovementRule {

    public PawnTakingRule() {
        super(PieceType.PAWN);
    }

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        BoardCoordinateState coordinateState = boardState
            .coordinateStates().forCoordinates(move.coordinates()).get();

        if (coordinateState.isUnoccupied()) {
            return RuleEvaluation.UNCONCERNED;
        }

        PieceOnBoard movingPiece = boardState.pieceOnBoardForId(move.pieceId()).get();
        Coordinates currentPieceCoordinates = movingPiece.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (!moveCoordinates.isDiagonalFrom(currentPieceCoordinates)) {
            return RuleEvaluation.ILLEGAL;
        }

        int verticalMovement = moveCoordinates.rankDifferenceTo(currentPieceCoordinates);
        if (Math.abs(verticalMovement) > 1) {
            return RuleEvaluation.ILLEGAL;
        }

        PlayerColor movingPieceColor = movingPiece.color();
        if (PawnMovementRule.verticalMovementDirectionIsOkayForColor(verticalMovement, movingPieceColor)) {
            return RuleEvaluation.LEGAL;
        }

        return RuleEvaluation.ILLEGAL;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }
}
