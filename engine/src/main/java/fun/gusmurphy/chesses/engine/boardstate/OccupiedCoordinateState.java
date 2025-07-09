package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.piece.Piece;
import java.util.Optional;

public class OccupiedCoordinateState implements BoardCoordinateState {

    private final Piece piece;

    OccupiedCoordinateState(Piece piece) {
        this.piece = piece;
    }

    @Override
    public Optional<Piece> piece() {
        return Optional.of(piece);
    }
}
