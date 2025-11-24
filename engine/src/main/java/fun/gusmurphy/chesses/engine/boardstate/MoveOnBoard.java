package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;

public class MoveOnBoard extends Move {

    private final PieceOnBoard piece;
    private final BoardCoordinateState coordinateState;

    public MoveOnBoard(PieceOnBoard piece, BoardCoordinateState coordinateState) {
        super(piece.id(), coordinateState.coordinates());
        this.piece = piece;
        this.coordinateState = coordinateState;
    }

    public PieceOnBoard pieceOnBoard() {
        return piece;
    }

    public BoardCoordinateState coordinateState() {
        return coordinateState;
    }
}
