package fun.gusmurphy.chesses.engine.boardstate.events;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class TurnChangeEvent implements BoardStateEvent {

    private final PlayerColor newTurnColor;

    public TurnChangeEvent(PlayerColor newTurnColor) {
        this.newTurnColor = newTurnColor;
    }

    public PlayerColor newTurnColor() {
        return newTurnColor;
    }

}
