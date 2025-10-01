package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class KingMovementRule extends SinglePieceMovementRule {

    public KingMovementRule() {
        super(PieceType.KING);
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates)) > 1) {
            return Legality.ILLEGAL;
        }

        if (Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates)) > 1) {
            return Legality.ILLEGAL;
        }

        return Legality.LEGAL;
    }
}
