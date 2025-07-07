package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;

import java.util.HashMap;

public class BoardStateBuilder {

    private PlayerColor currentTurnColor;
    private final HashMap<Coordinates, Piece> piecesByCoordinates = new HashMap<>();

    public BoardStateBuilder() {
    }

    public BoardState build() {
        return new BoardState(
            currentTurnColor,
            piecesByCoordinates
        );
    }

    public BoardStateBuilder currentTurnColor(PlayerColor color) {
        currentTurnColor = color;
        return this;
    }

    public BoardStateBuilder addPieceAt(Piece piece, Coordinates coordinates) {
        piecesByCoordinates.put(coordinates, piece);
        return this;
    }

}
