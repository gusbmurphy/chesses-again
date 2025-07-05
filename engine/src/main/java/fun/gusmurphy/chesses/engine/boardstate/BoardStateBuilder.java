package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;

import java.util.HashSet;
import java.util.Set;

public class BoardStateBuilder {

    private PlayerColor currentTurnColor;
    private Set<Piece> pieces = new HashSet<>();

    public BoardStateBuilder() {
    }

    public BoardState build() {
        return new BoardState(
            currentTurnColor,
            pieces
        );
    }

    public BoardStateBuilder currentTurnColor(PlayerColor color) {
        currentTurnColor = color;
        return this;
    }

    public BoardStateBuilder  addPiece(Piece piece) {
        pieces.add(piece);
        return this;
    }

}
