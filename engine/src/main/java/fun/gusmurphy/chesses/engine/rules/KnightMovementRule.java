package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class KnightMovementRule extends SinglePieceMovementRule {

    public KnightMovementRule() {
        super(PieceType.KNIGHT);
    }

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId()).get();

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();
        int verticalChangeWithMove = Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates));

        if (verticalChangeWithMove > 2 || verticalChangeWithMove == 0) {
            return RuleEvaluation.ILLEGAL;
        }

        int requiredHorizontalMove = verticalChangeWithMove == 2 ? 1 : 2;
        int horizontalChangeWithMove = Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates));
        if (horizontalChangeWithMove != requiredHorizontalMove) {
            return RuleEvaluation.ILLEGAL;
        }

        return RuleEvaluation.LEGAL;
    }

}
