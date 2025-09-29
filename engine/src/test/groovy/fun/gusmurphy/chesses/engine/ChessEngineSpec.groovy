package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.Legality
import fun.gusmurphy.chesses.engine.rules.MoveRule
import spock.lang.Specification

class ChessEngineSpec extends Specification {

    private static final INITIAL_BOARD = new BoardStateBuilder().build()
    private static final DUMMY_MOVE = new Move(new PieceId(), Coordinates.A1)
    private final AppliesMoves moveApplicator = Mock(AppliesMoves)
    private final MoveRule moveRule = Mock(MoveRule)
    private RunsGame engine

    def setup() {
        engine = new ChessEngine(moveApplicator, moveRule, INITIAL_BOARD)
    }

    def "the engine asks its move rule for rule legality"() {
        when:
        def result = engine.checkLegalityOf(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(INITIAL_BOARD, DUMMY_MOVE) >> Legality.LEGAL
        result == Legality.LEGAL
    }

    def "when a move is made, the move applier is used if the move is legal"() {
        given:
        def newBoardState = new BoardStateBuilder()
            .addPieceAt(new Piece(PlayerColor.WHITE, PieceType.ROOK), Coordinates.A1)
            .build()

        when:
        engine.makeMove(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(INITIAL_BOARD, DUMMY_MOVE) >> Legality.LEGAL
        1 * moveApplicator.applyMoveToBoard(DUMMY_MOVE, INITIAL_BOARD) >> newBoardState

        and: "we can retrieve the new board state"
        engine.currentBoardState() == newBoardState
    }

    def "when a move is made, the move applier is NOT invoked if the move is NOT legal"() {
        when:
        engine.makeMove(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(INITIAL_BOARD, DUMMY_MOVE) >> Legality.ILLEGAL
        0 * moveApplicator.applyMoveToBoard(DUMMY_MOVE, INITIAL_BOARD)

        and: "the board state remains the same"
        engine.currentBoardState() == INITIAL_BOARD
    }

}
