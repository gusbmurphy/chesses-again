package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class PawnTakingRuleSpec extends Specification {

    static rule = new PawnTakingRule()

    def "the rule overrides the pawn movement rule"() {
        expect:
        rule.overrides(new PawnMovementRule())
    }

    def "the rule is relevant for pawns"() {
        expect:
        rule.isRelevantForPieceType(PAWN)
    }

    def "the rule is not relevant for non-pawns"() {
        expect:
        rule.isRelevantForPieceType(pieceType) == false

        where:
        pieceType << [KING, KNIGHT, QUEEN, BISHOP, ROOK]
    }

}
