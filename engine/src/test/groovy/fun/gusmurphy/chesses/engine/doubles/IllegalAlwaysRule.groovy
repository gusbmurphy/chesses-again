package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.MoveRule
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation

class IllegalAlwaysRule implements MoveRule {

    private final PieceType relevantPieceType

    IllegalAlwaysRule() {
        relevantPieceType = null
    }

    IllegalAlwaysRule(PieceType relevantPieceType) {
        this.relevantPieceType = relevantPieceType
    }

    @Override
    RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        return RuleEvaluation.ILLEGAL_EVALUATION
    }

    @Override
    boolean isRelevantForPieceType(PieceType pieceType) {
        if (relevantPieceType == null) {
            return true
        }

        return pieceType == relevantPieceType
    }
}
