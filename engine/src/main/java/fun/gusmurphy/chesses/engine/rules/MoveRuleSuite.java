package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import java.util.Arrays;
import java.util.Optional;

public class MoveRuleSuite implements MoveRule {

    private final MoveRule[] rules;

    public MoveRuleSuite(MoveRule... rules) {
        this.rules = rules;
    }

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        if (rules.length < 1) {
            return RuleEvaluation.legalWithNoEffects();
        }

        PieceType relevantPieceType = getTypeOfMovingPiece(boardState, move);
        RuleEvaluation combinedEvaluation = RuleEvaluation.legalWithNoEffects();

        for (MoveRule rule : rules) {
            if (!rule.isRelevantForPieceType(relevantPieceType)) {
                continue;
            }

            RuleEvaluation thisEvaluation = rule.evaluate(boardState, move);

            if (thisEvaluation.isIllegal()) {
                Optional<MoveRule> legalOverride =
                        findOverrideWithLegalRuling(boardState, move, rule);
                if (!legalOverride.isPresent()) {
                    combinedEvaluation = combinedEvaluation.combineWith(thisEvaluation);
                }
            } else {
                combinedEvaluation = combinedEvaluation.combineWith(thisEvaluation);
            }
        }

        return combinedEvaluation;
    }

    private Optional<MoveRule> findOverrideWithLegalRuling(
            BoardState boardState, Move move, MoveRule rule) {
        return Arrays.stream(rules)
                .filter(r -> r.overrides(rule))
                .filter(r -> r.evaluate(boardState, move).isLegal())
                .findAny();
    }

    private static PieceType getTypeOfMovingPiece(BoardState boardState, Move move) {
        PieceOnBoard piece = boardState.pieceOnBoardForId(move.pieceId()).get();
        return piece.type();
    }

    public static final MoveRuleSuite BASIC =
            new MoveRuleSuite(
                    new BishopMovementRule(),
                    new RookMovementRule(),
                    new PawnMovementRule(),
                    new PawnTakingRule(),
                    new KingMovementRule(),
                    new KnightMovementRule(),
                    new CastlingRule(),
                    new QueenMovementRule(),
                    new PlayerTurnRule(),
                    new CantStayStillRule(),
                    new CantMoveThroughPiecesRule(),
                    new CantMoveToSameColorOccupiedSpaceRule());
}
