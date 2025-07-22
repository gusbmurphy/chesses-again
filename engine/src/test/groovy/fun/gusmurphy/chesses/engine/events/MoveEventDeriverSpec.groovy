package fun.gusmurphy.chesses.engine.events

import fun.gusmurphy.chesses.engine.DerivesMoveEvents
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*

class MoveEventDeriverSpec extends Specification {

    private final static Piece PIECE_A = new Piece(WHITE, ROOK)
    private final static Piece PIECE_B = new Piece(BLACK, ROOK)

    private final static BoardState BOARD = new BoardStateBuilder()
        .addPieceAt(PIECE_A, A3)
        .addPieceAt(PIECE_B, D3)
        .build()

    private final TracksTurns turnTracker = Mock(TracksTurns)
    private final DerivesMoveEvents deriver = new MoveEventDeriver(turnTracker)

    def "moving a piece to another spot on the board does just that"() {
        given:
        def move = new Move(PIECE_A.id(), A7)

        when:
        def result = deriver.deriveEventsFrom(move, BOARD)

        then:
        def event = result.inOrder()[0] as PieceMovedEvent
        event.newCoordinates() == A7
        event.pieceId() == PIECE_A.id()
    }

    def "moving a piece to an occupied space removes the piece from that space, and then moves the piece"() {
        given:
        def move = new Move(PIECE_A.id(), D3)

        when:
        def result = deriver.deriveEventsFrom(move, BOARD)

        then:
        def e1 = result.inOrder()[0] as PieceRemovedEvent
        e1.pieceId() == PIECE_B.id()

        def e2 = result.inOrder()[1] as PieceMovedEvent
        e2.newCoordinates() == D3
        e2.pieceId() == PIECE_A.id()
    }

    def "the turn tracker is consulted for the new turn color"() {
        given:
        def turnChangeEvent = new TurnChangeEvent(WHITE)
        def move = new Move(PIECE_A.id(), A7)

        when:
        def result = deriver.deriveEventsFrom(move, BOARD)

        then:
        1 * turnTracker.turnTaken() >> turnChangeEvent
        result.inOrder().any { event -> event == turnChangeEvent }
    }

}
