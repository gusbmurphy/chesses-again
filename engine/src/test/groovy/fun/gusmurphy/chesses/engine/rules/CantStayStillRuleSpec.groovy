package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType

class CantStayStillRuleSpec extends MoveRuleSpecification {

    static rule = new CantStayStillRule()

    def "the rule is relevant for all piece types"() {
        expect:
        rule.isRelevantForPieceType(type)

        where:
        type << PieceType.values()
    }

    def "a move to a pieces current position is illegal"() {
        given:
        def piece = new Piece(PlayerColor.WHITE, PieceType.PAWN)
        def piecePosition = Coordinates.D5
        def board = new BoardStateBuilder().addPieceAt(piece, piecePosition).build()

        expect:
        def result = rule.evaluate(board, new Move(piece.id(), piecePosition))
        evaluationIsIllegalWithNoEffects(result)
    }

    def "for any other move, we are unconcerned"() {
        given:
        def piece = new Piece(PlayerColor.WHITE, PieceType.PAWN)
        def piecePosition = Coordinates.D5
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.A7).build()

        expect:
        def result = rule.evaluate(board, new Move(piece.id(), piecePosition))
        evaluationIsLegal(result)
    }

}
