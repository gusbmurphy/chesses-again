package fun.gusmurphy.chesses.engine.boardstate.events

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import spock.lang.Specification
import static fun.gusmurphy.chesses.engine.PlayerColor.*

class TurnChangeEventSpec extends Specification {

    def "a turn change event changes the current turn color"() {
        given:
        def board = new BoardStateBuilder().currentTurnColor(newColor.opposite()).build()
        def turnChangeEvent = new TurnChangeEvent(newColor)

        when:
        board.apply(turnChangeEvent)

        then:
        board.currentTurnColor() == newColor

        where:
        newColor << [WHITE, BLACK]
    }

}
