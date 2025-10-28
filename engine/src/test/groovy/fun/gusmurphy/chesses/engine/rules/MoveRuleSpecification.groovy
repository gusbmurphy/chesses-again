package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.events.BoardStateEvent
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent
import spock.lang.Specification

class MoveRuleSpecification extends Specification {

    protected static void evaluationIsLegal(RuleEvaluation evaluation) {
        assert evaluation.legality() == RuleEvaluation.Legality.LEGAL
    }

    protected static void evaluationIsLegalAndHasSimpleMoveEffect(RuleEvaluation evaluation, Move move) {
        assert evaluation.legality() == RuleEvaluation.Legality.LEGAL

        BoardStateEvent effect = evaluation.effects().next()
        assert (effect instanceof PieceMovedEvent)
    }

    protected static void evaluationIsIllegalWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation.legality() == RuleEvaluation.Legality.ILLEGAL
        assert evaluation.effects().areNone()
    }

    protected static void evaluationIsUnconcernedWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation.legality() == RuleEvaluation.Legality.UNCONCERNED
        assert evaluation.effects().areNone()
    }

}
