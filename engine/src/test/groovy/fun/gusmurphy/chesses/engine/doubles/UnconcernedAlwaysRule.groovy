package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule

class UnconcernedAlwaysRule implements MoveLegalityRule {
    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.UNCONCERNED
    }
}
