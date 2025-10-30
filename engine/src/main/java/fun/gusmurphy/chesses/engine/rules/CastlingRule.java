package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.Arrays;

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*;

public class CastlingRule implements MoveRule {

    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        if (moveIsNotToAValidCastlingPosition(move)) {
            return RuleEvaluation.unconcerned();
        }

        PieceOnBoard movingKing = boardState.pieceOnBoardForId(move.pieceId()).get();
        if (movingKing.hasMoved()) {
            return RuleEvaluation.illegal();
        }

        Coordinates relevantRookPosition = findRelevantRookPosition(move);
        Piece rook = boardState.coordinateStates().forCoordinates(relevantRookPosition).get().piece().get(); // TODO: Yikes!
        if (rook.hasMoved()) {
            return RuleEvaluation.illegal();
        }

        Coordinates newKingPosition = move.coordinates();
        PieceMovedEvent kingMove = new PieceMovedEvent(move.pieceId(), newKingPosition);

        Coordinates newRookPosition = newRookPositionBasedOnKingMove(move);
        PieceId rookId = boardState.coordinateStates().forCoordinates(relevantRookPosition).get().piece().get().id();
        PieceMovedEvent rookMove = new PieceMovedEvent(rookId, newRookPosition);

        return RuleEvaluation.legalWithEffectsFromEvents(kingMove, rookMove);
    }

    private static boolean moveIsNotToAValidCastlingPosition(Move move) {
        return !Arrays.asList(C1, G1, C8, G8).contains(move.coordinates());
    }

    private static Coordinates findRelevantRookPosition(Move move) {
        switch (move.coordinates()) {
            case C1:
                return A1;
            case G1:
                return H1;
            case C8:
                return A8;
            case G8:
                return H8;
            default:
                return null;
        }
    }

    private static Coordinates newRookPositionBasedOnKingMove(Move move) {
        switch (move.coordinates()) {
            case C1:
                return D1;
            case G1:
                return F1;
            case C8:
                return D8;
            case G8:
                return F8;
            default:
                return null;
        }
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
