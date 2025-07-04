package fun.gusmurphy.chesses.engine

class IllegalAlwaysEvaluationRule implements MoveEvaluationRule {

    @Override
    MoveLegality evaluate() {
        return MoveLegality.ILLEGAL
    }

}
