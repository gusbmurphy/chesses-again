package fun.gusmurphy.chesses.engine;

import java.util.ArrayList;
import java.util.List;

public class LineOfCoordinates {

    private final List<Coordinates> coordinates;

    private LineOfCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    static LineOfCoordinates between(Coordinates a, Coordinates b) {
        if (a.sameFileAs(b)) {
            return new LineOfCoordinates(createListOfCoordinatesInHorizontalLineBetween(a, b));
        }

        return new LineOfCoordinates(createListOfCoordinatesInVerticalLineBetween(a, b));
    }

    public List<Coordinates> inOrder() {
        return coordinates;
    }

    private static List<Coordinates> createListOfCoordinatesInHorizontalLineBetween(Coordinates a, Coordinates b) {
        List<Coordinates> lineList = new ArrayList<>();

        Coordinates currentCoordinates = a;

        int direction = currentCoordinates.rank.ordinal() < b.rank.ordinal() ? 1 : -1;

        while (currentCoordinates != b) {
            Rank newRank = Rank.values()[currentCoordinates.rank.ordinal() + direction];
            currentCoordinates = Coordinates.with(a.file, newRank);

            if (currentCoordinates != b) {
                lineList.add(currentCoordinates);
            }
        }

        return lineList;
    }

    private static List<Coordinates> createListOfCoordinatesInVerticalLineBetween(Coordinates a, Coordinates b) {
        List<Coordinates> lineList = new ArrayList<>();

        Coordinates currentCoordinates = a;

        int direction = currentCoordinates.file.ordinal() < b.file.ordinal() ? 1 : -1;

        while (currentCoordinates != b) {
            File newFile = File.values()[currentCoordinates.file.ordinal() + direction];
            currentCoordinates = Coordinates.with(newFile, a.rank);

            if (currentCoordinates != b) {
                lineList.add(currentCoordinates);
            }
        }

        return lineList;
    }


}
