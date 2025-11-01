package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateReducer;
import fun.gusmurphy.chesses.engine.boardstate.ReducesBoardState;
import fun.gusmurphy.chesses.engine.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.rules.*;

import java.util.Optional;

public class ChessEngine implements RunsGame {

    private final ReducesBoardState boardStateReducer;
    private final MoveRule moveRule;
    private BoardState boardState;

    public ChessEngine(ReducesBoardState boardStateReducer, MoveRule moveRule, BoardState boardState) {
        this.boardStateReducer = boardStateReducer;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    public static ChessEngine defaultEngine() {
        return defaultEngine(BoardStateBuilder.justCastling());
    }

    public static ChessEngine defaultEngine(BoardState initialBoardState) {
        return new ChessEngine(
            new BoardStateReducer(),
            MoveRuleSuite.BASIC,
            initialBoardState
        );
    }

    @Override
    public BoardState currentBoardState() {
        return boardState;
    }

    @Override
    public RuleEvaluation.Legality checkLegalityOf(Move move) {
        return moveRule.evaluate(boardState, move).legality();
    }

    @Override
    public void makeMove(Move move) {
        RuleEvaluation ruleEvaluation = moveRule.evaluate(boardState, move);

        if (ruleEvaluation.legality() != RuleEvaluation.Legality.LEGAL) {
            return;
        }

        for (Optional<BoardStateEvent> event = ruleEvaluation.effects().next();
             event.isPresent();
             event = ruleEvaluation.effects().next()) {
            boardState = boardStateReducer.reduce(boardState, event.get());
        }
    }

}
