package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

class MoveRuleSpecification extends Specification {

    protected static void evaluationIsLegal(RuleEvaluation evaluation) {
        assert evaluation == RuleEvaluation.LEGAL
    }

    protected static void evaluationIsIllegalWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation == RuleEvaluation.ILLEGAL
        assert evaluation.effects().areNone()
    }

    protected static void evaluationIsUnconcerned(RuleEvaluation evaluation) {
        assert evaluation == RuleEvaluation.UNCONCERNED
    }

}
