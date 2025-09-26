package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class QueenMovementRuleSpec extends Specification {

    def "the rule is only concerned with queens"() {
        expect:
        new QueenMovementRule().isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | false
        QUEEN     | true
        PAWN      | false
    }

}
