package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.WHITE
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class KingMovementRuleSpec extends Specification {

    private static final MoveRule KING_RULE = new KingMovementRule()

    def "the rule is only concerned with kings"() {
        expect:
        KING_RULE.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | true
        QUEEN     | false
        PAWN      | false
    }

    def "a king can move to any spot right next to it"() {
        given:
        def king = new Piece(WHITE, KING)
        def board = new BoardStateBuilder().addPieceAt(king, D4).build()
        def move = new Move(king.id(), moveCoordinates)

        when:
        def result = KING_RULE.evaluate(board, move)

        then:
        result == Legality.LEGAL

        where:
        moveCoordinates << [C5, D5, E5, E4, E3, D3, C3, C4]
    }

    def "a king cannot move beyond one spot"() {
        given:
        def king = new Piece(WHITE, KING)
        def board = new BoardStateBuilder().addPieceAt(king, D4).build()
        def move = new Move(king.id(), moveCoordinates)

        when:
        def result = KING_RULE.evaluate(board, move)

        then:
        result == Legality.ILLEGAL

        where:
        moveCoordinates << [D6, D2, F6, F5, B4]
    }

}
