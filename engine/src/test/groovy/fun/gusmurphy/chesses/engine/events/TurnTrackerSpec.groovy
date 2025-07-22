package fun.gusmurphy.chesses.engine.events

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.PlayerColor.*

class TurnTrackerSpec extends Specification {

    def "the tracker alternates turn colors with every call"() {
        given:
        TracksTurns tracker = new TurnTracker(WHITE)

        expect:
        tracker.turnTaken().newTurnColor() == BLACK

        and:
        tracker.turnTaken().newTurnColor() == WHITE
    }

}
