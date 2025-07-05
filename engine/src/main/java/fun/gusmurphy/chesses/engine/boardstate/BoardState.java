package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;

import java.util.Set;

public class BoardState {

    private PlayerColor currentTurnColor;
    private Set<Piece> pieces;

    protected BoardState(PlayerColor currentTurnColor, Set<Piece> pieces) {
        this.currentTurnColor = currentTurnColor;
        this.pieces = pieces;
    }

}
