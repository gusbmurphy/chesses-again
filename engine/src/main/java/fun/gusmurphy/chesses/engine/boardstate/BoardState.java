package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvent;
import fun.gusmurphy.chesses.engine.boardstate.events.PieceMovedEvent;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceId;

import java.util.HashMap;
import java.util.Optional;

public class BoardState {

    private final PlayerColor currentTurnColor;
    private final HashMap<Coordinates, Piece> piecesByCoordinates;

    protected BoardState(PlayerColor currentTurnColor, HashMap<Coordinates, Piece> piecesByCoordinates) {
        this.currentTurnColor = currentTurnColor;
        this.piecesByCoordinates = piecesByCoordinates;
    }

    public PlayerColor currentTurnColor() {
        return currentTurnColor;
    }

    public Piece pieceForId(PieceId id) throws UnknownPieceException {
        Optional<Piece> piece = piecesByCoordinates.values().stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            return piece.get();
        }

        throw new UnknownPieceException("Piece does not exist in board state");
    }

    public Coordinates positionForPieceId(PieceId id) {
        return piecesByCoordinates.entrySet().stream()
            .filter(entry -> entry.getValue().id() == id)
            .findFirst()
            .get()
            .getKey();
    }

    public void apply(BoardStateEvent event) {
        if (event instanceof PieceMovedEvent) {
            PieceMovedEvent pieceMovedEvent = (PieceMovedEvent) event;
            PieceId pieceId = pieceMovedEvent.pieceId();
            Piece piece = pieceForId(pieceId);
            piecesByCoordinates.put(pieceMovedEvent.newCoordinates(), piece);
        }
    }

}
