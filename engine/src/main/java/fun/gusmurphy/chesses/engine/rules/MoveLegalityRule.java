package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public interface MoveLegalityRule {

    MoveLegality evaluate(BoardState boardState, Move move);
    default boolean isRelevantForPieceType(PieceType pieceType) {
        return true;
    }

}
