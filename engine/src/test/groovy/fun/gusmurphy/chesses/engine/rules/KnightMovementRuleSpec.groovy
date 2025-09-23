package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class KnightMovementRuleSpec extends Specification {

    private static final MoveLegalityRule KNIGHT_RULE = new KnightMovementRule()

    def "the knight can move in an L shape"() {
        given:
        def piece = new Piece(PlayerColor.BLACK, PieceType.KNIGHT)
        def board = new BoardStateBuilder().addPieceAt(piece, D5).build()

        expect:
        KNIGHT_RULE.evaluate(board, new Move(piece.id(), coordinates)) == MoveLegality.LEGAL

        where:
        coordinates << [C7, E7, F6, F4, E3, C3, B4, B6]
    }

}
