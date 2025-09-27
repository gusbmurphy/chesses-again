package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.MoveRule
import fun.gusmurphy.chesses.engine.rules.Legality

class LegalAlwaysRule implements MoveRule {

    private final PieceType relevantPieceType

    LegalAlwaysRule() {
        relevantPieceType = null
    }

    LegalAlwaysRule(PieceType relevantPieceType) {
        this.relevantPieceType = relevantPieceType
    }

    @Override
    Legality evaluate(BoardState boardState, Move move) {
        return Legality.LEGAL
    }

    @Override
    boolean isRelevantForPieceType(PieceType pieceType) {
        if (relevantPieceType == null) {
            return true
        }

        return pieceType == relevantPieceType
    }

}
