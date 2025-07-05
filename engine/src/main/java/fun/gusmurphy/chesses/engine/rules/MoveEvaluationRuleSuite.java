package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class MoveEvaluationRuleSuite implements MoveEvaluationRule {

    private final MoveEvaluationRule[] rules;

    public MoveEvaluationRuleSuite(MoveEvaluationRule... rules) {
        this.rules = rules;
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        if (rules.length < 1) {
            return MoveLegality.LEGAL;
        }

        for (MoveEvaluationRule rule : rules) {
            if (rule.evaluate(boardState, move) == MoveLegality.ILLEGAL) {
                return MoveLegality.ILLEGAL;
            }
        }

        return MoveLegality.LEGAL;
    }

}
