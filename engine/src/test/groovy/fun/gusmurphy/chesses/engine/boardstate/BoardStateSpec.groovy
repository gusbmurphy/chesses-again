package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

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
        BoardCoordinateStates boardCoordinateStates = boardState.coordinateStates()

        then:
        boardCoordinateStates.size() == 64
        Coordinates.values().each { c ->
            assert boardCoordinateStates.forCoordinates(c).isPresent()
        }
    }

    def "a board can be a number of odd dimensions"() {
        given:
        BoardState boardState = new BoardStateBuilder()
            .width(width)
            .height(height)
            .build()

        when:
        BoardCoordinateStates boardCoordinateStates = boardState.coordinateStates()

        then:
        boardCoordinateStates.size() == expectedSize
        expectedPresentCoordinates.each { c ->
            assert boardCoordinateStates.forCoordinates(c).isPresent()
        }

        where:
        width | height || expectedSize | expectedPresentCoordinates
        2     | 2      || 4            | [A1, A2, B1, B2]
        1     | 2      || 2            | [A1, A2]
        2     | 1      || 2            | [A1, B1]
    }

    def "a board cannot be 1 by 1"() {
        given:
        def builder = new BoardStateBuilder()
            .width(1)
            .height(1)

        when:
        builder.build()

        then:
        def exception = thrown IllegalArgumentException
        exception.message == "Board must have at least two coordinates"
    }

}
