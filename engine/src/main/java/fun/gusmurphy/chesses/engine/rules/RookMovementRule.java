package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import static fun.gusmurphy.chesses.engine.rules.Legality.*;

public class RookMovementRule extends SinglePieceMovementRule {

    public RookMovementRule() {
        super(PieceType.ROOK);
    }

    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId());
        Coordinates pieceCoordinates = pieceOnBoard.coordinates();
        Coordinates moveCoordinates = move.coordinates();

        if (pieceOnBoard.type() != PieceType.ROOK) {
            return UNCONCERNED;
        }

        if (moveCoordinates.sameRankAs(pieceCoordinates)) {
            return LEGAL;
        }

        if (moveCoordinates.sameFileAs(pieceCoordinates)) {
            return LEGAL;
        }

        return ILLEGAL;
    }
}
