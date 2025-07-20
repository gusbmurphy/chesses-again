package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import static fun.gusmurphy.chesses.engine.rules.MoveLegality.*;

public class RookMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        return LEGAL;
    }
}
