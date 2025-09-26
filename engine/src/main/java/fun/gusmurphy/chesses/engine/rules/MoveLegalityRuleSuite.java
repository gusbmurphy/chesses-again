package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class MoveLegalityRuleSuite implements MoveLegalityRule {

    private final MoveLegalityRule[] rules;

    public MoveLegalityRuleSuite(MoveLegalityRule... rules) {
        this.rules = rules;
    }

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        if (rules.length < 1) {
            return MoveLegality.LEGAL;
        }

        PieceType pieceType = getTypeOfMovingPiece(boardState, move);

        for (MoveLegalityRule rule : rules) {
            if (ruleIsRelevantForPieceAndIllegal(boardState, move, rule, pieceType)) {
                return MoveLegality.ILLEGAL;
            }
        }

        return MoveLegality.LEGAL;
    }

    private static boolean ruleIsRelevantForPieceAndIllegal(
        BoardState boardState,
        Move move,
        MoveLegalityRule rule,
        PieceType pieceType
    ) {
        return rule.isRelevantForPieceType(pieceType) && rule.evaluate(boardState, move) == MoveLegality.ILLEGAL;
    }

    private static PieceType getTypeOfMovingPiece(BoardState boardState, Move move) {
        PieceOnBoard piece = boardState.pieceOnBoardForId(move.pieceId());
        return piece.type();
    }

}
