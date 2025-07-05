package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.BoardState
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.MoveLegality

class IllegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.ILLEGAL
    }

}
