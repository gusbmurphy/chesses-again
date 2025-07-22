package fun.gusmurphy.chesses.engine.events

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.UnknownPieceException
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class BoardStateReducerSpec extends Specification {

    private static final Piece PIECE = new Piece(WHITE, BISHOP)
    private static final BoardState BOARD = new BoardStateBuilder()
        .addPieceAt(PIECE, A7)
        .build()

    private ReducesBoardState reducer = new BoardStateReducer()

    def "a piece moved event will move the given piece on the board"() {
        given:
        BoardStateEvent event = new PieceMovedEvent(PIECE.id(), B6)

        when:
        def result = reducer.reduce(BOARD, event)

        then:
        result.pieceOnBoardForId(PIECE.id()).coordinates() == B6
    }

    def "a piece moved event for a piece not on the board throws an UnknownPieceException"() {
        given:
        BoardState emptyBoard = new BoardStateBuilder().build()
        BoardStateEvent event = new PieceMovedEvent(PIECE.id(), B6)

        when:
        reducer.reduce(emptyBoard, event)

        then:
        thrown UnknownPieceException
    }

}
