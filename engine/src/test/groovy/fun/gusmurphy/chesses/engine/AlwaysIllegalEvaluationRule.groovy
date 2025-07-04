package fun.gusmurphy.chesses.engine

class AlwaysIllegalEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.ILLEGAL
    }

}
