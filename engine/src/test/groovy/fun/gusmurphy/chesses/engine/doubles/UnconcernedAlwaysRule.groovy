package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.rules.Legality
import fun.gusmurphy.chesses.engine.rules.MoveRule

class UnconcernedAlwaysRule implements MoveRule {
    @Override
    Legality evaluate(BoardState boardState, Move move) {
        return Legality.UNCONCERNED
    }
}
