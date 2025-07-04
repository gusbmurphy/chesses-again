package fun.gusmurphy.chesses.engine;

import static fun.gusmurphy.chesses.engine.MoveLegality.*;

class RulesEngine implements ForSubmittingMoves {
    RulesEngine(MoveEvaluationRuleSuite moveEvaluationRuleSuite) {
    }

    @Override
    public MoveLegality submit(Move move) {
        return ILLEGAL;
    }
}
