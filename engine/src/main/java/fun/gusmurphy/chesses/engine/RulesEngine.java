package fun.gusmurphy.chesses.engine;

class RulesEngine implements ForSubmittingMoves {

    private final MoveEvaluationRuleSuite evaluationRules;

    RulesEngine(MoveEvaluationRuleSuite moveEvaluationRuleSuite) {
        this.evaluationRules = moveEvaluationRuleSuite;
    }

    @Override
    public MoveLegality submit(Move move) {
        return evaluationRules.evaluate();
    }

}
