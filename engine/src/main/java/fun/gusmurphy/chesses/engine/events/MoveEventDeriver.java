package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.DerivesMoveEvents;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import java.util.ArrayList;
import java.util.List;

public class MoveEventDeriver implements DerivesMoveEvents {

    @Override
    public BoardStateEvents deriveEventsFrom(Move move, BoardState boardState) {
        List<BoardStateEvent> eventList = new ArrayList<>();

        BoardCoordinateStates coordinateStates = boardState.allCoordinateStates();
        coordinateStates.forCoordinates(move.coordinates())
            .flatMap(BoardCoordinateState::piece)
            .ifPresent(occupyingPiece -> eventList.add(new PieceRemovedEvent(occupyingPiece.id())));

        eventList.add(new PieceMovedEvent(move.pieceId(), move.coordinates()));
        return new BoardStateEvents(eventList.toArray(new BoardStateEvent[0]));
    }

}
