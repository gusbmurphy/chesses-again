package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.File;
import fun.gusmurphy.chesses.engine.Rank;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fun.gusmurphy.chesses.engine.piece.PieceType.*;
import static fun.gusmurphy.chesses.engine.PlayerColor.*;

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

    public static BoardState defaultBoard() {
        BoardStateBuilder builder = new BoardStateBuilder()
            .currentTurnColor(WHITE);

        Map<File, PieceType> backRankPieceTypesByFile = Stream.of(
            new AbstractMap.SimpleEntry<>(File.A, ROOK),
            new AbstractMap.SimpleEntry<>(File.B, KNIGHT),
            new AbstractMap.SimpleEntry<>(File.C, BISHOP),
            new AbstractMap.SimpleEntry<>(File.D, KING),
            new AbstractMap.SimpleEntry<>(File.E, QUEEN),
            new AbstractMap.SimpleEntry<>(File.F, BISHOP),
            new AbstractMap.SimpleEntry<>(File.G, KNIGHT),
            new AbstractMap.SimpleEntry<>(File.H, ROOK)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Rank whitePawnRank = Rank.TWO;
        Rank whiteBackRank = Rank.ONE;
        Rank blackPawnRank = Rank.SEVEN;
        Rank blackBackRank = Rank.EIGHT;

        for (File file : File.values()) {
            PieceType backRankPieceType = backRankPieceTypesByFile.get(file);

            Piece whiteBackRankPiece = new Piece(WHITE, backRankPieceType);
            Piece blackBackRankPiece = new Piece(BLACK, backRankPieceType);

            builder.addPieceAt(whiteBackRankPiece, Coordinates.with(file, whiteBackRank));
            builder.addPieceAt(blackBackRankPiece, Coordinates.with(file, blackBackRank));

            Piece whitePawn = new Piece(WHITE, PAWN);
            Piece blackPawn = new Piece(BLACK, PAWN);

            builder.addPieceAt(whitePawn, Coordinates.with(file, whitePawnRank));
            builder.addPieceAt(blackPawn, Coordinates.with(file, blackPawnRank));
        }

        return builder.build();
    }

}
