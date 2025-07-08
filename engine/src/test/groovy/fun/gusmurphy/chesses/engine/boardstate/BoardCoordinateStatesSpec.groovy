package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

class BoardCoordinateStatesSpec extends Specification {

    def "asking for an unknown piece ID returns nothing"() {
        given: "an empty board"
        BoardState boardState = new BoardStateBuilder().build()

        when:
        def result = boardState.allCoordinateStates().forPieceId(new PieceId())

        then:
        result.isEmpty()
    }

}
