package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public interface AppliesMoves {

    /**
     * @return The resulting board state.
     */
    BoardState applyMoveToBoard(Move move, BoardState boardState);

}
