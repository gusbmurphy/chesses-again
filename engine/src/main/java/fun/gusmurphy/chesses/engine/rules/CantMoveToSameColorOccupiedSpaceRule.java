package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.Piece;

public class CantMoveToSameColorOccupiedSpaceRule implements MoveRule {
    @Override
    public Legality evaluate(BoardState boardState, Move move) {
        PlayerColor occupyingColor = boardState
            .coordinateStates().forCoordinates(move.coordinates()).get().piece().get().color();

        Piece movingPiece = boardState.pieceOnBoardForId(move.pieceId());

        if (occupyingColor == movingPiece.color()) {
            return Legality.ILLEGAL;
        }

        return Legality.UNCONCERNED;
    }
}
