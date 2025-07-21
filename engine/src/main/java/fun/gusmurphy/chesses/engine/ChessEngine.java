package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.MoveLegality;
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule;

public class ChessEngine {

    private final AppliesMoves moveApplicator;
    private final MoveLegalityRule moveRule;
    private final BoardState boardState;

    public ChessEngine(AppliesMoves moveApplicator, MoveLegalityRule moveRule, BoardState boardState) {
        this.moveApplicator = moveApplicator;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    public MoveLegality checkLegalityOf(Move move) {
        return moveRule.evaluate(boardState, move);
    }

    public void makeMove(Move move) {
        moveRule.evaluate(boardState, move);
        moveApplicator.applyMoveToBoard(move, boardState);
    }

}
