package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.MoveLegality

class IllegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.ILLEGAL
    }

}
