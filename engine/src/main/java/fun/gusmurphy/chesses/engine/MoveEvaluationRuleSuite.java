package fun.gusmurphy.chesses.engine;

class MoveEvaluationRuleSuite implements MoveEvaluationRule {

    private final MoveEvaluationRule rule;

    public MoveEvaluationRuleSuite(MoveEvaluationRule rule) {
        this.rule = rule;
    }

    @Override
    public MoveLegality evaluate() {
        return rule.evaluate();
    }

}
