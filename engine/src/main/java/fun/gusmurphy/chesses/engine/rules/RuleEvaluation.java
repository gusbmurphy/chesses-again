package fun.gusmurphy.chesses.engine.rules;

public enum RuleEvaluation {
    LEGAL, ILLEGAL, UNCONCERNED;

    public Effects effects() {
        return new Effects();
    }

    public static class Effects {
        public boolean areNone() {
            return true;
        }
    }
}
