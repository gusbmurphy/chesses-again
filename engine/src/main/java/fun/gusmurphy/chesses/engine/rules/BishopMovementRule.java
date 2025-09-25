package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import static fun.gusmurphy.chesses.engine.rules.MoveLegality.*;

public class BishopMovementRule implements MoveLegalityRule {

    private static final RelevantPieceTypes RELEVANT_PIECE_TYPE = new SingleRelevantPieceType(PieceType.BISHOP);

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());
        Coordinates moveCoordinates = move.coordinates();

        if (pieceOnBoard.type() != PieceType.BISHOP) {
            return UNCONCERNED;
        }

        if (moveCoordinates.sameRankAs(pieceOnBoard.coordinates())) {
            return ILLEGAL;
        }

        if (moveCoordinates.sameFileAs(pieceOnBoard.coordinates())) {
            return ILLEGAL;
        }

        if (!moveCoordinates.isDiagonalFrom(pieceOnBoard.coordinates())) {
            return ILLEGAL;
        }

        return LEGAL;
    }

    @Override
    public RelevantPieceTypes relevantPieceTypes() {
        return RELEVANT_PIECE_TYPE;
    }
}
