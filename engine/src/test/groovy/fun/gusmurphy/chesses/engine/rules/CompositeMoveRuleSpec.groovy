package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.doubles.IllegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.LegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.UnconcernedAlwaysRule
import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

class CompositeMoveRuleSpec extends Specification {

    def "the composite move rule returns legal if all the rules say it's legal, or don't care"() {
        given:
        MoveLegalityRule compositeRule = new CompositeMoveRule(List.of(
            new LegalAlwaysRule(), new LegalAlwaysRule(), new UnconcernedAlwaysRule()
        ))

        def board = new BoardStateBuilder().build()

        expect:
        compositeRule.evaluate(board, new Move(new PieceId(), Coordinates.A1)) == MoveLegality.LEGAL
    }

    def "if one rule says illegal, the final result is illegal"() {
        given:
        MoveLegalityRule compositeRule = new CompositeMoveRule(List.of(
            new LegalAlwaysRule(), new IllegalAlwaysRule(), new UnconcernedAlwaysRule()
        ))

        def board = new BoardStateBuilder().build()

        expect:
        compositeRule.evaluate(board, new Move(new PieceId(), Coordinates.A1)) == MoveLegality.ILLEGAL
    }

}
