package fun.gusmurphy.chesses.engine.boardstate.events;

public class BoardStateEvents {

    private final BoardStateEvent[] eventList;

    public BoardStateEvents(BoardStateEvent... events) {
        eventList = events.clone();
    }

    public BoardStateEvent[] inOrder() {
        return eventList;
    }

}
