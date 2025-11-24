package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.events.TurnChangeEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;

public class PlayerTurnRule implements MoveRule {

    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        Piece piece = move.pieceOnBoard();

        if (piece.color() == boardState.currentTurnColor()) {
            PlayerColor currentTurnColor = boardState.currentTurnColor();
            PlayerColor newTurnColor = currentTurnColor.opposite();
            TurnChangeEvent turnChangeEvent = new TurnChangeEvent(newTurnColor);
            return RuleEvaluation.legalWithEffectsFromEvents(turnChangeEvent);
        }

        return RuleEvaluation.illegal();
    }
}
