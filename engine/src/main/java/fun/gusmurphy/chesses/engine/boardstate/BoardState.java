package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.File;
import fun.gusmurphy.chesses.engine.Rank;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.TurnChangeEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;

import java.util.*;

public class BoardState {

    private PlayerColor currentTurnColor;
    private final Set<Piece> pieces = new HashSet<>();
    private final Map<PieceId, Coordinates> coordinatesForPieces = new HashMap<>();
    private final Set<Coordinates> coordinatesOnBoard;

    protected BoardState(
        PlayerColor currentTurnColor,
        HashMap<Coordinates, Piece> piecesAtCoordinates,
        Set<Rank> ranks,
        Set<File> files
    ) {
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

    public PlayerColor currentTurnColor() {
        return currentTurnColor;
    }

    public PieceOnBoard pieceOnBoardForId(PieceId id) throws UnknownPieceException {
        Optional<Piece> piece = pieces.stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            Coordinates coordinates = coordinatesForPieces.get(piece.get().id());
            return new PieceOnBoard(piece.get(), coordinates);
        }

        throw new UnknownPieceException("Piece does not exist in board state");
    }

    public Coordinates coordinatesForPieceId(PieceId id) throws UnknownPieceException {
        Coordinates coordinates = coordinatesForPieces.get(id);

        if (coordinates == null) {
            throw new UnknownPieceException("Piece does not exist in board state");
        }

        return coordinates;
    }

    public BoardCoordinateStates allCoordinateStates() {
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
            Piece piece = pieces.stream()
                .filter(p -> p.id() == coordinatesForPieceId.get().getKey())
                .findFirst()
                .get();

            return new OccupiedCoordinateState(coordinates, piece);
        }

        return new UnoccupiedCoordinateState(coordinates);
    }

    public void apply(BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            handlePieceMoved((PieceMovedEvent) event);
        } else if (event instanceof PieceRemovedEvent) {
            handlePieceRemoved((PieceRemovedEvent) event);
        } else if (event instanceof TurnChangeEvent) {
            handleTurnChange((TurnChangeEvent) event);
        }
    }

    private void handlePieceMoved(PieceMovedEvent event) {
        PieceId pieceId = event.pieceId();

        if (pieces.stream().noneMatch(p -> p.id() == pieceId)) {
            throw new UnknownPieceException("Piece does not exist in board state");
        }

        coordinatesForPieces.put(pieceId, event.newCoordinates());
    }

    private void handlePieceRemoved(PieceRemovedEvent event) {
        PieceId pieceId = event.pieceId();
        Optional<Piece> pieceToRemove = pieces.stream().filter(p -> p.id() == pieceId).findFirst();

        if (pieceToRemove.isPresent()) {
            pieces.remove(pieceToRemove.get());
        } else {
            throw new UnknownPieceException("Piece does not exist in board state");
        }
    }

    private void handleTurnChange(TurnChangeEvent event) {
        currentTurnColor = event.newTurnColor();
    }

}
