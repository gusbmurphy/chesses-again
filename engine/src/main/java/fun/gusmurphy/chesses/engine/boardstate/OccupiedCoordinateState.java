package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;

import java.util.Optional;

public class OccupiedCoordinateState implements BoardCoordinateState {

    private final Piece piece;
    private final Coordinates coordinates;

    OccupiedCoordinateState(Coordinates coordinates, Piece piece) {
        this.coordinates = coordinates;
        this.piece = piece;
    }

    @Override
    public Optional<Piece> piece() {
        return Optional.of(piece);
    }

    public Coordinates coordinates() {
        return coordinates;
    }

}
