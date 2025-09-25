package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.rules.AllRelevantPieceTypes
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule
import fun.gusmurphy.chesses.engine.rules.RelevantPieceTypes

class UnconcernedAlwaysRule implements MoveLegalityRule {
    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.UNCONCERNED
    }

    @Override
    RelevantPieceTypes relevantPieceTypes() {
        return new AllRelevantPieceTypes()
    }
}
