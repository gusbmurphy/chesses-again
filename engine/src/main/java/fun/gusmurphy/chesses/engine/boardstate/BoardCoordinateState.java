package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;
import java.util.Optional;

public interface BoardCoordinateState {

    Optional<Piece> piece();

    Coordinates coordinates();

    boolean isOccupied();

    boolean isUnoccupied();
}
