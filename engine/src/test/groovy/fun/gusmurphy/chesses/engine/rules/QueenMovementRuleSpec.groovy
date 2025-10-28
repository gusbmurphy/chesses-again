package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static Legality.*

class QueenMovementRuleSpec extends MoveRuleSpecification {

    static queen = new Piece(PlayerColor.WHITE, QUEEN)
    static queenPosition = D5
    static board = new BoardStateBuilder().addPieceAt(queen, queenPosition).build()

    def "the rule is only concerned with queens"() {
        expect:
        new QueenMovementRule().isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | false
        QUEEN     | true
        PAWN      | false
    }

    def "queens can move to any spot in the same file or rank, or diagonally"() {
        expect:
        def result = new QueenMovementRule().evaluate(board, new Move(queen.id(), coordinates))
        evaluationIsLegal(result)

        where:
        coordinates << [coordinatesInSameRankOrFileAsQueen(), coordinatesDiagonalFromQueen()].flatten()
    }

    def "queens cannot move anywhere else"() {
        expect:
        def result = new QueenMovementRule().evaluate(board, new Move(queen.id(), coordinates))
        evaluationIsIllegal(result)

        where:
        coordinates << allIllegalCoordinates()
    }

    static List<Coordinates> coordinatesInSameRankOrFileAsQueen() {
        return Coordinates.values().findAll(
            c -> c.file() == queenPosition.file() || c.rank() == queenPosition.rank()
        )
    }

    static List<Coordinates> coordinatesDiagonalFromQueen() {
        return Coordinates.values().findAll(c -> c.isDiagonalFrom(queenPosition))
    }

    static List<Coordinates> allIllegalCoordinates() {
        def coordinates = Coordinates.values().toList()
        coordinates.removeAll(coordinatesInSameRankOrFileAsQueen())
        coordinates.removeAll(coordinatesDiagonalFromQueen())
        return coordinates
    }

}
