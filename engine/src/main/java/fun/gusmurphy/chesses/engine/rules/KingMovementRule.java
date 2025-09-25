package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class KingMovementRule implements MoveLegalityRule {

    private static final RelevantPieceTypes RELEVANT_PIECE_TYPE = new SingleRelevantPieceType(PieceType.KING);

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());

        if (pieceOnBoard.type() != PieceType.KING) {
            return MoveLegality.UNCONCERNED;
        }

        Coordinates currentPieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (Math.abs(currentPieceCoordinates.fileDifferenceTo(moveCoordinates)) > 1) {
            return MoveLegality.ILLEGAL;
        }

        if (Math.abs(currentPieceCoordinates.rankDifferenceTo(moveCoordinates)) > 1) {
            return MoveLegality.ILLEGAL;
        }

        return MoveLegality.LEGAL;
    }

    @Override
    public RelevantPieceTypes relevantPieceTypes() {
        return RELEVANT_PIECE_TYPE;
    }
}
