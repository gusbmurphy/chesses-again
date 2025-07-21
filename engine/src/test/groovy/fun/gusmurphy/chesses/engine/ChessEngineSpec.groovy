package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule
import spock.lang.Specification

class ChessEngineSpec extends Specification {

    def "the engine asks its move rule for rule legality"() {
        given:
        def moveRule = Mock(MoveLegalityRule)
        def board = new BoardStateBuilder().build()
        def engine = new ChessEngine(moveRule, board)
        def move = new Move(new PieceId(), Coordinates.A1)

        when:
        def result = engine.checkLegalityOf(move)

        then:
        1 * moveRule.evaluate(board, move) >> MoveLegality.LEGAL
        result == MoveLegality.LEGAL
    }

}
