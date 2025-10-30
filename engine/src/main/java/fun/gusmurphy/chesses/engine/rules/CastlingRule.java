package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.Arrays;

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*;

public class CastlingRule implements MoveRule {

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        if (Arrays.asList(C1, G1, C8, G8).contains(move.coordinates())) {
            return RuleEvaluation.legalWithNoEffects();
        }

        return RuleEvaluation.unconcerned();
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof KingMovementRule;
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType == PieceType.KING;
    }
}
