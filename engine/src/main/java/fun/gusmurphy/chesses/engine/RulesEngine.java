package fun.gusmurphy.chesses.engine;

class RulesEngine implements ForSubmittingMoves {

    private final MoveEvaluationRule moveEvaluationRule;

    RulesEngine(MoveEvaluationRule moveEvaluationRule) {
        this.moveEvaluationRule = moveEvaluationRule;
    }

    @Override
    public MoveLegality submit(Move move) {
        return moveEvaluationRule.evaluate();
    }

}
