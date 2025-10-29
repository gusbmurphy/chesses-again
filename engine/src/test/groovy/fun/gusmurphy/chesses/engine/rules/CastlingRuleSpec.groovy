package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*

class CastlingRuleSpec extends MoveRuleSpecification {

    private static MoveRule rule = new CastlingRule()

    def "castling moves the king and rook in one move"() {
        given:
        def king = new Piece(color, KING)
        def rook = new Piece(color, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(rook, rookPosition)
            .build()
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

}
