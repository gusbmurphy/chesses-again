package fun.gusmurphy.chesses.engine.boardstate

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

class BoardCoordinateStatesSpec extends Specification {

    def "asking for an unknown piece ID returns nothing"() {
        given: "an empty board"
        BoardState boardState = new BoardStateBuilder().build()

        when:
        def result = boardState.allCoordinateStates().forPieceId(new PieceId())

        then:
        result.isEmpty()
    }

    def "we can get the state for a piece we've placed"() {
        given: "an empty board"
        Piece piece = new Piece(PlayerColor.WHITE)
        BoardState boardState = new BoardStateBuilder()
            .addPieceAt(piece, Coordinates.B6)
            .build()

        when:
        def result = boardState.allCoordinateStates().forPieceId(piece.id())

        then:
        result.isPresent()
        result.get().piece().get() == piece
        result.get().coordinates() == Coordinates.B6
    }

    def "we can get just the occupied spaces"() {
        given:
        def occupiedSpaceA = new OccupiedCoordinateState(Coordinates.A1, new Piece(PlayerColor.WHITE, PieceType.BISHOP))
        def occupiedSpaceB = new OccupiedCoordinateState(Coordinates.A3, new Piece(PlayerColor.WHITE, PieceType.BISHOP))
        def unoccupiedSpace = new UnoccupiedCoordinateState(Coordinates.A7)

        def states = new BoardCoordinateStates(List.of(occupiedSpaceA, occupiedSpaceB, unoccupiedSpace))

        when:
        BoardCoordinateStates occupiedStates = states.justOccupiedStates()

        then:
        occupiedStates.forCoordinates(Coordinates.A1).isPresent()
        occupiedStates.forCoordinates(Coordinates.A3).isPresent()
        occupiedStates.forCoordinates(Coordinates.A7).isEmpty()
    }

}
