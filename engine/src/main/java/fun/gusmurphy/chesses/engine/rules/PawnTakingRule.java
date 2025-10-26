package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnTakingRule extends SinglePieceMovementRule {

    public PawnTakingRule() {
        super(PieceType.PAWN);
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        BoardCoordinateState coordinateState = boardState
            .coordinateStates().forCoordinates(move.coordinates()).get();

        if (coordinateState.isOccupied()) {
            Coordinates currentPieceCoordinates = boardState
                .pieceOnBoardForId(move.pieceId())
                .get()
                .coordinates();

            Coordinates moveCoordinates = move.coordinates();

            if (moveCoordinates.isDiagonalFrom(currentPieceCoordinates)
                && Math.abs(moveCoordinates.rankDifferenceTo(currentPieceCoordinates)) < 2) {
                PlayerColor movingPieceColor = boardState.pieceOnBoardForId(move.pieceId()).get().color();

                int verticalMovement = moveCoordinates.rankDifferenceTo(currentPieceCoordinates);
                if ((verticalMovement > 0 && movingPieceColor == PlayerColor.WHITE)
                    || (verticalMovement < 0 && movingPieceColor == PlayerColor.BLACK)) {
                    return Legality.LEGAL;
                }
            }
        }

        return Legality.ILLEGAL;
    }

    @Override
    public boolean overrides(MoveRule otherRule) {
        return otherRule instanceof PawnMovementRule;
    }
}
