package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.coordinates.LineOfCoordinates;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.Optional;

public class CantMoveThroughPiecesRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, Move move) {
        Coordinates currentPieceCoordinates = boardState.pieceOnBoardForId(move.pieceId()).get().coordinates();
        Coordinates moveCoordinates = move.coordinates();
        Optional<LineOfCoordinates> line = currentPieceCoordinates.lineTo(moveCoordinates);

        if (line.isPresent()) {
            for (Coordinates c : line.get().inOrder()) {
                if (boardState
                    .coordinateStates()
                    .forCoordinates(c)
                    .map(BoardCoordinateState::isOccupied)
                    .orElse(false)) {
                    return RuleEvaluation.ILLEGAL;
                }
            }
        }

        return RuleEvaluation.LEGAL;
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType != PieceType.KNIGHT;
    }

    private static boolean destinationIsOccupied(BoardState boardState, Move move) {
        // TODO: Would a Null Object be better than an Optional for this state?
        return boardState
            .coordinateStates()
            .forCoordinates(move.coordinates())
            .map(BoardCoordinateState::isOccupied)
            .orElse(false);
    }
}
