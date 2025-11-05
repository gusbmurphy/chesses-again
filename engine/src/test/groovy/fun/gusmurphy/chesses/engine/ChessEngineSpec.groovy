package fun.gusmurphy.chesses.engine

import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.ReducesBoardState
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.doubles.DummyBoardStateEvent
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceId
import fun.gusmurphy.chesses.engine.piece.PieceType
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation
import fun.gusmurphy.chesses.engine.rules.MoveRule
import spock.lang.Specification

class ChessEngineSpec extends Specification {

    private final MoveRule moveRule = Mock(MoveRule)
    private ReducesBoardState boardStateReducer = Mock(ReducesBoardState)
    private RunsGame engine

    private static final INITIAL_BOARD = new BoardStateBuilder().build()
    private static final DUMMY_MOVE = new Move(new PieceId(), Coordinates.A1)

    private static final FIRST_EVENT = new DummyBoardStateEvent()
    private static final SECOND_EVENT = new DummyBoardStateEvent()
    private static final FIRST_REDUCED_BOARD = new BoardStateBuilder().addPieceAt(
        new Piece(PlayerColor.WHITE, PieceType.ROOK), Coordinates.A1
    ).build()
    private static final SECOND_REDUCED_BOARD = new BoardStateBuilder().addPieceAt(
        new Piece(PlayerColor.WHITE, PieceType.ROOK), Coordinates.A3
    ).build()

    def setup() {
        engine = new ChessEngine(boardStateReducer, moveRule, INITIAL_BOARD)
    }

    def "the engine asks its move rule for rule legality"() {
        when:
        def isLegal = engine.moveIsLegal(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(INITIAL_BOARD, DUMMY_MOVE) >> RuleEvaluation.legalWithNoEffects()
        isLegal
    }

    def "when a legal move is made, its effects are applied to the board with the reducer"() {
        given:
        def evaluationWithEffects = RuleEvaluation.legalWithEffectsFromEvents(
            FIRST_EVENT,
            SECOND_EVENT
        )

        when:
        engine.makeMove(DUMMY_MOVE)

        then:
        1 * moveRule.evaluate(INITIAL_BOARD, DUMMY_MOVE) >> evaluationWithEffects
        1 * boardStateReducer.reduce(INITIAL_BOARD, FIRST_EVENT) >> FIRST_REDUCED_BOARD
        1 * boardStateReducer.reduce(FIRST_REDUCED_BOARD, SECOND_EVENT) >> SECOND_REDUCED_BOARD

        and:
        engine.currentBoardState() == SECOND_REDUCED_BOARD
    }

}
