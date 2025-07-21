package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.DerivesMoveEvents;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoveEventDeriver implements DerivesMoveEvents {

    @Override
    public BoardStateEvents deriveEventsFrom(Move move, BoardState boardState) {
        List<BoardStateEvent> eventList = new ArrayList<>();

        createEventForTakenPiece(move, boardState).ifPresent(eventList::add);
        eventList.add(PieceMovedEvent.from(move));

        return new BoardStateEvents(eventList.toArray(new BoardStateEvent[0]));
    }

    private Optional<PieceRemovedEvent> createEventForTakenPiece(Move move, BoardState boardState) {
        BoardCoordinateStates coordinateStates = boardState.allCoordinateStates();
        return coordinateStates.forCoordinates(move.coordinates())
            .flatMap(BoardCoordinateState::piece)
            .map(occupyingPiece -> new PieceRemovedEvent(occupyingPiece.id()));
    }

}
