package fun.gusmurphy.chesses.engine;

import java.util.Set;

public class BoardState {

    private PlayerColor currentTurnColor;
    private Set<Piece> pieces;

    protected BoardState(PlayerColor currentTurnColor, Set<Piece> pieces) {
        this.currentTurnColor = currentTurnColor;
        this.pieces = pieces;
    }

}
