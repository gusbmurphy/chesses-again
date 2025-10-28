package fun.gusmurphy.chesses.engine.rules;

public class RuleEvaluation {
    private final Legality legality;
    private final Effects effects;

    public final static RuleEvaluation ILLEGAL = new RuleEvaluation(
        Legality.ILLEGAL,
        Effects.emptyEffects()
    );

    public final static RuleEvaluation LEGAL = new RuleEvaluation(
        Legality.LEGAL,
        Effects.emptyEffects()
    );

    public final static RuleEvaluation UNCONCERNED = new RuleEvaluation(
        Legality.UNCONCERNED,
        Effects.emptyEffects()
    );

    private RuleEvaluation(Legality legality, Effects effects) {
        this.legality = legality;
        this.effects = effects;
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
