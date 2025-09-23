package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnMovementRule implements MoveLegalityRule {
    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        if (pieceOnBoard.type() != PieceType.PAWN) {
            return MoveLegality.UNCONCERNED;
        }

        Coordinates currentCoordinates = pieceOnBoard.coordinates();

        if (moveIsGreaterThanOneSpotVertically(move, currentCoordinates)) {
            return MoveLegality.ILLEGAL;
        }

        if (moveIsBackwards(move, currentCoordinates, pieceOnBoard.color())) {
            return MoveLegality.ILLEGAL;
        }

        if (moveIsToDifferentFile(move, currentCoordinates)) {
            return MoveLegality.ILLEGAL;
        }

        return MoveLegality.LEGAL;
    }

    private static boolean moveIsGreaterThanOneSpotVertically(Move move, Coordinates currentCoordinates) {
        return Math.abs(currentCoordinates.rankDifferenceTo(move.coordinates())) > 1;
    }

    private static boolean moveIsToDifferentFile(Move move, Coordinates currentCoordinates) {
        return !currentCoordinates.sameFileAs(move.coordinates());
    }

    private static boolean moveIsBackwards(Move move, Coordinates currentCoordinates, PlayerColor color) {
        return currentCoordinates.rankDifferenceTo(move.coordinates()) == (color == PlayerColor.WHITE ? 1 : -1);
    }
}
