package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class KnightMovementRuleSpec extends Specification {

    private static final MoveLegalityRule KNIGHT_RULE = new KnightMovementRule()
    private static final KNIGHT_POSITION = D5
    private static final L_COORDINATES = [C7, E7, F6, F4, E3, C3, B4, B6]

    def "the movement rule is not concerned with a non-knight piece"() {
        given:
        def nonKnight = new Piece(WHITE, pieceType as PieceType)
        def board = new BoardStateBuilder().addPieceAt(nonKnight, D4).build()
        def move = new Move(nonKnight.id(), C2)

        when:
        def result = KNIGHT_RULE.evaluate(board, move)

        then:
        result == MoveLegality.UNCONCERNED

        where:
        pieceType << [ROOK, BISHOP, KING, QUEEN, PAWN]
    }

    def "the knight can move in an L shape"() {
        given:
        def piece = new Piece(BLACK, KNIGHT)
        def board = new BoardStateBuilder().addPieceAt(piece, KNIGHT_POSITION).build()

        expect:
        KNIGHT_RULE.evaluate(board, new Move(piece.id(), coordinates)) == MoveLegality.LEGAL

        where:
        coordinates << L_COORDINATES
    }

    def "the knight can NOT move outside of that L shape"() {
        given:
        def piece = new Piece(BLACK, KNIGHT)
        def board = new BoardStateBuilder().addPieceAt(piece, KNIGHT_POSITION).build()

        expect:
        KNIGHT_RULE.evaluate(board, new Move(piece.id(), coordinates as Coordinates)) == MoveLegality.ILLEGAL

        where:
        coordinates << Arrays.asList(Coordinates.values()) - L_COORDINATES
    }

}
