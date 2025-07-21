package fun.gusmurphy.chesses.engine;

import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvents;

public interface DerivesMoveEvents {

    BoardStateEvents deriveEventsFrom(Move move, BoardState boardState);

}
