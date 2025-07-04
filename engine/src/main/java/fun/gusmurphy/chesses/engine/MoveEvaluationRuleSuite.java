package fun.gusmurphy.chesses.engine;

class MoveEvaluationRuleSuite implements MoveEvaluationRule {

    private final MoveEvaluationRule[] rules;

    public MoveEvaluationRuleSuite(MoveEvaluationRule... rules) {
        this.rules = rules;
    }

    @Override
    public MoveLegality evaluate() {
        if (rules.length < 1) {
            return MoveLegality.LEGAL;
        }

        return rules[0].evaluate();
    }

}
