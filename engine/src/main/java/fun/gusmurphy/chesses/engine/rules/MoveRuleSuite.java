package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import static fun.gusmurphy.chesses.engine.rules.Legality.*;

public class MoveRuleSuite implements MoveRule {

    private final MoveRule[] rules;

    public MoveRuleSuite(MoveRule... rules) {
        this.rules = rules;
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        if (rules.length < 1) {
            return LEGAL;
        }

        if (anyRelevantRuleSaysMoveIsIllegal(boardState, move)) {
            return ILLEGAL;
        }

        return LEGAL;
    }

    private boolean anyRelevantRuleSaysMoveIsIllegal(BoardState boardState, Move move) {
        PieceType pieceType = getTypeOfMovingPiece(boardState, move);

        for (MoveRule rule : rules) {
            if (ruleIsRelevantForPieceAndIllegal(boardState, move, rule, pieceType)) {
                return true;
            }
        }

        return false;
    }

    private static PieceType getTypeOfMovingPiece(BoardState boardState, Move move) {
        PieceOnBoard piece = boardState.pieceOnBoardForId(move.pieceId());
        return piece.type();
    }

    private static boolean ruleIsRelevantForPieceAndIllegal(
        BoardState boardState,
        Move move,
        MoveRule rule,
        PieceType pieceType
    ) {
        return rule.isRelevantForPieceType(pieceType) && rule.evaluate(boardState, move) == ILLEGAL;
    }

    public static final MoveRuleSuite BASIC = new MoveRuleSuite(
        new BishopMovementRule(),
        new RookMovementRule(),
        new PawnMovementRule(),
        new KingMovementRule(),
        new KnightMovementRule(),
        new QueenMovementRule(),
        new PlayerTurnRule(),
        new CantStayStillRule(),
        new CantMoveThroughPiecesRule()
    );

}
