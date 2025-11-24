package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.events.TurnChangeEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PlayerTurnRule implements MoveRule {

    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        Piece piece = boardState.pieceOnBoardForId(move.pieceId()).get();

        if (piece.color() == boardState.currentTurnColor()) {
            PlayerColor currentTurnColor = boardState.currentTurnColor();
            PlayerColor newTurnColor = currentTurnColor.opposite();
            TurnChangeEvent turnChangeEvent = new TurnChangeEvent(newTurnColor);
            return RuleEvaluation.legalWithEffectsFromEvents(turnChangeEvent);
        }

        return RuleEvaluation.illegal();
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return true;
    }
}
