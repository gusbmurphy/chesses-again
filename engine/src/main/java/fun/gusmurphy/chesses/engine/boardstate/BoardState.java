package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceRemovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceId;

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

    // TODO: Seems odd that we are asking for a `Piece` and the `Coordinates` separately...
    public Piece pieceForId(PieceId id) throws UnknownPieceException {
        Optional<Piece> piece = pieces.stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            return piece.get();
        }

        throw new UnknownPieceException("Piece does not exist in board state");
    }

    public Coordinates positionForPieceId(PieceId id) {
        return coordinatesForPieces.get(id);
    }

    public void apply(BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            PieceMovedEvent pieceMovedEvent = (PieceMovedEvent) event;
            PieceId pieceId = pieceMovedEvent.pieceId();

            if (pieces.stream().noneMatch(p -> p.id() == pieceId)) {
                throw new UnknownPieceException("Piece does not exist in board state");
            }

            coordinatesForPieces.put(pieceId, pieceMovedEvent.newCoordinates());
        } else if (event instanceof PieceRemovedEvent) {
            PieceRemovedEvent pieceRemovedEvent = (PieceRemovedEvent) event;
            PieceId pieceId = pieceRemovedEvent.pieceId();
            Piece pieceToRemove = pieceForId(pieceId);
            pieces.remove(pieceToRemove);
        }
    }

}
