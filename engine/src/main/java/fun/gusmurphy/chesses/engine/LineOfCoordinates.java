package fun.gusmurphy.chesses.engine;

import java.util.List;

public class LineOfCoordinates {

    private final List<Coordinates> coordinates;

    LineOfCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Coordinates> inOrder() {
        return coordinates;
    }

}
