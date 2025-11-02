package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;
import java.util.Optional;

public class UnoccupiedCoordinateState implements BoardCoordinateState {

    private final Coordinates coordinates;

    public UnoccupiedCoordinateState(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Optional<Piece> piece() {
        return Optional.empty();
    }

    @Override
    public Coordinates coordinates() {
        return coordinates;
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public boolean isUnoccupied() {
        return true;
    }
}
