package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RuleEvaluation {
    private final Legality legality;
    private final Effects effects;

    private final static RuleEvaluation ILLEGAL_EVALUATION = new RuleEvaluation(
        Legality.ILLEGAL,
        Effects.emptyEffects()
    );

    private final static RuleEvaluation LEGAL_EVALUATION_WITH_NO_EFFECTS = new RuleEvaluation(
        Legality.LEGAL,
        Effects.emptyEffects()
    );

    private final static RuleEvaluation UNCONCERNED_EVALUATION = new RuleEvaluation(
        Legality.UNCONCERNED,
        Effects.emptyEffects()
    );

    private RuleEvaluation(Legality legality, Effects effects) {
        this.legality = legality;
        this.effects = effects;
    }

    public static RuleEvaluation legalWithMove(Move move) {
        PieceMovedEvent event = new PieceMovedEvent(move.pieceId(), move.coordinates());
        return new RuleEvaluation(
            Legality.LEGAL,
            new Effects(event)
        );
    }

    public static RuleEvaluation legalWithEffectsFromEvents(BoardStateEvent... events) {
        return new RuleEvaluation(
            Legality.LEGAL,
            new Effects(events)
        );
    }

    public static RuleEvaluation legalWithNoEffects() {
        return LEGAL_EVALUATION_WITH_NO_EFFECTS;
    }

    public static RuleEvaluation illegal() {
        return ILLEGAL_EVALUATION;
    }

    public static RuleEvaluation unconcerned() {
        return UNCONCERNED_EVALUATION;
    }

    public Legality legality() {
        return legality;
    }

    public Effects effects() {
        return effects;
    }

    public enum Legality {
        LEGAL, ILLEGAL, UNCONCERNED
    }

    public static class Effects {
        private final List<BoardStateEvent> eventList;
        private int nextEffectIndex = 0;

        private Effects(BoardStateEvent... events) {
            eventList = new ArrayList<>();
            eventList.addAll(Arrays.asList(events));
        }

        public Optional<BoardStateEvent> next() {
            if (nextEffectIndex >= eventList.size()) {
                return Optional.empty();
            }
            return Optional.of(eventList.get(nextEffectIndex++));
        }

        protected static Effects emptyEffects() {
            return new Effects();
        }

        public boolean areNone() {
            return true;
        }
    }
}
