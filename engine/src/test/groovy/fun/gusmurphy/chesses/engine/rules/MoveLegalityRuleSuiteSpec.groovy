package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.doubles.IllegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.LegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.UnconcernedAlwaysRule
import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.rules.MoveLegality.ILLEGAL
import static fun.gusmurphy.chesses.engine.rules.MoveLegality.LEGAL

class MoveLegalityRuleSuiteSpec extends Specification {

    private static final DUMMY_BOARD = new BoardStateBuilder().build()
    private static final DUMMY_MOVE = new Move(new PieceId(), Coordinates.A1)
    private static final MoveLegalityRule ALWAYS_LEGAL_RULE = new LegalAlwaysRule()
    private static final MoveLegalityRule ALWAYS_ILLEGAL_RULE = new IllegalAlwaysRule()

    def "with no rules, a move is legal"() {
        given:
        MoveLegalityRuleSuite ruleSuite = new MoveLegalityRuleSuite()

        expect:
        ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE) == LEGAL
    }

    def "with just one move evaluation rule, a move is legal or illegal based on just that one"() {
        given:
        MoveLegalityRuleSuite ruleSuite = new MoveLegalityRuleSuite(rule)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == expected

        where:
        rule                    | expected
        new IllegalAlwaysRule() | ILLEGAL
        new LegalAlwaysRule()   | LEGAL
    }

    def "with two move evaluation rules, a move is legal if both allow it"() {
        given:
        def ruleOne = new LegalAlwaysRule()
        def ruleTwo = new LegalAlwaysRule()
        MoveLegalityRuleSuite ruleSuite = new MoveLegalityRuleSuite(ruleOne, ruleTwo)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == LEGAL
    }

    def "with two move evaluation rules, a move is illegal if one does not allow it"() {
        given:
        MoveLegalityRuleSuite ruleSuite = new MoveLegalityRuleSuite(ALWAYS_LEGAL_RULE, ALWAYS_ILLEGAL_RULE)

        when:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == ILLEGAL
    }

    def "with two move evaluation rules, where one is unconcerned, the other rule decides the legality"() {
        given:
        MoveLegalityRule unconcerned = new UnconcernedAlwaysRule()
        def suite = new MoveLegalityRuleSuite(unconcerned, otherRule)

        when:
        def result = suite.evaluate(DUMMY_BOARD, DUMMY_MOVE)

        then:
        result == expected

        where:
        otherRule           | expected
        ALWAYS_ILLEGAL_RULE | ILLEGAL
        ALWAYS_LEGAL_RULE   | LEGAL
    }

}
