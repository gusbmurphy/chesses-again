package fun.gusmurphy.chesses.engine.coordinates;

public class CoordinateDifference {

    private final Coordinates start;
    private final Coordinates end;

    public CoordinateDifference(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }

    public boolean isNotDiagonal() {
        return Math.abs(rankDifference()) != Math.abs(fileDifference());
    }

    private int rankDifference() {
        return end.rankDifferenceTo(start);
    }

    private int fileDifference() {
        return end.fileDifferenceTo(start);
    }
}
