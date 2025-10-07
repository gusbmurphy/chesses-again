package fun.gusmurphy.chesses.engine.rules

import spock.lang.Specification

class PawnTakingRuleSpec extends Specification {

    def "the rule overrides the pawn movement rule"() {
        given:
        MoveRule rule = new PawnTakingRule()

        expect:
        rule.overrides(new PawnMovementRule())
    }

}
