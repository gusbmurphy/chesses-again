package fun.gusmurphy.chesses.engine.rules;

public class RuleEvaluation {
    private final Legality legality;
    private final Effects effects;

    private final static RuleEvaluation ILLEGAL_EVALUATION = new RuleEvaluation(
        Legality.ILLEGAL,
        Effects.emptyEffects()
    );

    private final static RuleEvaluation LEGAL_EVALUATION = new RuleEvaluation(
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

    public static RuleEvaluation legal() {
        return LEGAL_EVALUATION;
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
        LEGAL, ILLEGAL, UNCONCERNED;
    }

    public static class Effects {
        protected static Effects emptyEffects() {
            return new Effects();
        }

        public boolean areNone() {
            return true;
        }
    }
}
