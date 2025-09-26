package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule
import fun.gusmurphy.chesses.engine.rules.MoveLegality

class IllegalAlwaysRule implements MoveLegalityRule {

    private final PieceType relevantPieceType

    IllegalAlwaysRule() {
        relevantPieceType = null
    }

    IllegalAlwaysRule(PieceType relevantPieceType) {
        this.relevantPieceType = relevantPieceType
    }

    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.ILLEGAL
    }

    @Override
    boolean isRelevantForPieceType(PieceType pieceType) {
        if (relevantPieceType == null) {
            return true
        }

        return pieceType == relevantPieceType
    }
}
