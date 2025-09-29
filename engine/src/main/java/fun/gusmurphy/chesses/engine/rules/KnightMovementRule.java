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
    public Legality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        if (pieceOnBoard.type() != PieceType.KNIGHT) {
            return Legality.UNCONCERNED;
        }

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();
        int verticalChangeWithMove = Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates));

        if (verticalChangeWithMove > 2 || verticalChangeWithMove == 0) {
            return Legality.ILLEGAL;
        }

        int requiredHorizontalMove = verticalChangeWithMove == 2 ? 1 : 2;
        int horizontalChangeWithMove = Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates));
        if (horizontalChangeWithMove != requiredHorizontalMove) {
            return Legality.ILLEGAL;
        }

        return Legality.LEGAL;
    }

}
