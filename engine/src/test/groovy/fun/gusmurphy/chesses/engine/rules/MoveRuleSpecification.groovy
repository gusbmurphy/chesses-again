package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

class MoveRuleSpecification extends Specification {

    protected static void evaluationIsLegal(Legality evaluation) {
        assert evaluation == Legality.LEGAL
    }

    protected static void evaluationIsIllegal(Legality evaluation) {
        assert evaluation == Legality.ILLEGAL
    }

    protected static void evaluationIsUnconcerned(Legality evaluation) {
        assert evaluation == Legality.UNCONCERNED
    }

}
