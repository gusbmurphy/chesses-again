package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class PawnMovementRuleSpec extends Specification {

    private static final MoveLegalityRule PAWN_RULE = new PawnMovementRule()

    def "the rule is only concerned with pawns"() {
        expect:
        PAWN_RULE.relevantPieceTypes().asList() == [PAWN]
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

    def "a pawn cannot move more than a single spot forward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates)) == MoveLegality.ILLEGAL

        where:
        color | moveCoordinates
        WHITE | D6
        BLACK | D2
    }

    def "a pawn cannot move horizontally"() {
        given:
        def pawn = new Piece(color as PlayerColor, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates as Coordinates)) == MoveLegality.ILLEGAL

        where:
        [color, moveCoordinates] << [[WHITE, BLACK], [C3, C4, C5, E4, E5, E6]].combinations()
    }

}
