package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class KnightMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        if (pieceOnBoard.type() != PieceType.KNIGHT) {
            return MoveLegality.UNCONCERNED;
        }

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();
        int verticalChangeWithMove = Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates));

        if (verticalChangeWithMove > 2 || verticalChangeWithMove == 0) {
            return MoveLegality.ILLEGAL;
        }

        int requiredHorizontalMove = verticalChangeWithMove == 2 ? 1 : 2;
        int horizontalChangeWithMove = Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates));
        if (horizontalChangeWithMove != requiredHorizontalMove) {
            return MoveLegality.ILLEGAL;
        }

        return MoveLegality.LEGAL;
    }

    @Override
    public RelevantPieceTypes relevantPieceTypes() {
        return null;
    }
}
