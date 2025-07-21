package fun.gusmurphy.chesses.engine.events;

public class BoardStateEvents {

    private final BoardStateEvent[] eventList;

    public BoardStateEvents(BoardStateEvent... events) {
        eventList = events.clone();
    }

    public BoardStateEvent[] inOrder() {
        return eventList;
    }

}
