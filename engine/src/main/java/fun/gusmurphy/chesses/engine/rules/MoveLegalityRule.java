package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public interface MoveLegalityRule {
    MoveLegality evaluate(BoardState boardState, Move move);
}
