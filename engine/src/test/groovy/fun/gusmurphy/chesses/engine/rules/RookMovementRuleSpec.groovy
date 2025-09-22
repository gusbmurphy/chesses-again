package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class RookMovementRuleSpec extends Specification {

    private static final Piece TEST_ROOK = new Piece(WHITE, ROOK)
    private static final BoardState TEST_BOARD = new BoardStateBuilder().addPieceAt(TEST_ROOK, D4).build()
    private static final MoveLegalityRule ROOK_RULE = new RookMovementRule()

    def "we are unconcerned with any move for a piece other than a rook"() {
        given:
        def nonRook = new Piece(WHITE, pieceType as PieceType)
        def board = new BoardStateBuilder().addPieceAt(nonRook, D4).build()
        def move = new Move(nonRook.id(), C2)

        when:
        def result = ROOK_RULE.evaluate(board, move)

        then:
        result == MoveLegality.UNCONCERNED

        where:
        pieceType << [BISHOP, PAWN, KING, QUEEN, KNIGHT]
    }

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

    def "a rook can move to a spot in the same rank"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.LEGAL

        where:
        moveCoordinates << [A4, B4, C4, E4, F4, G4, H4]
    }

    def "a rook cannot move diagonally"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [E5, C3, E3, C5]
    }

    def "a rook cannot move to other odd spots"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [F5, G5, C6, E2]
    }

    private static Move rookMoveTo(Coordinates coordinates) {
        return new Move(TEST_ROOK.id(), coordinates)
    }

}
