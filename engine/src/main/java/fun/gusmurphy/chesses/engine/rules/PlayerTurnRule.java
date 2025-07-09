package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.Piece;

public class PlayerTurnRule implements MoveLegalityRule {

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        Piece piece = boardState.pieceOnBoardForId(move.pieceId());

        if (piece.color() == boardState.currentTurnColor()) {
            return MoveLegality.LEGAL;
        }

        return MoveLegality.ILLEGAL;
    }
}
