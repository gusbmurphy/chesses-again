package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation
import fun.gusmurphy.chesses.engine.rules.MoveRule

class UnconcernedAlwaysRule implements MoveRule {
    @Override
    RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        return RuleEvaluation.UNCONCERNED_EVALUATION
    }
}
