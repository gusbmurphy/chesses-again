package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.C2
import static fun.gusmurphy.chesses.engine.Coordinates.C3
import static fun.gusmurphy.chesses.engine.Coordinates.C4
import static fun.gusmurphy.chesses.engine.Coordinates.C5
import static fun.gusmurphy.chesses.engine.Coordinates.D3
import static fun.gusmurphy.chesses.engine.Coordinates.D4
import static fun.gusmurphy.chesses.engine.Coordinates.D5
import static fun.gusmurphy.chesses.engine.Coordinates.D6
import static fun.gusmurphy.chesses.engine.Coordinates.E4
import static fun.gusmurphy.chesses.engine.Coordinates.E5
import static fun.gusmurphy.chesses.engine.Coordinates.E6
import static fun.gusmurphy.chesses.engine.PlayerColor.BLACK
import static fun.gusmurphy.chesses.engine.PlayerColor.WHITE
import static fun.gusmurphy.chesses.engine.piece.PieceType.BISHOP
import static fun.gusmurphy.chesses.engine.piece.PieceType.KING
import static fun.gusmurphy.chesses.engine.piece.PieceType.KNIGHT
import static fun.gusmurphy.chesses.engine.piece.PieceType.PAWN
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

    def "a pawn can move a single spot forward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates)) == MoveLegality.LEGAL

        where:
        color | moveCoordinates
        WHITE | D5
        BLACK | D3
    }

    def "a pawn cannot move backward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates)) == MoveLegality.ILLEGAL

        where:
        color | moveCoordinates
        WHITE | D3
        BLACK | D5
    }

    def "a white pawn cannot move more than a single spot forward"() {
        given:
        def pawn = new Piece(WHITE, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), D6)) == MoveLegality.ILLEGAL
    }

    def "a white pawn cannot move horizontally"() {
        given:
        def pawn = new Piece(WHITE, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates)) == MoveLegality.ILLEGAL

        where:
        moveCoordinates << [C3, C4, C5, E4, E5, E6]
    }

}
