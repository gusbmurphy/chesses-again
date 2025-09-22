package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.C2
import static fun.gusmurphy.chesses.engine.Coordinates.D4
import static fun.gusmurphy.chesses.engine.PlayerColor.WHITE
import static fun.gusmurphy.chesses.engine.piece.PieceType.BISHOP
import static fun.gusmurphy.chesses.engine.piece.PieceType.KING
import static fun.gusmurphy.chesses.engine.piece.PieceType.KNIGHT
import static fun.gusmurphy.chesses.engine.piece.PieceType.QUEEN
import static fun.gusmurphy.chesses.engine.piece.PieceType.ROOK

class PawnMovementRuleSpec extends Specification {

    private static final MoveLegalityRule PAWN_RULE = new PawnMovementRule()

    def "the pawn movement rule is not concerned with a non-pawn piece"() {
        given:
        def nonPawn = new Piece(WHITE, pieceType as PieceType)
        def board = new BoardStateBuilder().addPieceAt(nonPawn, D4).build()
        def move = new Move(nonPawn.id(), C2)

        when:
        def result = PAWN_RULE.evaluate(board, move)

        then:
        result == MoveLegality.UNCONCERNED

        where:
        pieceType << [ROOK, BISHOP, KING, QUEEN, KNIGHT]
    }
}
