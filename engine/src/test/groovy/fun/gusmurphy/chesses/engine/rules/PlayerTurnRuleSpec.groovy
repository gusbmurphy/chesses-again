package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

class PlayerTurnRuleSpec extends Specification {

    def "a move for a piece of the same color as the current turn is legal"() {
        given:
        Piece piece = new Piece(color)
        BoardState board = new BoardStateBuilder()
            .currentTurnColor(color)
            .addPieceAt(piece, Coordinates.A1)
            .build()

        MoveRule rule = new PlayerTurnRule()
        Move move = new Move(piece.id(), Coordinates.A1)

        expect:
        rule.evaluate(board, move) == Legality.LEGAL

        where:
        color << [PlayerColor.WHITE, PlayerColor.BLACK]
    }

    def "a move for a piece without same color as the current turn is illegal"() {
        given:
        Piece piece = new Piece(pieceColor)
        BoardState board = new BoardStateBuilder()
            .currentTurnColor(pieceColor.opposite())
            .addPieceAt(piece, Coordinates.A1)
            .build()

        MoveRule rule = new PlayerTurnRule()
        Move move = new Move(piece.id(), Coordinates.A1)

        expect:
        rule.evaluate(board, move) == Legality.ILLEGAL

        where:
        pieceColor << [PlayerColor.WHITE, PlayerColor.BLACK]
    }

}
