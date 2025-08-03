package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.MoveLegality;

public interface RunsGame {

    BoardState currentBoardState();
    MoveLegality checkLegalityOf(Move move);
    void makeMove(Move move);

}
