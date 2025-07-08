package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;

import java.util.*;

public class BoardState {

    private final PlayerColor currentTurnColor;
    private final Set<Piece> pieces = new HashSet<>();
    private final Map<PieceId, Coordinates> coordinatesForPieces = new HashMap<>();

    protected BoardState(PlayerColor currentTurnColor, HashMap<Coordinates, Piece> piecesAtCoordinates) {
        this.currentTurnColor = currentTurnColor;

        for (Map.Entry<Coordinates, Piece> pieceAtCoordinates : piecesAtCoordinates.entrySet()) {
            Piece piece = pieceAtCoordinates.getValue();
            pieces.add(piece);
            coordinatesForPieces.put(piece.id(), pieceAtCoordinates.getKey());
        }
    }

    public PlayerColor currentTurnColor() {
        return currentTurnColor;
    }

    /**
     * @deprecated
     * Seems like we should be using {@link BoardCoordinateStates}
     */
    @Deprecated
    public PieceOnBoard pieceOnBoardForId(PieceId id) throws UnknownPieceException {
        Optional<Piece> piece = pieces.stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            Coordinates coordinates = coordinatesForPieces.get(piece.get().id());
            return new PieceOnBoard(piece.get(), coordinates);
        }

        throw new UnknownPieceException("Piece does not exist in board state");
    }

    public BoardCoordinateStates allCoordinateStates() {
        return new BoardCoordinateStates();
    }

    public void apply(BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            handlePieceMoved((PieceMovedEvent) event);
        } else if (event instanceof PieceRemovedEvent) {
            handlePieceRemoved((PieceRemovedEvent) event);
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

}
