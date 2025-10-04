package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.events.BoardStateEvent
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent
import fun.gusmurphy.chesses.engine.events.TurnChangeEvent
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class BoardStateReducerSpec extends Specification {

    private static final Piece PIECE = new Piece(WHITE, BISHOP)
    private static final PIECE_STARTING_POSITION = A7
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
        result.pieceOnBoardForId(PIECE.id()).get().coordinates() == B6
    }

    def "the original board state is not modified"() {
        given:
        BoardStateEvent event = new PieceMovedEvent(PIECE.id(), B6)

        when:
        def result = reducer.reduce(BOARD, event)

        then:
        result != BOARD
        BOARD.pieceOnBoardForId(PIECE.id()).get().coordinates() == A7
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

    def "a piece removed event removes a piece from the board"() {
        given:
        BoardStateEvent event = new PieceRemovedEvent(PIECE.id())

        when:
        def result = reducer.reduce(BOARD, event)

        then:
        result.pieceOnBoardForId(PIECE.id()).isPresent() == false
        // TODO: Looking at some major train wrecks going on here...
        result.coordinateStates().forCoordinates(PIECE_STARTING_POSITION).get().piece().isPresent() == false
    }

    def "removing a piece that is not on the board throws an exception"() {
        given:
        BoardState emptyBoard = new BoardStateBuilder().build()
        BoardStateEvent event = new PieceRemovedEvent(PIECE.id())

        when:
        reducer.reduce(emptyBoard, event)

        then:
        thrown UnknownPieceException
    }

    def "a turn change event changes the current turn color"() {
        given:
        def boardWithTurnColor = new BoardStateBuilder().currentTurnColor(newColor.opposite()).build()
        def turnChangeEvent = new TurnChangeEvent(newColor)

        when:
        def result = reducer.reduce(boardWithTurnColor, turnChangeEvent)

        then:
        result.currentTurnColor() == newColor

        where:
        newColor << [WHITE, BLACK]
    }

}
