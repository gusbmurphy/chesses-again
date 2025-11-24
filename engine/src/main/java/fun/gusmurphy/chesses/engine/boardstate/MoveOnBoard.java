package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;

public class MoveOnBoard extends Move {

    private final PieceOnBoard piece;

    public MoveOnBoard(PieceOnBoard piece, Coordinates coordinates) {
        super(piece.id(), coordinates);
        this.piece = piece;
    }

    public PieceOnBoard pieceOnBoard() {
        return piece;
    }
}
