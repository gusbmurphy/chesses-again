package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.doubles.IllegalAlwaysEvaluationRule
import fun.gusmurphy.chesses.engine.doubles.LegalAlwaysEvaluationRule
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.MoveLegality.ILLEGAL
import static fun.gusmurphy.chesses.engine.MoveLegality.LEGAL

class MoveEvaluationRuleSuiteSpec extends Specification {

    private static final DUMMY_BOARD = new BoardStateBuilder().build()
    private static final DUMMY_MOVE = new Move(new PieceId())

    def "with no rules, a move is legal"() {
        given:
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite()

        expect:
        ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE) == LEGAL
    }

    def "with just one move evaluation rule, a move is legal or illegal based on just that one"() {
        given:
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(rule)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == expected

        where:
        rule                              | expected
        new IllegalAlwaysEvaluationRule() | ILLEGAL
        new LegalAlwaysEvaluationRule()   | LEGAL
    }

    def "with two move evaluation rules, a move is legal if both allow it"() {
        given:
        MoveEvaluationRule ruleOne = new LegalAlwaysEvaluationRule()
        MoveEvaluationRule ruleTwo = new LegalAlwaysEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(ruleOne, ruleTwo)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == LEGAL
    }

    def "with two move evaluation rules, a move is illegal if one does not allow it"() {
        given:
        MoveEvaluationRule legal = new LegalAlwaysEvaluationRule()
        MoveEvaluationRule illegal = new IllegalAlwaysEvaluationRule()
        MoveEvaluationRuleSuite ruleSuite = new MoveEvaluationRuleSuite(legal, illegal)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == ILLEGAL
    }

}
