package fun.gusmurphy.chesses.engine.boardstate.events

import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.UnknownPieceException
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class PieceRemovedEventSpec extends Specification {

    def "a piece removed event removes a piece from the board"() {
        given:
        Piece piece = new Piece(PlayerColor.WHITE)
        BoardState board = new BoardStateBuilder()
            .addPieceAt(piece, A7)
            .build()
        BoardStateEvent event = new PieceRemovedEvent(piece.id())

        when:
        board.apply(event)
        board.pieceForId(piece.id())

        then:
        thrown UnknownPieceException
    }

}
