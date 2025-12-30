package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.File;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.Rank;
import fun.gusmurphy.chesses.engine.coordinates.CoordinateDifference;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.coordinates.LineOfCoordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import java.util.*;

public class BoardState {

    // TODO: Feels a little bad to make this members `protected`...
    protected PlayerColor currentTurnColor;
    protected final Set<Piece> pieces = new HashSet<>();
    protected final Map<PieceId, Coordinates> coordinatesForPieces = new HashMap<>();
    private final Set<Coordinates> coordinatesOnBoard;

    // TODO: Throw an exception if two pieces are at the same coordinates.
    protected BoardState(
            PlayerColor currentTurnColor,
            HashMap<Coordinates, Piece> piecesAtCoordinates,
            Set<Rank> ranks,
            Set<File> files) {
        this.currentTurnColor = currentTurnColor;

        Set<Coordinates> coordinatesOnBoard = new HashSet<>();
        for (Rank rank : ranks) {
            for (File file : files) {
                coordinatesOnBoard.add(Coordinates.with(file, rank));
            }
        }
        this.coordinatesOnBoard = coordinatesOnBoard;

        for (Map.Entry<Coordinates, Piece> pieceAtCoordinates : piecesAtCoordinates.entrySet()) {
            Piece piece = pieceAtCoordinates.getValue();
            pieces.add(piece);
            coordinatesForPieces.put(piece.id(), pieceAtCoordinates.getKey());
        }
    }

    protected BoardState movePiece(PieceId pieceId, Coordinates newPosition) {
        Piece movingPiece = findPieceWithId(pieceId).orElseThrow(UnknownPieceException::new);
        BoardState newState = copy();
        newState.pieces.remove(movingPiece);
        newState.pieces.add(movingPiece.afterMove());
        newState.coordinatesForPieces.put(pieceId, newPosition);
        return newState;
    }

    protected BoardState removePiece(PieceId pieceId) {
        Piece removedPiece = findPieceWithId(pieceId).orElseThrow(UnknownPieceException::new);
        BoardState newState = copy();
        newState.pieces.remove(removedPiece);
        newState.coordinatesForPieces.remove(removedPiece.id());
        return newState;
    }

    private Optional<Piece> findPieceWithId(PieceId id) {
        return pieces.stream().filter(piece -> piece.id() == id).findFirst();
    }

    private BoardState() {
        coordinatesOnBoard = new HashSet<>();
    }

    protected BoardState copy() {
        BoardState copy = new BoardState();
        copy.currentTurnColor = currentTurnColor;
        copy.pieces.addAll(pieces);
        copy.coordinatesForPieces.putAll(coordinatesForPieces);
        copy.coordinatesOnBoard.addAll(coordinatesOnBoard);
        return copy;
    }

    public PlayerColor currentTurnColor() {
        return currentTurnColor;
    }

    public CoordinateDifference coordinateDifferenceForMove(MoveOnBoard move) {
        Coordinates movingPieceCoordinates =
                pieceOnBoardForId(move.pieceId())
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Move must be for a piece on the board"))
                        .coordinates();

        return new CoordinateDifference(movingPieceCoordinates, move.coordinates());
    }

    // TODO: This doesn't feel very good...
    public MoveOnBoard enhanceMove(Move move) {
        PieceOnBoard movingPiece = pieceOnBoardForId(move.pieceId()).get();
        return new MoveOnBoard(movingPiece, getStateFor(move.coordinates()));
    }

    /* TODO: I'm not sure if returning an Optional is right here, we want to be able to account for the given piece
     * not being there, but could there be another way that doesn't require "gets" everywhere? */
    public Optional<PieceOnBoard> pieceOnBoardForId(PieceId id) {
        Optional<Piece> piece = pieces.stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            Coordinates coordinates = coordinatesForPieces.get(piece.get().id());
            return Optional.of(new PieceOnBoard(piece.get(), coordinates));
        }

        return Optional.empty();
    }

    public Optional<PieceOnBoard> pieceAtCoordinates(Coordinates coordinates) {
        return coordinatesForPieces.entrySet().stream()
                .filter(set -> set.getValue() == coordinates)
                .map(Map.Entry::getKey)
                .findFirst()
                .flatMap(
                        id ->
                                pieces.stream()
                                        .filter(p -> p.id() == id)
                                        .findFirst()
                                        .map(piece -> new PieceOnBoard(piece, coordinates)));
    }

    public boolean anyPartOfLineIsOccupied(LineOfCoordinates line) {
        return line.inOrder().stream()
                .anyMatch(c -> coordinateStates().forCoordinates(c).get().isOccupied());
    }

    public BoardCoordinateStates coordinateStates() {
        List<BoardCoordinateState> statesList = new ArrayList<>();

        for (Coordinates coordinates : coordinatesOnBoard) {
            statesList.add(getStateFor(coordinates));
        }

        return new BoardCoordinateStates(statesList);
    }

    private BoardCoordinateState getStateFor(Coordinates coordinates) {
        Optional<Map.Entry<PieceId, Coordinates>> coordinatesForPieceId =
                coordinatesForPieces.entrySet().stream()
                        .filter(e -> e.getValue() == coordinates)
                        .findFirst();

        if (coordinatesForPieceId.isPresent()) {
            Piece piece =
                    pieces.stream()
                            .filter(p -> p.id() == coordinatesForPieceId.get().getKey())
                            .findFirst()
                            .get();

            return new OccupiedCoordinateState(coordinates, piece);
        }

        return new UnoccupiedCoordinateState(coordinates);
    }
}
