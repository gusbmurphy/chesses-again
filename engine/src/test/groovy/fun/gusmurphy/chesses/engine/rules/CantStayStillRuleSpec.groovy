package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

class CantStayStillRuleSpec extends Specification {

    def "the rule is relevant for all piece types"() {
        given:
        MoveRule rule = new CantStayStillRule()

        expect:
        rule.isRelevantForPieceType(type)

        where:
        type << PieceType.values()
    }

}
