package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;

public class PieceTakingRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        Piece takenPiece = boardState.pieceAtCoordinates(move.coordinates()).get();
        return RuleEvaluation.legalWithEffectsFromEvents(new PieceRemovedEvent(takenPiece.id()));
    }
}
