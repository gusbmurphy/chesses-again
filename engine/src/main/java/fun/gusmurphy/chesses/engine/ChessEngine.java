package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.MoveLegality;
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule;
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRuleSuite;

public class ChessEngine {

    private final MoveLegalityRule ruleSuite;
    private final BoardState boardState;

    public ChessEngine(MoveLegalityRuleSuite ruleSuite, BoardState boardState) {
        this.ruleSuite = ruleSuite;
        this.boardState = boardState;
    }

    public MoveLegality checkLegalityOf(Move move) {
        return ruleSuite.evaluate(boardState, move);
    }

}
