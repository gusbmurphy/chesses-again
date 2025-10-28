package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

class MoveRuleSpecification extends Specification {

    protected static boolean evaluationIsLegal(Legality evaluation) {
        evaluation == Legality.LEGAL
    }

    protected static boolean evaluationIsIllegal(Legality evaluation) {
        evaluation == Legality.ILLEGAL
    }

    protected static boolean evaluationIsUnconcerned(Legality evaluation) {
        evaluation == Legality.UNCONCERNED
    }

}
