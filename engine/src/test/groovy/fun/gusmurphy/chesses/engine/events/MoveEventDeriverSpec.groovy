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

    private final static Piece PIECE_A = new Piece(WHITE, ROOK)
    private final static Piece PIECE_B = new Piece(BLACK, ROOK)

    private final DerivesMoveEvents deriver = new MoveEventDeriver()

    def "moving a piece to another spot on the board does just that"() {
        given:
        def board = new BoardStateBuilder().addPieceAt(PIECE_A, A3).build()
        def move = new Move(PIECE_A.id(), A7)

        when:
        def result = deriver.deriveEventsFrom(move, board)

        then:
        result.inOrder().size() == 1
        def event = result.inOrder()[0] as PieceMovedEvent
        event.newCoordinates() == A7
        event.pieceId() == PIECE_A.id()
    }

    def "moving a piece to an occupied space removes the piece from that space, and then moves the piece"() {
        given:
        def board = new BoardStateBuilder()
            .addPieceAt(PIECE_A, A3)
            .addPieceAt(PIECE_B, D3)
            .build()
        def move = new Move(PIECE_A.id(), D3)

        when:
        def result = deriver.deriveEventsFrom(move, board)

        then:
        result.inOrder().size() == 2

        def e1 = result.inOrder()[0] as PieceRemovedEvent
        e1.pieceId() == PIECE_B.id()

        def e2 = result.inOrder()[1] as PieceMovedEvent
        e2.newCoordinates() == D3
        e2.pieceId() == PIECE_A.id()
    }

}
