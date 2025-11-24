package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;

public class PieceTakingRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        PieceOnBoard movingPiece = boardState.pieceOnBoardForId(move.pieceId()).get();

        return boardState
                .pieceAtCoordinates(move.coordinates())
                .filter(pieceOnBoard -> pieceOnBoard.color() != movingPiece.color())
                .map(PieceTakingRule::createLegalTakingEvaluation)
                .orElseGet(RuleEvaluation::unconcerned);
    }

    private static RuleEvaluation createLegalTakingEvaluation(PieceOnBoard pieceOnBoard) {
        return RuleEvaluation.legalWithEffectsFromEvents(new PieceRemovedEvent(pieceOnBoard.id()));
    }
}
