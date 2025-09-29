package fun.gusmurphy.chesses.engine;

import java.util.ArrayList;
import java.util.List;

public class LineOfCoordinates {

    private final List<Coordinates> coordinates;

    private LineOfCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    static LineOfCoordinates between(Coordinates a, Coordinates b) {
        List<Coordinates> coordinatesList = createListOfCoordinatesBetween(a, b);
        return new LineOfCoordinates(coordinatesList);
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
            Rank newRank = Rank.values()[currentCoordinates.rank.ordinal() + horizontalChange];
            File newFile = File.values()[currentCoordinates.file.ordinal() + verticalChange];
            currentCoordinates = Coordinates.with(newFile, newRank);

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

        return a.rank.ordinal() < b.rank.ordinal() ? 1 : -1;
    }

    private static int getVerticalChange(Coordinates a, Coordinates b) {
        if (a.sameFileAs(b)) {
            return 0;
        }

        return a.file.ordinal() < b.file.ordinal() ? 1 : -1;
    }

}
