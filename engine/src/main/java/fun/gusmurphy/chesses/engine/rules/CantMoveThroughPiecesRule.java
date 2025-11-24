package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.MoveOnBoard;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.coordinates.LineOfCoordinates;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import java.util.Optional;

public class CantMoveThroughPiecesRule implements MoveRule {
    @Override
    public RuleEvaluation evaluate(BoardState boardState, MoveOnBoard move) {
        Coordinates currentPieceCoordinates = move.pieceOnBoard().coordinates();
        Coordinates moveCoordinates = move.coordinates();
        Optional<LineOfCoordinates> line = currentPieceCoordinates.lineTo(moveCoordinates);

        if (line.isPresent()) {
            for (Coordinates c : line.get().inOrder()) {
                if (boardState
                        .coordinateStates()
                        .forCoordinates(c)
                        .map(BoardCoordinateState::isOccupied)
                        .orElse(false)) {
                    return RuleEvaluation.illegal();
                }
            }
        }

        return RuleEvaluation.legalWithNoEffects();
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return pieceType != PieceType.KNIGHT;
    }
}
