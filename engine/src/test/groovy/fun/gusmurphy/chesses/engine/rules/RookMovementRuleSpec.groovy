package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class RookMovementRuleSpec extends Specification {

    private static final Piece TEST_ROOK = new Piece(WHITE, ROOK)
    private static final BoardState TEST_BOARD = new BoardStateBuilder().addPieceAt(TEST_ROOK, D4).build()
    private static final MoveLegalityRule ROOK_RULE = new RookMovementRule()

    def "a rook can move to a spot in the same file"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.LEGAL

        where:
        moveCoordinates << [D1, D2, D3, D5, D6, D7, D8]
    }

    private static Move rookMoveTo(Coordinates coordinates) {
        return new Move(TEST_ROOK.id(), coordinates)
    }

}
