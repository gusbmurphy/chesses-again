package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.PendingFeature

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*

class CastlingRuleSpec extends MoveRuleSpecification {

    private static MoveRule rule = new CastlingRule()

    def "castling moves the king and rook in one move"() {
        given:
        def (board, king) = setupBoard(color, kingPosition, rookPosition)
        def move = new Move(king.id(), moveCoordinates)

        expect:
        rule.evaluate(board, move).legality() == LEGAL

        where:
        color | kingPosition | rookPosition | moveCoordinates
        WHITE | E1           | A1           | C1
        WHITE | E1           | H1           | G1
        BLACK | E8           | A8           | C8
        BLACK | E8           | H8           | G8
    }

    def "the rule is unconcerned with basically any other move"() {
        given:
        def (board, king) = setupBoard(WHITE, E1, A1)
        def move = new Move(king.id(), moveCoordinates)

        expect:
        rule.evaluate(board, move).legality() == UNCONCERNED

        where:
        moveCoordinates << [G5, C3, E8]
    }

    def "the rule only applies to kings"() {
        expect:
        rule.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | true
        QUEEN     | false
        PAWN      | false
    }

    @PendingFeature
    def "regular king rules are overridden"() {
    }

    @PendingFeature
    def "castling cannot happen if the king has moved"() {
    }

    @PendingFeature
    def "castling cannot happen if the relevant rook has moved"() {
    }

    private static setupBoard(PlayerColor color, Coordinates kingPosition, Coordinates rookPosition) {
        def king = new Piece(color, KING)
        def rook = new Piece(color, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(rook, rookPosition)
            .build()

        return [board, king]
    }
}
