package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class CantMoveThroughSameColorRuleSpec extends Specification {

    def "the rule is for all pieces except knights"() {
        given:
        MoveRule rule = new CantMoveThroughSameColorRule()

        expect:
        rule.isRelevantForPieceType(type) == expected

        where:
        type   | expected
        KNIGHT | false
        QUEEN  | true
        PAWN   | true
        KING   | true
        BISHOP | true
        ROOK   | true
    }

}
