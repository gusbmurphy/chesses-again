package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

public interface RelevantPieceTypes {
    boolean includes(PieceType pieceType);
}
