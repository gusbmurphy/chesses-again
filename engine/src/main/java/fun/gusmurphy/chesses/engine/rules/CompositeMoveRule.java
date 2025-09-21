package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import java.util.List;

public class CompositeMoveRule implements MoveLegalityRule {

    private final List<MoveLegalityRule> ruleList;

    public CompositeMoveRule(List<MoveLegalityRule> ruleList) {
        this.ruleList = ruleList;
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        for (MoveLegalityRule rule : ruleList) {
            MoveLegality ruling = rule.evaluate(boardState, move);
            if (ruling == MoveLegality.ILLEGAL) {
                return ruling;
            }
        }

        return MoveLegality.LEGAL;
    }
}
