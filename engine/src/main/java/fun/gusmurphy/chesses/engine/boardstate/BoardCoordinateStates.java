package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;

import java.util.List;
import java.util.Optional;

public class BoardCoordinateStates {

    private final List<BoardCoordinateState> coordinateStateList;

    BoardCoordinateStates(List<BoardCoordinateState> coordinateStateList) {
        this.coordinateStateList = coordinateStateList;
    }

    public int size() {
        return coordinateStateList.size();
    }

    public Optional<Coordinates> get(Coordinates coordinates) {
        return Optional.of(coordinates);
    }

}
