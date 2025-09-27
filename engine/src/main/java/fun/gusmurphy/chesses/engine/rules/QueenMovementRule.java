package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

class QueenMovementRule extends SinglePieceMovementRule {

    public QueenMovementRule() {
        super(PieceType.QUEEN);
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        Coordinates currentPosition = getCurrentPiecePosition(boardState, move);
        Coordinates movePosition = move.coordinates();

        if (moveIsLegal(movePosition, currentPosition)) {
            return MoveLegality.LEGAL;
        }

        return MoveLegality.ILLEGAL;
    }

    private static boolean moveIsLegal(Coordinates movePosition, Coordinates currentPosition) {
        return movePosition.isDiagonalFrom(currentPosition)
            || movePosition.sameFileAs(currentPosition)
            || movePosition.sameRankAs(currentPosition);
    }

    private static Coordinates getCurrentPiecePosition(BoardState boardState, Move move) {
        return boardState.pieceOnBoardForId(move.pieceId()).coordinates();
    }
}
