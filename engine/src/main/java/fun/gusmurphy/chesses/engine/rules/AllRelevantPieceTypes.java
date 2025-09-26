package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

public class AllRelevantPieceTypes implements RelevantPieceTypes {
    @Override
    public boolean includes(PieceType pieceType) {
        return true;
    }
}
