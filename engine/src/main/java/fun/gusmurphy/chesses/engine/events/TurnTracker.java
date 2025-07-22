package fun.gusmurphy.chesses.engine.events;

import fun.gusmurphy.chesses.engine.PlayerColor;

public class TurnTracker implements TracksTurns {

    private PlayerColor currentTurnColor;

    public TurnTracker(PlayerColor initialTurnColor) {
        currentTurnColor = initialTurnColor;
    }

    @Override
    public TurnChangeEvent turnTaken() {
        currentTurnColor = currentTurnColor.opposite();
        return new TurnChangeEvent(currentTurnColor);
    }

}
