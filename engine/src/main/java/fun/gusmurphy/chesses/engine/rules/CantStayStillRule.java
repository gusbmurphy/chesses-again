package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

class CantStayStillRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        return null;
    }
}
