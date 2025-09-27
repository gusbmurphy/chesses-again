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

class BishopMovementRuleSpec extends Specification {

    private static final Piece TEST_BISHOP = new Piece(WHITE, BISHOP)
    private static final BoardState TEST_BOARD = new BoardStateBuilder().addPieceAt(TEST_BISHOP, D4).build()
    private static final MoveRule BISHOP_RULE = new BishopMovementRule()

    def "a bishop can move diagonally"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == Legality.LEGAL

        where:
        moveCoordinates << [E5, C5, C3, E3, B2, B6, F6]
    }

    def "a bishop cannot move straight up or down"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == Legality.ILLEGAL

        where:
        moveCoordinates << [D5, C4, D3, E4]
    }

    def "bishops can't move outside those diagonal paths"() {
        given:
        def move = bishopMoveTo(moveCoordinates)

        when:
        def result = BISHOP_RULE.evaluate(TEST_BOARD, move)

        then:
        result == Legality.ILLEGAL

        where:
        moveCoordinates << [C2, E2, F3, F5, E6, C6, B5, B3]
    }

    def "the rule is only concerned with bishops"() {
        expect:
        BISHOP_RULE.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | true
        ROOK      | false
        KNIGHT    | false
        KING      | false
        QUEEN     | false
        PAWN      | false
    }

    private static Move bishopMoveTo(Coordinates coordinates) {
        return new Move(TEST_BISHOP.id(), coordinates)
    }

}
