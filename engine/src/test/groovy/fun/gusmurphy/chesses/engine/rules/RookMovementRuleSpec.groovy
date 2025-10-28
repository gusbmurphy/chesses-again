package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class RookMovementRuleSpec extends MoveRuleSpecification {

    private static final Piece TEST_ROOK = new Piece(WHITE, ROOK)
    private static final BoardState TEST_BOARD = new BoardStateBuilder().addPieceAt(TEST_ROOK, D4).build()
    private static final MoveRule ROOK_RULE = new RookMovementRule()

    def "the rule is only concerned with rooks"() {
        expect:
        ROOK_RULE.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | true
        KNIGHT    | false
        KING      | false
        QUEEN     | false
        PAWN      | false
    }

    def "a rook can move to a spot in the same file"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        evaluationIsLegal(result)

        where:
        moveCoordinates << [D1, D2, D3, D5, D6, D7, D8]
    }

    def "a rook can move to a spot in the same rank"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        evaluationIsLegal(result)

        where:
        moveCoordinates << [A4, B4, C4, E4, F4, G4, H4]
    }

    def "a rook cannot move diagonally"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        evaluationIsIllegalWithNoEffects(result)

        where:
        moveCoordinates << [E5, C3, E3, C5]
    }

    def "a rook cannot move to other odd spots"() {
        given:
        def move = rookMoveTo(moveCoordinates)

        when:
        def result = ROOK_RULE.evaluate(TEST_BOARD, move)

        then:
        evaluationIsIllegalWithNoEffects(result)

        where:
        moveCoordinates << [F5, G5, C6, E2]
    }

    private static Move rookMoveTo(Coordinates coordinates) {
        return new Move(TEST_ROOK.id(), coordinates)
    }

}
