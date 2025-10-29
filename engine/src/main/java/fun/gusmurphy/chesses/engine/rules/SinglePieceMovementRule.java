package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceType;

abstract class SinglePieceMovementRule implements MoveRule {

    private final PieceType relevantType;
    private final LegalityEvaluation evaluation;

    SinglePieceMovementRule(PieceType pieceType, LegalityEvaluation evaluation) {
        relevantType = pieceType;
        this.evaluation = evaluation;
    }

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        RuleEvaluation.Legality legality = evaluation.evaluateLegality(boardState, move);

        switch (legality) {
            case ILLEGAL:
                return RuleEvaluation.illegal();
            case UNCONCERNED:
                return RuleEvaluation.unconcerned();
            default:
                return RuleEvaluation.legalWithMove(move);
        }
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return relevantType == pieceType;
    }

    @FunctionalInterface
    interface LegalityEvaluation {
        RuleEvaluation.Legality evaluateLegality(BoardState boardState, Move move);
    }
}
