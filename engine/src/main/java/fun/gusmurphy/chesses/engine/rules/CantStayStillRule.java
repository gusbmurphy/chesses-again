package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

class CantStayStillRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        Coordinates currentPiecePosition = boardState.pieceOnBoardForId(move.pieceId()).get().coordinates();
        Coordinates movePosition = move.coordinates();

        if (currentPiecePosition == movePosition) {
            return RuleEvaluation.ILLEGAL;
        }

        return RuleEvaluation.LEGAL;
    }
}
