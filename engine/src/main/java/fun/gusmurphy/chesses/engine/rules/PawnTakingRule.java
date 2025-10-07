package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnTakingRule extends SinglePieceMovementRule {

    public PawnTakingRule() {
        super(PieceType.PAWN);
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        return null;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }
}
