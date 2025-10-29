package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

class CantMoveThroughPiecesRuleSpec extends MoveRuleSpecification {

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
        def result = rule.evaluate(board, new Move(movingPiece.id(), moveCoordinates))
        evaluationIsIllegalWithNoEffects(result)

        where:
        blockingPosition | moveCoordinates
        E5               | E6
        F5               | G6
        F4               | G4
    }

    // TODO: Should this actually be "unconcerned"?
    def "the rule is not concerned with moves to an occupied spice"() {
        given:
        def movingPiece = new Piece()
        def occupyingPiece = new Piece()
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, E4)
            .addPieceAt(occupyingPiece, D6)
            .build()
        def move = new Move(movingPiece.id(), D6)

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsLegalWithNoEffects(result)
    }

    def "a move to a non-obstructed position is legal"() {
        given:
        def movingPiece = new Piece()
        def occupyingPiece = new Piece()
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, E4)
            .addPieceAt(occupyingPiece, D6)
            .build()
        def move = new Move(movingPiece.id(), moveCoordinates)

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsLegalWithNoEffects(result)

        where:
        moveCoordinates << [B2, H8, G4]
    }

}
