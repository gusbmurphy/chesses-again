package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class KnightMovementRuleSpec extends MoveRuleSpecification {

    private static final MoveRule KNIGHT_RULE = new KnightMovementRule()
    private static final KNIGHT_POSITION = D5
    private static final L_COORDINATES = [C7, E7, F6, F4, E3, C3, B4, B6]

    def "the rule is only concerned with knights"() {
        expect:
        KNIGHT_RULE.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | true
        KING      | false
        QUEEN     | false
        PAWN      | false
    }

    def "the knight can move in an L shape"() {
        given:
        def piece = new Piece(BLACK, KNIGHT)
        def board = new BoardStateBuilder().addPieceAt(piece, KNIGHT_POSITION).build()

        expect:
        def result = KNIGHT_RULE.evaluate(board, new Move(piece.id(), coordinates))
        evaluationIsLegal(result)

        where:
        coordinates << L_COORDINATES
    }

    def "the knight can NOT move outside of that L shape"() {
        given:
        def piece = new Piece(BLACK, KNIGHT)
        def board = new BoardStateBuilder().addPieceAt(piece, KNIGHT_POSITION).build()

        expect:
        def result = KNIGHT_RULE.evaluate(board, new Move(piece.id(), coordinates as Coordinates))
        evaluationIsIllegal(result)

        where:
        coordinates << Arrays.asList(Coordinates.values()) - L_COORDINATES
    }

}
