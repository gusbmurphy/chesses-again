package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.rules.MoveLegality;
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule;

public class ChessEngine implements RunsGame {

    private final AppliesMoves moveApplicator;
    private final MoveLegalityRule moveRule;
    private BoardState boardState;

    public ChessEngine(AppliesMoves moveApplicator, MoveLegalityRule moveRule, BoardState boardState) {
        this.moveApplicator = moveApplicator;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    @Override
    public BoardState currentBoardState() {
        return boardState;
    }

    @Override
    public MoveLegality checkLegalityOf(Move move) {
        return moveRule.evaluate(boardState, move);
    }

    @Override
    public void makeMove(Move move) {
        MoveLegality moveLegality = moveRule.evaluate(boardState, move);

        if (moveLegality == MoveLegality.LEGAL) {
            boardState = moveApplicator.applyMoveToBoard(move, boardState);
        }
    }

}
