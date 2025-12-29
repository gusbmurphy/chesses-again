package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.CoordinateDifference;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class EnPassantRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        Coordinates coordinatesOfMove = move.coordinates();
        PieceOnBoard movingPiece =
                boardState
                        .pieceOnBoardForId(move.pieceId())
                        .orElseThrow(IllegalStateException::new);

        CoordinateDifference difference = boardState.coordinateDifferenceForMove(move);
        if (difference.isNotDiagonal()) {
            return RuleEvaluation.unconcerned();
        }

        int rankDifferenceToEnemyPawnPosition = movingPiece.color() == PlayerColor.WHITE ? 1 : -1;
        Coordinates possiblePositionOfEnemyPawn =
                coordinatesOfMove.coordinatesTo(rankDifferenceToEnemyPawnPosition, 0);
        PieceOnBoard enemyPawn =
                boardState
                        .pieceAtCoordinates(possiblePositionOfEnemyPawn)
                        .orElseThrow(IllegalStateException::new);
        return RuleEvaluation.legalWithEffectsFromEvents(
                new PieceRemovedEvent(enemyPawn.id()),
                new PieceMovedEvent(move.pieceId(), move.coordinates()));
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType == PieceType.PAWN;
    }
}
