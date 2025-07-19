package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification
import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*

class BishopMovementRuleSpec extends Specification {

    def "a bishop can move diagonally"() {
        given:
        def bishop = new Piece(WHITE, PieceType.BISHOP)
        def board = new BoardStateBuilder().addPieceAt(bishop, D4).build()
        def move = new Move(bishop.id(), moveCoordinates)
        MoveLegalityRule rule = new BishopMovementRule()

        when:
        def result = rule.evaluate(board, move)

        then:
        result == MoveLegality.LEGAL

        where:
        moveCoordinates << [E5, C5, C3, E3, B2, B6, F6]
    }

    def "a bishop cannot move straight up or down"() {
        given:
        def bishop = new Piece(WHITE, PieceType.BISHOP)
        def board = new BoardStateBuilder().addPieceAt(bishop, D4).build()
        def move = new Move(bishop.id(), moveCoordinates)
        MoveLegalityRule rule = new BishopMovementRule()

        when:
        def result = rule.evaluate(board, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [D5, C4, D3, E4]
    }

    def "bishops can't move outside those diagonal paths"() {
        given:
        def bishop = new Piece(WHITE, PieceType.BISHOP)
        def board = new BoardStateBuilder().addPieceAt(bishop, D4).build()
        def move = new Move(bishop.id(), moveCoordinates)
        MoveLegalityRule rule = new BishopMovementRule()

        when:
        def result = rule.evaluate(board, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [C2, E2, F3, F5, E6, C6, B5, B3]
    }

}
