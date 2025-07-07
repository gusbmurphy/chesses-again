package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvent
import fun.gusmurphy.chesses.engine.boardstate.events.PieceMovedEvent
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceId
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class BoardStateSpec extends Specification {

    def "asking for an unknown piece ID throws an UnknownPieceException"() {
        given: "an empty board"
        BoardState boardState = new BoardStateBuilder().build()

        when:
        boardState.pieceForId(new PieceId())

        then:
        thrown UnknownPieceException
    }

    def "a piece moved event will move the given piece on the board"() {
        given:
        Piece piece = new Piece(PlayerColor.WHITE)
        BoardState board = new BoardStateBuilder()
            .addPieceAt(piece, A7)
            .build()
        BoardStateEvent event = new PieceMovedEvent(piece.id(), B6)

        when:
        board.apply(event)

        then:
        board.positionForPieceId(piece.id()) == B6
    }

    def "a piece moved event for a piece not on the board throws an UnknownPieceException"() {
        given:
        BoardState board = new BoardStateBuilder().build()
        Piece piece = new Piece(PlayerColor.WHITE)
        BoardStateEvent event = new PieceMovedEvent(piece.id(), B6)

        when:
        board.apply(event)

        then:
        thrown UnknownPieceException
    }

}
