package fun.gusmurphy.chesses.engine

class AlwaysLegalEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.LEGAL
    }

}
