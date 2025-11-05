package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.events.BoardStateEvent
import fun.gusmurphy.chesses.engine.rules.MoveRule
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation

class LegalAlwaysWithEffectsRule implements MoveRule {

    private final BoardStateEvent[] events

    LegalAlwaysWithEffectsRule(BoardStateEvent... eventsForEffect) {
        this.events = eventsForEffect
    }

    @Override
    RuleEvaluation evaluate(BoardState boardState, Move move) {
        return RuleEvaluation.legalWithEffectsFromEvents(events)
    }
}
