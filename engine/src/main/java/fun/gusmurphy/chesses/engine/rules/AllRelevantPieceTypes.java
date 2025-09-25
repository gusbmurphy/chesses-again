package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.Arrays;
import java.util.List;

public class AllRelevantPieceTypes implements RelevantPieceTypes {
    @Override
    public List<PieceType> asList() {
        return Arrays.asList(PieceType.values());
    }
}
