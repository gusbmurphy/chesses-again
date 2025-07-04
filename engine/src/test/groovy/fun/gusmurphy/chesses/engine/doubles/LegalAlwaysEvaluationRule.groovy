package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.MoveLegality

class LegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.LEGAL
    }

}
