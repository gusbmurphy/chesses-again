package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.rules.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.rules.MoveLegality

class LegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.LEGAL
    }

}
