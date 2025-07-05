package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.PieceId;

import java.util.Optional;
import java.util.Set;

public class BoardState {

    private final PlayerColor currentTurnColor;
    private final Set<Piece> pieces;

    protected BoardState(PlayerColor currentTurnColor, Set<Piece> pieces) {
        this.currentTurnColor = currentTurnColor;
        this.pieces = pieces;
    }

    public PlayerColor currentTurnColor() {
        return currentTurnColor;
    }

    public Piece pieceForId(PieceId id) throws UnknownPieceException {
        Optional<Piece> piece = pieces.stream().filter(p -> p.id() == id).findFirst();

        if (piece.isPresent()) {
            return piece.get();
        }

        throw new UnknownPieceException("Piece does not exist in board state");
    }

}
