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

class BishopMovementRuleSpec extends Specification {

    private static final Piece TEST_BISHOP = new Piece(WHITE, BISHOP)
    private static final BoardState TEST_BOARD = new BoardStateBuilder().addPieceAt(TEST_BISHOP, D4).build()
    private static final MoveLegalityRule BISHOP_RULE = new BishopMovementRule()

    def "a bishop can move diagonally"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.LEGAL

        where:
        moveCoordinates << [E5, C5, C3, E3, B2, B6, F6]
    }

    def "a bishop cannot move straight up or down"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [D5, C4, D3, E4]
    }

    def "bishops can't move outside those diagonal paths"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [C2, E2, F3, F5, E6, C6, B5, B3]
    }

    def "the bishop movement rule is not concerned with a non-bishop piece"() {
        given:
        def nonBishop = new Piece(WHITE, pieceType as PieceType)
        def board = new BoardStateBuilder().addPieceAt(nonBishop, D4).build()
        def move = new Move(nonBishop.id(), C2)

        when:
        def result = BISHOP_RULE.evaluate(board, move)

        then:
        result == MoveLegality.UNCONCERNED

        where:
        pieceType << [ROOK, PAWN, KING, QUEEN, KNIGHT]
    }

    private static Move bishopMoveTo(Coordinates coordinates) {
        return new Move(TEST_BISHOP.id(), coordinates)
    }

}
