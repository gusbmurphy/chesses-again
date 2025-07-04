package fun.gusmurphy.chesses.engine

import spock.lang.Specification
import static fun.gusmurphy.chesses.engine.MoveLegality.*

class RuleEngineSpec extends Specification {

    def "with just one move evaluation rule, a move is illegal if that one deems it"() {
        given:
        MoveEvaluationRule rule = new AlwaysIllegalEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(rule)
        ForSubmittingMoves engine = new RulesEngine(ruleSuite)

        when:
        def result = engine.submit(new Move())

        then:
        result == ILLEGAL
    }

    def "with just one move evaluation rule, a move is legal if that one deems it"() {
        given:
        MoveEvaluationRule rule = new AlwaysLegalEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(rule)
        ForSubmittingMoves engine = new RulesEngine(ruleSuite)

        when:
        def result = engine.submit(new Move())

        then:
        result == LEGAL
    }

}
