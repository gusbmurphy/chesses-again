package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.piece.PieceId;

public class Move {

    private final PieceId pieceId;

    public Move(PieceId pieceId) {
        this.pieceId = pieceId;
    }

    public PieceId pieceId() {
        return pieceId;
    }

}
