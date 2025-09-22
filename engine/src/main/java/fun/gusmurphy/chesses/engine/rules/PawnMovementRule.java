package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        if (pieceOnBoard.type() != PieceType.PAWN) {
            return MoveLegality.UNCONCERNED;
        }

        return MoveLegality.LEGAL;
    }
}
