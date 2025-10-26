package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.rules.Legality.*

class PawnTakingRuleSpec extends Specification {

    static rule = new PawnTakingRule()

    def "the rule overrides the pawn movement rule"() {
        expect:
        rule.overrides(new PawnMovementRule())
    }

    def "the rule is relevant for pawns"() {
        expect:
        rule.isRelevantForPieceType(PAWN)
    }

    def "the rule is not relevant for non-pawns"() {
        expect:
        rule.isRelevantForPieceType(pieceType) == false

        where:
        pieceType << [KING, KNIGHT, QUEEN, BISHOP, ROOK]
    }

    def "a pawn cannot take a piece right in front of it"() {
        given:
        def pawn = new Piece(movingColor, PAWN)
        def otherPiece = new Piece(movingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .addPieceAt(otherPiece, moveCoordinates)
            .build()
        def move = new Move(pawn.id(), moveCoordinates)

        expect:
        rule.evaluate(board, move) == ILLEGAL

        where:
        movingColor | moveCoordinates
        WHITE       | E7
        BLACK       | E5
    }

}
