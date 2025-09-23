package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class KnightMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.LEGAL;
    }
}
