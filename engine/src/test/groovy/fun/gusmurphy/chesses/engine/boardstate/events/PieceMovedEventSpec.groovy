package fun.gusmurphy.chesses.engine.boardstate.events

import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.UnknownPieceException
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class PieceMovedEventSpec extends Specification {

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
