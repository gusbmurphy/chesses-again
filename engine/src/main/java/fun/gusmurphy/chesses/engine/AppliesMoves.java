package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public interface AppliesMoves {

    void applyMoveToBoard(Move move, BoardState boardState);

}
