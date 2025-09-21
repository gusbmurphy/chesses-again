package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import java.util.List;

public class CompositeMoveRule implements MoveLegalityRule {

    public CompositeMoveRule(List<MoveLegalityRule> ruleList) {
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.LEGAL;
    }
}
