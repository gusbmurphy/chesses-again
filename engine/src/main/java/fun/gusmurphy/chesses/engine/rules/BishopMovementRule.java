package fun.gusmurphy.chesses.engine.rules;

import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class BishopMovementRule extends SinglePieceMovementRule {

    public BishopMovementRule() {
        super(PieceType.BISHOP, BishopMovementRule::legality);
    }

    static RuleEvaluation.Legality legality(BoardState boardState, Move move) {
        PieceOnBoard pieceOnBoard = boardState.pieceOnBoardForId(move.pieceId()).get();
        Coordinates moveCoordinates = move.coordinates();

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
}
