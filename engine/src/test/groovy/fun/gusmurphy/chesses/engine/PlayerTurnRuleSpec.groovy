package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.rules.MoveEvaluationRule
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.PlayerTurnRule
import spock.lang.Specification

class PlayerTurnRuleSpec extends Specification {

    def "the player for the current turn is able to make a move for a piece of their color"() {
        given:
        Piece piece = new Piece(PlayerColor.WHITE)
        BoardState board = new BoardStateBuilder()
            .currentTurnColor(PlayerColor.WHITE)
            .addPiece(piece)
            .build()

        MoveEvaluationRule rule = new PlayerTurnRule()
        Move move = new Move(piece.id())

        expect:
        rule.evaluate(board, move) == MoveLegality.LEGAL
    }

}
