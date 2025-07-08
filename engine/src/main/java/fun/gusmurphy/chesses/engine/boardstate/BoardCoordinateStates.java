package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;

import java.util.Optional;

public class BoardCoordinateStates {

    public int size() {
        return 64;
    }

    public Optional<Coordinates> get(Coordinates coordinates) {
        return Optional.of(coordinates);
    }

}
