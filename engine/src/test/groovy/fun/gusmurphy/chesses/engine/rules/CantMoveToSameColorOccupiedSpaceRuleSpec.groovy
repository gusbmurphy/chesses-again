package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

class CantMoveToSameColorOccupiedSpaceRuleSpec extends Specification {

    static MoveRule rule = new CantMoveToSameColorOccupiedSpaceRule()

    def "the rule is relevant for all piece types"() {
        expect:
        rule.isRelevantForPieceType(type)

        where:
        type << PieceType.values()
    }

}
