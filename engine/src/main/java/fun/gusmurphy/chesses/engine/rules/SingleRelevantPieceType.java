package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

public class SingleRelevantPieceType implements RelevantPieceTypes {

    private final PieceType relevantType;

    public SingleRelevantPieceType(PieceType pieceType) {
        this.relevantType = pieceType;
    }

    @Override
    public boolean includes(PieceType pieceType) {
        return pieceType == relevantType;
    }
}
