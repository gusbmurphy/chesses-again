package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class PawnTakingRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        return null;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }
}
