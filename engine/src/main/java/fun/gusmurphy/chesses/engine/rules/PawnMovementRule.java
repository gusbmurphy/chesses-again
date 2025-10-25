package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PawnMovementRule extends SinglePieceMovementRule {

    public PawnMovementRule() {
        super(PieceType.PAWN);
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId()).get();

        Coordinates currentCoordinates = pieceOnBoard.coordinates();

        if (moveCoordinatesAreOccupied(boardState, move)) {
            return Legality.ILLEGAL;
        }

        if (moveIsGreaterThanTwoSpotsVertically(move, currentCoordinates)) {
            return Legality.ILLEGAL;
        }

        if (moveIsBackwards(move, currentCoordinates, pieceOnBoard.color())) {
            return Legality.ILLEGAL;
        }

        if (moveIsToDifferentFile(move, currentCoordinates)) {
            return Legality.ILLEGAL;
        }

        return Legality.LEGAL;
    }

    private static boolean moveCoordinatesAreOccupied(BoardState boardState, Move move) {
        BoardCoordinateState moveCoordinatesState = boardState.coordinateStates()
            .forCoordinates(move.coordinates()).get();
        return moveCoordinatesState.isOccupied();
    }

    private static boolean moveIsGreaterThanTwoSpotsVertically(Move move, Coordinates currentCoordinates) {
        return Math.abs(currentCoordinates.rankDifferenceTo(move.coordinates())) > 2;
    }

    private static boolean moveIsToDifferentFile(Move move, Coordinates currentCoordinates) {
        return !currentCoordinates.sameFileAs(move.coordinates());
    }

    private static boolean moveIsBackwards(Move move, Coordinates currentCoordinates, PlayerColor color) {
        return currentCoordinates.rankDifferenceTo(move.coordinates()) == (color == PlayerColor.WHITE ? 1 : -1);
    }
}
