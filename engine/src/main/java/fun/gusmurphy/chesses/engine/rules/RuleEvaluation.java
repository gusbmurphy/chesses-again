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

    private static final RuleEvaluation ILLEGAL_EVALUATION =
            new RuleEvaluation(Legality.ILLEGAL, Effects.emptyEffects());

    private static final RuleEvaluation LEGAL_EVALUATION_WITH_NO_EFFECTS =
            new RuleEvaluation(Legality.LEGAL, Effects.emptyEffects());

    private static final RuleEvaluation UNCONCERNED_EVALUATION =
            new RuleEvaluation(Legality.UNCONCERNED, Effects.emptyEffects());

    private RuleEvaluation(Legality legality, Effects effects) {
        this.legality = legality;
        this.effects = effects;
    }

    public static RuleEvaluation legalWithMove(Move move) {
        PieceMovedEvent event = new PieceMovedEvent(move.pieceId(), move.coordinates());
        return new RuleEvaluation(Legality.LEGAL, new Effects(event));
    }

    public static RuleEvaluation legalWithEffectsFromEvents(BoardStateEvent... events) {
        return new RuleEvaluation(Legality.LEGAL, new Effects(events));
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

    public RuleEvaluation combineWith(RuleEvaluation other) {
        Effects combinedEffects = effects.combineWith(other.effects);

        Legality newLegality;
        if (legality == Legality.ILLEGAL || other.legality == Legality.ILLEGAL) {
            newLegality = Legality.ILLEGAL;
        } else if (legality == Legality.UNCONCERNED && other.legality == Legality.UNCONCERNED) {
            newLegality = Legality.UNCONCERNED;
        } else {
            newLegality = Legality.LEGAL;
        }

        return new RuleEvaluation(newLegality, combinedEffects);
    }

    public Legality legality() {
        return legality;
    }

    public boolean isIllegal() {
        return legality == Legality.ILLEGAL;
    }

    public boolean isLegal() {
        return legality == Legality.LEGAL;
    }

    public boolean isNotLegal() {
        return legality != Legality.LEGAL;
    }

    public boolean isUnconcerned() {
        return legality == Legality.UNCONCERNED;
    }

    public Effects effects() {
        return effects;
    }

    public enum Legality {
        LEGAL,
        ILLEGAL,
        UNCONCERNED
    }

    public static class Effects {
        private final BoardStateEvent[] events;
        private int nextEffectIndex = 0;

        private Effects(BoardStateEvent... events) {
            this.events = events;
        }

        public Optional<BoardStateEvent> next() {
            if (nextEffectIndex >= events.length) {
                return Optional.empty();
            }
            return Optional.of(events[nextEffectIndex++]);
        }

        protected static Effects emptyEffects() {
            return new Effects();
        }

        protected Effects combineWith(Effects other) {
            List<BoardStateEvent> allEvents = new ArrayList<>(Arrays.asList(events));
            allEvents.addAll(new ArrayList<>(Arrays.asList(other.events)));
            return new Effects(allEvents.toArray(new BoardStateEvent[0]));
        }

        public boolean areNone() {
            return true;
        }
    }
}
