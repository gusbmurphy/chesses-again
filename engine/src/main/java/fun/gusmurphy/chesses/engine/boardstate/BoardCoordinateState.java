package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;

import java.util.Optional;

public interface BoardCoordinateState {

    Optional<Piece> piece();
    Coordinates coordinates();

}
