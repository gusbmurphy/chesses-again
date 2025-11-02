package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation;

public interface RunsGame {

    BoardState currentBoardState();

    RuleEvaluation.Legality checkLegalityOf(Move move);

    void makeMove(Move move);
}
