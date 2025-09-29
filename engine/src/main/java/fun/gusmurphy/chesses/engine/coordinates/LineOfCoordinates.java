package fun.gusmurphy.chesses.engine.coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LineOfCoordinates {

    private final List<Coordinates> coordinates;

    private LineOfCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    static Optional<LineOfCoordinates> between(Coordinates a, Coordinates b) {
        if (!a.isDiagonalFrom(b) && !a.sameFileAs(b) && ! a.sameRankAs(b) ) {
            return Optional.empty();
        }

        List<Coordinates> coordinatesList = createListOfCoordinatesBetween(a, b);
        return Optional.of(new LineOfCoordinates(coordinatesList));
    }

    public List<Coordinates> inOrder() {
        return coordinates;
    }

    private static List<Coordinates> createListOfCoordinatesBetween(Coordinates a, Coordinates b) {
        List<Coordinates> lineList = new ArrayList<>();

        Coordinates currentCoordinates = a;

        int horizontalChange = getHorizontalChange(a, b);
        int verticalChange = getVerticalChange(a, b);

        while (currentCoordinates != b) {
            currentCoordinates = currentCoordinates.coordinatesTo(horizontalChange, verticalChange);

            if (currentCoordinates != b) {
                lineList.add(currentCoordinates);
            }
        }

        return lineList;
    }

    private static int getHorizontalChange(Coordinates a, Coordinates b) {
        if (a.sameRankAs(b)) {
            return 0;
        }

        return a.rankDifferenceTo(b) < 0 ? 1 : -1;
    }

    private static int getVerticalChange(Coordinates a, Coordinates b) {
        if (a.sameFileAs(b)) {
            return 0;
        }

        return a.fileDifferenceTo(b) < 0 ? 1 : -1;
    }

}
