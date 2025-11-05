package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent
import spock.lang.Specification

class MoveRuleSpecification extends Specification {

    protected static void evaluationIsLegalAndHasSimpleMoveEffect(RuleEvaluation evaluation, Move move) {
        assert evaluation.isLegal()

        def effect = evaluation.effects().next().get()
        assert (effect instanceof PieceMovedEvent)
        assert effect.newCoordinates() == move.coordinates()
        assert effect.pieceId() == move.pieceId()
    }

    protected static void evaluationIsLegal(RuleEvaluation evaluation) {
        assert evaluation.isLegal()
    }

    protected static void evaluationIsIllegalWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation.isIllegal()
        assert evaluation.effects().areNone()
    }

    protected static void evaluationIsLegalWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation.isLegal()
        assert evaluation.effects().areNone()
    }

    protected static void evaluationIsUnconcernedWithNoEffects(RuleEvaluation evaluation) {
        assert evaluation.isUnconcerned()
        assert evaluation.effects().areNone()
    }

}
