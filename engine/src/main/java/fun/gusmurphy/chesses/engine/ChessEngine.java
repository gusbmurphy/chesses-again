package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateReducer;
import fun.gusmurphy.chesses.engine.events.MoveEventDeriver;
import fun.gusmurphy.chesses.engine.events.TurnTracker;
import fun.gusmurphy.chesses.engine.rules.*;

public class ChessEngine implements RunsGame {

    private final AppliesMoves moveApplicator;
    private final MoveLegalityRule moveRule;
    private BoardState boardState;

    public ChessEngine(AppliesMoves moveApplicator, MoveLegalityRule moveRule, BoardState boardState) {
        this.moveApplicator = moveApplicator;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    public static ChessEngine defaultEngine() {
        PlayerColor startingPlayerColor = PlayerColor.WHITE;

        MoveLegalityRule pieceMovementRule = new MoveLegalityRuleSuite(
            new BishopMovementRule(),
            new RookMovementRule()
        );

        return new ChessEngine(
            new MoveApplicator(new MoveEventDeriver(new TurnTracker(startingPlayerColor)), new BoardStateReducer()),
            new MoveLegalityRuleSuite(pieceMovementRule, new PlayerTurnRule()),
            BoardStateBuilder.defaultBoard()
        );
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
