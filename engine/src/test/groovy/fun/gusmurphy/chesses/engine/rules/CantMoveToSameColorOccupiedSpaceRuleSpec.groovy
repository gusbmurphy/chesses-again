package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType

class CantMoveToSameColorOccupiedSpaceRuleSpec extends MoveRuleSpecification {

    static MoveRule rule = new CantMoveToSameColorOccupiedSpaceRule()

    def "the rule is relevant for all piece types"() {
        expect:
        rule.isRelevantForPieceType(type)

        where:
        type << PieceType.values()
    }

    def "a move to a space that's occupied by a piece of the same color is illegal"() {
        given:
        def movingPiece = new Piece(PlayerColor.WHITE)
        def occupyingPiece = new Piece(PlayerColor.WHITE)
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, Coordinates.A7)
            .addPieceAt(occupyingPiece, Coordinates.B8)
            .build()

        def move = new Move(movingPiece.id(), Coordinates.B8)

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(result)
    }

    def "we're unconcerned with a move to a space occupied by the other color"() {
        given:
        def movingPiece = new Piece(PlayerColor.WHITE)
        def occupyingPiece = new Piece(PlayerColor.BLACK)
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, Coordinates.A7)
            .addPieceAt(occupyingPiece, Coordinates.B8)
            .build()

        def move = new Move(movingPiece.id(), Coordinates.B8)

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsLegalAndHasSimpleMoveEffect(result, move)
    }

    def "we're unconcerned with a move to an unoccupied space"() {
        given:
        def movingPiece = new Piece(PlayerColor.WHITE)
        def occupyingPiece = new Piece(PlayerColor.WHITE)
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, Coordinates.A7)
            .addPieceAt(occupyingPiece, Coordinates.B8)
            .build()

        def move = new Move(movingPiece.id(), Coordinates.C3)

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsLegalAndHasSimpleMoveEffect(result, move)
    }

}
