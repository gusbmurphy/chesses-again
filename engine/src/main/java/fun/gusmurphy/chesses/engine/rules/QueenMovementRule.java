package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

class QueenMovementRule extends SinglePieceMovementRule {

    public QueenMovementRule() {
        super(PieceType.QUEEN);
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        return null;
    }
}
