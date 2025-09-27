package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class CantMoveThroughSameColorRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        return Legality.ILLEGAL;
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType != PieceType.KNIGHT;
    }
}
