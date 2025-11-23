package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.events.PieceRemovedEvent
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

class PieceTakingRuleSpec extends MoveRuleSpecification {

    def "moving a piece to a spot occupied by the opposite color removes that other piece"() {
        given:
        def movingPiece = new Piece(color)
        def takenPiece = new Piece(color.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(movingPiece, D4)
            .addPieceAt(takenPiece, D5)
            .build()

        MoveRule rule = new PieceTakingRule()
        def move = new Move(movingPiece.id(), D5)

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

}
