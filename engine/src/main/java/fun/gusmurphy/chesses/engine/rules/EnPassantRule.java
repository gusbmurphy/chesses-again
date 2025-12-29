package fun.gusmurphy.chesses.engine.rules;

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
        Coordinates currentPieceCoordinates =
                boardState
                        .pieceOnBoardForId(move.pieceId())
                        .orElseThrow(IllegalStateException::new)
                        .coordinates();

        CoordinateDifference difference =
                new CoordinateDifference(currentPieceCoordinates, coordinatesOfMove);
        if (difference.isNotDiagonal()) {
            return RuleEvaluation.unconcerned();
        }

        Coordinates possiblePositionOfEnemyPawn = coordinatesOfMove.coordinatesTo(1, 0);
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
