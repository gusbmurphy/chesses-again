package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

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
    }

}
