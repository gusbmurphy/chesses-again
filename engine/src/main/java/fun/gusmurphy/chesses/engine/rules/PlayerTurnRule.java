package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PlayerTurnRule implements MoveRule {

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        Piece piece = boardState.pieceOnBoardForId(move.pieceId()).get();

        if (piece.color() == boardState.currentTurnColor()) {
            return RuleEvaluation.legal();
        }

        return RuleEvaluation.illegal();
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return true;
    }
}
