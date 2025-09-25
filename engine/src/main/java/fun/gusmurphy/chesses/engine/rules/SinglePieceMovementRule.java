package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

abstract class SinglePieceMovementRule implements MoveLegalityRule {

    private final SingleRelevantPieceType relevantPieceType;

    SinglePieceMovementRule(PieceType pieceType) {
        relevantPieceType = new SingleRelevantPieceType(pieceType);
    }

    @Override
    public RelevantPieceTypes relevantPieceTypes() {
        return relevantPieceType;
    }
}
