package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.CoordinateDifference;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import java.util.Optional;

public class EnPassantRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        PieceOnBoard movingPiece = move.pieceOnBoard();

        CoordinateDifference difference = boardState.coordinateDifferenceForMove(move);
        if (difference.isNotDiagonal()) {
            return RuleEvaluation.unconcerned();
        }

        Optional<PieceOnBoard> enemyPawn =
                findEnemyPawn(boardState, move.coordinates(), movingPiece);

        return enemyPawn
                .map(piece -> createEvaluationWithEffects(move, piece))
                .orElseGet(RuleEvaluation::unconcerned);
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType == PieceType.PAWN;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }

    private static RuleEvaluation createEvaluationWithEffects(
            MoveOnBoard move, PieceOnBoard piece) {
        return RuleEvaluation.legalWithEffectsFromEvents(
                new PieceRemovedEvent(piece.id()),
                new PieceMovedEvent(move.pieceId(), move.coordinates()));
    }

    private static Optional<PieceOnBoard> findEnemyPawn(
            BoardState boardState, Coordinates coordinatesOfMove, PieceOnBoard movingPiece) {
        Coordinates possiblePositionOfEnemyPawn =
                getPossiblePositionOfEnemyPawn(coordinatesOfMove, movingPiece);
        return boardState.pieceAtCoordinates(possiblePositionOfEnemyPawn);
    }

    private static Coordinates getPossiblePositionOfEnemyPawn(
            Coordinates coordinatesOfMove, Piece movingPiece) {
        int rankDifferenceToEnemyPawnPosition = movingPiece.color() == PlayerColor.WHITE ? -1 : 1;

        return coordinatesOfMove.coordinatesTo(rankDifferenceToEnemyPawnPosition, 0);
    }
}
