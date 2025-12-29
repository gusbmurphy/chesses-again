package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.ChessEngine
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.boardstate.BoardStateReducer
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Ignore

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

class EnPassantRuleSpec extends MoveRuleSpecification {

    def "a pawn can take another that has just taken it's double move by moving to the space behind it"() {
        given:
        def takingPawn = new Piece(WHITE, PAWN)
        def takenPawn = new Piece(BLACK, PAWN)

        def board = new BoardStateBuilder()
            .addPieceAt(takingPawn, F5)
            .addPieceAt(takenPawn, E7)
            .build()

        def engine = new ChessEngine(new BoardStateReducer(), new EnPassantRule(), board)

        def takenPawnDoubleMove = new Move(takenPawn.id(), E5)
        def takingMove = new Move(takingPawn.id(), E6)

        when:
        engine.makeMove(takenPawnDoubleMove)
        engine.makeMove(takingMove)

        then:
        def resultingBoard = engine.currentBoardState()
        resultingBoard.pieceOnBoardForId(takenPawn.id()).isPresent() == false
        resultingBoard.pieceOnBoardForId(takingPawn.id()).orElseThrow().coordinates() == E6
    }

    @Ignore("To be implemented...")
    def "a pawn still can't move diagonally if there's no piece to take"() {
    }

}
