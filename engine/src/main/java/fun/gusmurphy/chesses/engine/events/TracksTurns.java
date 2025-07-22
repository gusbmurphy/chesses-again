package fun.gusmurphy.chesses.engine.events;

public interface TracksTurns {

    /**
     * Tells the tracker that a turn has been taken.
     * @return The `TurnChangeEvent` for the next turn.
     */
    TurnChangeEvent turnTaken();

}
