package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Ignore
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.Coordinates.*

class CantMoveThroughPiecesRuleSpec extends Specification {

    static rule = new CantMoveThroughPiecesRule()

    def "the rule is for all pieces except knights"() {
        expect:
        rule.isRelevantForPieceType(type) == expected

        where:
        type   | expected
        KNIGHT | false
        QUEEN  | true
        PAWN   | true
        KING   | true
        BISHOP | true
        ROOK   | true
    }

    def "a move to the opposite side of another piece is illegal"() {
        given:
        def movingPiece = new Piece()
        def blockingPiece = new Piece()
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, E4)
            .addPieceAt(blockingPiece, blockingPosition)
            .build()

        expect:
        rule.evaluate(board, new Move(movingPiece.id(), moveCoordinates)) == Legality.ILLEGAL

        where:
        blockingPosition | moveCoordinates
        E5               | E6
        F5               | G6
        F4               | G4
    }

    // TODO: Seems like LEGAL would get us to the same place here, do we need UNCONCERNED at all?
    def "the rule is not concerned with moves to an occupied spice"() {
        given:
        def movingPiece = new Piece()
        def occupyingPiece = new Piece()
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, E4)
            .addPieceAt(occupyingPiece, D6)
            .build()

        expect:
        rule.evaluate(board, new Move(movingPiece.id(), D6)) == Legality.UNCONCERNED
    }

    @Ignore("Soon...")
    def "a move to a non-obstructed position is legal"() {
    }

}
