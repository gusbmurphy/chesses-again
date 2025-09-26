package fun.gusmurphy.chesses.engine.rules;

import fun.gusmurphy.chesses.engine.piece.PieceType;

abstract class SinglePieceMovementRule implements MoveLegalityRule {

    private final PieceType relevantType;

    SinglePieceMovementRule(PieceType pieceType) {
        relevantType = pieceType;
    }

    @Override
    public boolean isRelevantForPieceType(PieceType pieceType) {
        return relevantType == pieceType;
    }
}
