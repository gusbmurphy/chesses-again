package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

class PieceTakingRuleSpec extends MoveRuleSpecification {

    def rule = new PieceTakingRule()

    def "moving a piece to a spot occupied by the opposite color removes that other piece"() {
        given:
        def (board, move, takenPiece) = setupBoardAndMoveWithOppositeColors(color)

        when:
        def result = rule.evaluate(board, move)

        then:
        result.isLegal()

        and:
        def effect = result.effects().next().get() as PieceRemovedEvent
        effect.pieceId() == takenPiece.id()

        where:
        color << [WHITE, BLACK]
    }

    def "moving a piece to a spot occupied by the SAME color is illegal"() {
        given:
        def (board, move) = setupBoardAndMoveWithSameColor(color)

        when:
        def result = rule.evaluate(board, move)

        then:
        result.isIllegal()

        where:
        color << [WHITE, BLACK]
    }

    private static setupBoardAndMoveWithOppositeColors(PlayerColor takingColor) {
        def movingPiece = new Piece(takingColor)
        def takenPiece = new Piece(takingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, D4)
            .addPieceAt(takenPiece, D5)
            .build()

        def move = new Move(movingPiece.id(), D5)

        return [board, move, takenPiece]
    }

    private static setupBoardAndMoveWithSameColor(PlayerColor color) {
        def movingPiece = new Piece(color)
        def takenPiece = new Piece(color)
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, D4)
            .addPieceAt(takenPiece, D5)
            .build()

        def move = new Move(movingPiece.id(), D5)

        return [board, move]
    }

}
