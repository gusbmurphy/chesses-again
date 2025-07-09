package fun.gusmurphy.chesses.engine.boardstate;

import fun.gusmurphy.chesses.engine.piece.Piece;
import java.util.Optional;

public class UnoccupiedCoordinateState implements BoardCoordinateState {

    @Override
    public Optional<Piece> piece() {
        return Optional.empty();
    }
}
