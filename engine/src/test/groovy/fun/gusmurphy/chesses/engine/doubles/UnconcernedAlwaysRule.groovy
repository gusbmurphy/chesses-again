package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation
import fun.gusmurphy.chesses.engine.rules.MoveRule

class UnconcernedAlwaysRule implements MoveRule {
    @Override
    RuleEvaluation evaluate(BoardState boardState, Move move) {
        return RuleEvaluation.UNCONCERNED_EVALUATION
    }
}
