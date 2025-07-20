package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class RookMovementRuleSpec extends Specification {

    def "a rook can move to a spot in the same column"() {
        given:
        def rook = new Piece(WHITE, ROOK)
        def board = new BoardStateBuilder().addPieceAt(rook, D4).build()
        def move = new Move(rook.id(), moveCoordinates)
        MoveLegalityRule rule = new RookMovementRule()

        when:
        def result = rule.evaluate(board, move)

        then:
        result == MoveLegality.LEGAL

        where:
        moveCoordinates << [E5, C5, C3, E3, B2, B6, F6]
    }

}
