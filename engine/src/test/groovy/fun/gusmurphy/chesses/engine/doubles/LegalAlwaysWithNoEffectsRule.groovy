package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.MoveRule
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation

class LegalAlwaysWithNoEffectsRule implements MoveRule {

    private final PieceType relevantPieceType

    LegalAlwaysWithNoEffectsRule() {
        relevantPieceType = null
    }

    LegalAlwaysWithNoEffectsRule(PieceType relevantPieceType) {
        this.relevantPieceType = relevantPieceType
    }

    @Override
    RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        return RuleEvaluation.legalWithNoEffects()
    }

    @Override
    boolean isRelevantForPieceType(PieceType pieceType) {
        if (relevantPieceType == null) {
            return true
        }

        return pieceType == relevantPieceType
    }

}
