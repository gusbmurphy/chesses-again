package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;

class CantStayStillRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        Coordinates currentPiecePosition = move.pieceOnBoard().coordinates();
        Coordinates movePosition = move.coordinates();

        if (currentPiecePosition == movePosition) {
            return RuleEvaluation.illegal();
        }

        return RuleEvaluation.legalWithNoEffects();
    }
}
