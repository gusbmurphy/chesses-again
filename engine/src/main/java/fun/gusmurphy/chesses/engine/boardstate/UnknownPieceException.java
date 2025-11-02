package fun.gusmurphy.chesses.engine.boardstate;

public class UnknownPieceException extends RuntimeException {

    public UnknownPieceException() {
        super("Piece does not exist in board state");
    }
}
