package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.rules.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.PlayerTurnRule
import spock.lang.Specification

class PlayerTurnRuleSpec extends Specification {

    def "a move for a piece of the same color as the current turn is legal"() {
        given:
        Piece piece = new Piece(color)
        BoardState board = new BoardStateBuilder()
            .currentTurnColor(color)
            .addPiece(piece)
            .build()

        MoveEvaluationRule rule = new PlayerTurnRule()
        Move move = new Move(piece.id())

        expect:
        rule.evaluate(board, move) == MoveLegality.LEGAL

        where:
        color << [PlayerColor.WHITE, PlayerColor.BLACK]
    }

}
