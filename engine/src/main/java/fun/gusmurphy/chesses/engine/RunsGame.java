package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.Legality;

public interface RunsGame {

    BoardState currentBoardState();
    Legality checkLegalityOf(Move move);
    void makeMove(Move move);

}
