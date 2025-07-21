package fun.gusmurphy.chesses.engine.events

import fun.gusmurphy.chesses.engine.DerivesMoveEvents
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*

class MoveEventDeriverSpec extends Specification {

    def "moving a piece to another spot on the board does just that"() {
        given:
        DerivesMoveEvents deriver = new MoveEventDeriver()
        def piece = new Piece(WHITE, ROOK)
        def board = new BoardStateBuilder().addPieceAt(piece, A3).build()
        def move = new Move(piece.id(), A7)

        when:
        def result = deriver.deriveEventsFrom(move, board)

        then:
        result.inOrder().size() == 1
        def event = result.inOrder()[0] as PieceMovedEvent
        event.newCoordinates() == A7
        event.pieceId() == piece.id()
    }

}
