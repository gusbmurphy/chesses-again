package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class BoardStateSpec extends Specification {

    def "asking for an unknown piece ID throws an UnknownPieceException"() {
        given: "an empty board"
        BoardState boardState = new BoardStateBuilder().build()

        when:
        boardState.pieceOnBoardForId(new PieceId())

        then:
        thrown UnknownPieceException
    }

    def "by default, the board has the normal 8 by 8 dimensions"() {
        given:
        BoardState boardState = new BoardStateBuilder().build()

        when:
        BoardCoordinateStates boardCoordinateStates = boardState.allCoordinateStates()

        then:
        boardCoordinateStates.size() == 64
        Coordinates.values().each { c ->
            assert boardCoordinateStates.get(c).isPresent()
        }
    }

    def "a board can be 2 by 2"() {
        given:
        BoardState boardState = new BoardStateBuilder()
            .width(2)
            .height(2)
            .build()

        when:
        BoardCoordinateStates boardCoordinateStates = boardState.allCoordinateStates()

        then:
        boardCoordinateStates.size() == 4
        [A1, A2, B1, B2].each { c ->
            assert boardCoordinateStates.get(c).isPresent()
        }
    }

}
