package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.File;
import fun.gusmurphy.chesses.engine.Rank;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BoardStateBuilder {

    private PlayerColor currentTurnColor;
    private final HashMap<Coordinates, Piece> piecesByCoordinates = new HashMap<>();
    private int width = 8;
    private int height = 8;

    public BoardStateBuilder() {
    }

    public BoardStateBuilder currentTurnColor(PlayerColor color) {
        currentTurnColor = color;
        return this;
    }

    public BoardStateBuilder addPieceAt(Piece piece, Coordinates coordinates) {
        piecesByCoordinates.put(coordinates, piece);
        return this;
    }

    public BoardStateBuilder width(int width) {
        this.width = width;
        return this;
    }

    public BoardStateBuilder height(int height) {
        this.height = height;
        return this;
    }

    public BoardState build() {
        validateBoardSize();

        Set<Rank> ranks = new HashSet<>(Arrays.asList(Rank.values()).subList(0, height));
        Set<File> files = new HashSet<>(Arrays.asList(File.values()).subList(0, width));

        return new BoardState(
            currentTurnColor,
            piecesByCoordinates,
            ranks,
            files
        );
    }

    private void validateBoardSize() {
        if (width * height < 2) {
            throw new IllegalArgumentException("Board must have at least two coordinates");
        }
    }

}
