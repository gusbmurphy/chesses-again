package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.ChessEngine
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.BoardStateReducer
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

class EnPassantRuleSpec extends MoveRuleSpecification {

    def "a pawn can take another that has just taken it's double move by moving to the space behind it"() {
        given:
        def (takingPawn, takenPawn, engine) = setupEngineAndPawns(taking, taken)

        def takenPawnDoubleMove = new Move(takenPawn.id(), taken.move)
        def takingMove = new Move(takingPawn.id(), taking.move)

        when:
        engine.makeMove(takenPawnDoubleMove)
        engine.makeMove(takingMove)

        then:
        def resultingBoard = engine.currentBoardState()
        resultingBoard.pieceOnBoardForId(takenPawn.id()).isPresent() == false
        resultingBoard.pieceOnBoardForId(takingPawn.id()).orElseThrow().coordinates() == taking.move

        where:
        [taking, taken] << testPieceParameters()
    }

    def "a pawn still can't move diagonally if there's no piece to take"() {
        given:
        def (movingPawn, _, engine) = setupEngineAndPawns(taking, taken)
        def move = new Move(movingPawn.id(), taking.move)

        when: "we don't move the other pawn"
        engine.makeMove(move)

        then:
        def resultingBoard = engine.currentBoardState()
        resultingBoard.pieceOnBoardForId(movingPawn.id()).orElseThrow().coordinates() == taking.start

        where:
        [taking, taken] << testPieceParameters()
    }

    def "the en passant move can only happen on the very next turn"() {
        given:
        def doubleMovePawn = new Piece(BLACK, PAWN)
        def movingPawn = new Piece(WHITE, PAWN)
        def someOtherPawn = new Piece(BLACK, PAWN)

        def board = new BoardStateBuilder()
            .addPieceAt(doubleMovePawn, E7)
            .addPieceAt(someOtherPawn, H7)
            .addPieceAt(movingPawn, F4)
            .build()

        def engine = engineWithLimitedRules(board)

        when:
        engine.makeMove(new Move(doubleMovePawn.id(), E5))
        engine.makeMove(new Move(movingPawn.id(), F5))
        engine.makeMove(new Move(someOtherPawn.id(), H6))
        engine.makeMove(new Move(movingPawn.id(), E6))

        then: "the last en passant move should not have happened"
        def finalBoard = engine.currentBoardState()
        finalBoard.pieceOnBoardForId(doubleMovePawn.id()).isPresent()
        finalBoard.pieceOnBoardForId(movingPawn.id()).orElseThrow().coordinates() == F5
    }

    private static class PieceParameters {
        PlayerColor color
        Coordinates start
        Coordinates move


        @Override
        String toString() {
            return "PieceParameters{" +
                "color=" + color +
                ", start=" + start +
                ", move=" + move +
                '}';
        }
    }

    private static setupEngineAndPawns(PieceParameters taking, PieceParameters taken) {
        def takingPawn = new Piece(taking.color, PAWN)
        def takenPawn = new Piece(taken.color, PAWN)
        def board = new BoardStateBuilder()
            .addPieceAt(takingPawn, taking.start)
            .addPieceAt(takenPawn, taken.start)
            .build()

        def engine = engineWithLimitedRules(board)

        return [takingPawn, takenPawn, engine]
    }

    private static ChessEngine engineWithLimitedRules(BoardState board) {
        def rules = new MoveRuleSuite(new EnPassantRule(), new PawnMovementRule())
        def engine = new ChessEngine(new BoardStateReducer(), rules, board)
        return engine
    }

    private static testPieceParameters() {
        return [
            [new PieceParameters(color: WHITE, start: F5, move: E6), new PieceParameters(color: BLACK, start: E7, move: E5)],
            [new PieceParameters(color: WHITE, start: F5, move: G6), new PieceParameters(color: BLACK, start: G7, move: G5)],
            [new PieceParameters(color: BLACK, start: F4, move: E3), new PieceParameters(color: WHITE, start: E2, move: E4)],
            [new PieceParameters(color: BLACK, start: F4, move: G3), new PieceParameters(color: WHITE, start: G2, move: G4)]
        ]
    }

}
