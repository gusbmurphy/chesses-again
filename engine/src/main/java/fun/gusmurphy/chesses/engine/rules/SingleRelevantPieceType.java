package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.Collections;
import java.util.List;

public class SingleRelevantPieceType implements RelevantPieceTypes {

    private final PieceType pieceType;

    public SingleRelevantPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    @Override
    public List<PieceType> asList() {
        return Collections.singletonList(pieceType);
    }
}
