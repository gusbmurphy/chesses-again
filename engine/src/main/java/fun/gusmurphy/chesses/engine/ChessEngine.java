package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateReducer;
import fun.gusmurphy.chesses.engine.events.MoveEventDeriver;
import fun.gusmurphy.chesses.engine.events.TurnTracker;
import fun.gusmurphy.chesses.engine.rules.*;

public class ChessEngine implements RunsGame {

    private final AppliesMoves moveApplicator;
    private final MoveRule moveRule;
    private BoardState boardState;

    public ChessEngine(AppliesMoves moveApplicator, MoveRule moveRule, BoardState boardState) {
        this.moveApplicator = moveApplicator;
        this.moveRule = moveRule;
        this.boardState = boardState;
    }

    public static ChessEngine defaultEngine() {
        return defaultEngine(BoardStateBuilder.defaultBoard());
    }

    public static ChessEngine defaultEngine(BoardState initialBoardState) {
        PlayerColor startingPlayerColor = PlayerColor.WHITE;

        MoveRule pieceMovementRule = new MoveRuleSuite(
            new BishopMovementRule(),
            new RookMovementRule(),
            new PawnMovementRule(),
            new KingMovementRule(),
            new KnightMovementRule(),
            new QueenMovementRule()
        );

        return new ChessEngine(
            new MoveApplicator(new MoveEventDeriver(new TurnTracker(startingPlayerColor)), new BoardStateReducer()),
            new MoveRuleSuite(pieceMovementRule, new PlayerTurnRule()),
            initialBoardState
        );
    }

    @Override
    public BoardState currentBoardState() {
        return boardState;
    }

    @Override
    public Legality checkLegalityOf(Move move) {
        return moveRule.evaluate(boardState, move);
    }

    @Override
    public void makeMove(Move move) {
        Legality legality = moveRule.evaluate(boardState, move);

        if (legality == Legality.LEGAL) {
            boardState = moveApplicator.applyMoveToBoard(move, boardState);
        }
    }

}
