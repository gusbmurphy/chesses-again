package fun.gusmurphy.chesses.engine

class LegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.LEGAL
    }

}
