package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.WHITE
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class KingMovementRuleSpec extends Specification {

    private static final MoveLegalityRule KING_RULE = new KingMovementRule()
    private static final L_COORDINATES = [C7, E7, F6, F4, E3, C3, B4, B6]

    def "the rule is not concerned with a non-king piece"() {
        given:
        def nonKing = new Piece(WHITE, pieceType as PieceType)
        def board = new BoardStateBuilder().addPieceAt(nonKing, D4).build()
        def move = new Move(nonKing.id(), C2)

        when:
        def result = KING_RULE.evaluate(board, move)

        then:
        result == MoveLegality.UNCONCERNED

        where:
        pieceType << [ROOK, BISHOP, KNIGHT, QUEEN, PAWN]
    }

}
