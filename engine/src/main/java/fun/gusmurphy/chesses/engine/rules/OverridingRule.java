package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import java.util.Arrays;

public class OverridingRule implements MoveRule {

    private final MoveRule baseRule;
    private final MoveRule[] overriddenRules;

    public OverridingRule(MoveRule baseRule, MoveRule... overriddenRules) {
        this.baseRule = baseRule;
        this.overriddenRules = overriddenRules;
    }

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        return baseRule.evaluate(boardState, move);
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return Arrays.stream(overriddenRules).anyMatch(rule -> rule == otherRule);
    }
}
