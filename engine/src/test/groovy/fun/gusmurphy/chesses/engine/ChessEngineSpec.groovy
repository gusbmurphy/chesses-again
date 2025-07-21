package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.rules.MoveLegality
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule
import spock.lang.Specification

class ChessEngineSpec extends Specification {

    private static final BOARD = new BoardStateBuilder().build()
    private static final DUMMY_MOVE = new Move(new PieceId(), Coordinates.A1)
    private final AppliesMoves moveApplicator = Mock(AppliesMoves)
    private final MoveLegalityRule moveRule = Mock(MoveLegalityRule)
    private final ChessEngine engine = new ChessEngine(moveApplicator, moveRule, BOARD)

    def "the engine asks its move rule for rule legality"() {
        when:
        def result = engine.checkLegalityOf(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(BOARD, DUMMY_MOVE) >> MoveLegality.LEGAL
        result == MoveLegality.LEGAL
    }

    def "when a move is made, the move applier is used if the move is legal"() {
        when:
        engine.makeMove(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(BOARD, DUMMY_MOVE) >> MoveLegality.LEGAL
        1 * moveApplicator.applyMoveToBoard(DUMMY_MOVE, BOARD)
    }

}
