package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*

class CastlingRuleSpec extends MoveRuleSpecification {

    private static final MoveRule CASTLING_RULE = new CastlingRule()

    def "castling moves the king and rook in one move"() {
        given:
        def king = new Piece(WHITE, KING)
        def rook = new Piece(WHITE, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(king, E1)
            .addPieceAt(rook, A1)
            .build()
        def move = new Move(king.id(), E1)

        expect:
        CASTLING_RULE.evaluate(board, move).legality() == LEGAL
    }

}
