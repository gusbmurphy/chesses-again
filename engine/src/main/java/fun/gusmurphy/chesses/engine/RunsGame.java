package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public interface RunsGame {

    BoardState currentBoardState();

    boolean moveIsLegal(Move move);

    void makeMove(Move move);
}
