package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public interface MoveRule {

    RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move);

    default boolean isRelevantForPieceType(PieceType pieceType) {
        return true;
    }

    default boolean overrides(MoveRule otherRule) {
        return false;
    }
}
