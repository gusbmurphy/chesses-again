package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.events.BoardStateEvents
import fun.gusmurphy.chesses.engine.doubles.DummyBoardStateEvent
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

class MoveApplicatorSpec extends Specification {

    private static final INITIAL_BOARD = new BoardStateBuilder().build()
    private static final REDUCED_BOARD = new BoardStateBuilder().addPieceAt(
        new Piece(PlayerColor.WHITE, PieceType.ROOK), Coordinates.A1
    ).build()
    private static final DUMMY_MOVE = new Move(new PieceId(), Coordinates.A1)
    private static final DERIVED_EVENTS = new BoardStateEvents(new DummyBoardStateEvent())

    private DerivesMoveEvents eventDeriver = Mock(DerivesMoveEvents)
    private ReducesBoardState boardStateReducer = Mock(ReducesBoardState)

    AppliesMoves applicator = new MoveApplicator(eventDeriver, boardStateReducer)

    def "events are derived from the move, and then passed to the reducer to produce the new board state"() {
        when:
        def result = applicator.applyMoveToBoard(DUMMY_MOVE, INITIAL_BOARD)

        then:
        1 * eventDeriver.deriveEventsFrom(DUMMY_MOVE, INITIAL_BOARD) >> DERIVED_EVENTS
        1 * boardStateReducer.reduce(INITIAL_BOARD, DERIVED_EVENTS) >> REDUCED_BOARD
        result == REDUCED_BOARD
    }

}
