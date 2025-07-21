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

    def "moving a piece to an occupied space removes the piece from that space, and then moves the piece"() {
        given:
        DerivesMoveEvents deriver = new MoveEventDeriver()
        def movingPiece = new Piece(WHITE, ROOK)
        def takenPiece = new Piece(WHITE, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, A3)
            .addPieceAt(takenPiece, D3)
            .build()
        def move = new Move(movingPiece.id(), D3)

        when:
        def result = deriver.deriveEventsFrom(move, board)

        then:
        result.inOrder().size() == 2

        def e1 = result.inOrder()[0] as PieceRemovedEvent
        e1.pieceId() == takenPiece.id()

        def e2 = result.inOrder()[1] as PieceMovedEvent
        e2.newCoordinates() == D3
        e2.pieceId() == movingPiece.id()
    }

}
