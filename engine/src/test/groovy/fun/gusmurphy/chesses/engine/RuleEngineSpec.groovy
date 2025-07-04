package fun.gusmurphy.chesses.engine

import spock.lang.Specification
import static fun.gusmurphy.chesses.engine.MoveLegality.*

class RuleEngineSpec extends Specification {

    def "with no evaluation rules, a move is legal"() {
        given:
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite()
        ForSubmittingMoves engine = new RulesEngine(ruleSuite)

        expect:
        engine.submit(new Move()) == LEGAL
    }

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

    def "with two move evaluation rules, a move is legal if both allow it"() {
        given:
        MoveEvaluationRule ruleOne = new AlwaysLegalEvaluationRule()
        MoveEvaluationRule ruleTwo = new AlwaysLegalEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(ruleOne, ruleTwo)
        ForSubmittingMoves engine = new RulesEngine(ruleSuite)

        when:
        def result = engine.submit(new Move())

        then:
        result == LEGAL
    }

    def "with two move evaluation rules, a move is illegal if one does not allow it"() {
        given:
        MoveEvaluationRule alwaysLegal = new AlwaysLegalEvaluationRule()
        MoveEvaluationRule alwaysIllegal = new AlwaysIllegalEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(alwaysLegal, alwaysIllegal)
        ForSubmittingMoves engine = new RulesEngine(ruleSuite)

        when:
        def result = engine.submit(new Move())

        then:
        result == ILLEGAL
    }

}
