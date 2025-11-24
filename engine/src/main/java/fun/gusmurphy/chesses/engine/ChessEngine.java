package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.*;
import fun.gusmurphy.chesses.engine.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.rules.*;
import java.util.Optional;

public class ChessEngine implements RunsGame {

    private final ReducesBoardState boardStateReducer;
    private final MoveRule moveRule;
    private BoardState boardState;

    public ChessEngine(
            ReducesBoardState boardStateReducer, MoveRule moveRule, BoardState boardState) {
        this.boardStateReducer = boardStateReducer;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    public static ChessEngine defaultEngine() {
        return defaultEngine(BoardStateBuilder.defaultBoard());
    }

    public static ChessEngine defaultEngine(BoardState initialBoardState) {
        return new ChessEngine(new BoardStateReducer(), MoveRuleSuite.BASIC, initialBoardState);
    }

    @Override
    public BoardState currentBoardState() {
        return boardState;
    }

    @Override
    public boolean moveIsLegal(Move move) {
        MoveOnBoard moveOnBoard = boardState.enhanceMove(move);
        return moveRule.evaluate(boardState, moveOnBoard).isLegal();
    }

    @Override
    public void makeMove(Move move) {
        MoveOnBoard moveOnBoard = boardState.enhanceMove(move);
        RuleEvaluation ruleEvaluation = moveRule.evaluate(boardState, moveOnBoard);

        if (ruleEvaluation.isNotLegal()) {
            return;
        }

        for (Optional<BoardStateEvent> event = ruleEvaluation.effects().next();
                event.isPresent();
                event = ruleEvaluation.effects().next()) {
            boardState = boardStateReducer.reduce(boardState, event.get());
        }
    }
}
