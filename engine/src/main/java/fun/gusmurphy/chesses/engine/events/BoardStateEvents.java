package fun.gusmurphy.chesses.engine.events;

public class BoardStateEvents {

    private final BoardStateEvent[] events;

    public BoardStateEvents(BoardStateEvent... events) {
        this.events = events;
    }

    public BoardStateEvent[] inOrder() {
        return events;
    }
}
