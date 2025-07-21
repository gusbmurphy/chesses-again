package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.piece.PieceId;

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

    public Optional<BoardCoordinateState> forPieceId(PieceId pieceId) {
        return coordinateStateList.stream()
            .filter(cs -> cs.piece().isPresent() && cs.piece().get().id() == pieceId)
            .findFirst();
    }

    public Optional<BoardCoordinateState> forCoordinates(Coordinates coordinates) {
        return coordinateStateList.stream()
            .filter(cs -> cs.coordinates() == coordinates)
            .findFirst();
    }

}
