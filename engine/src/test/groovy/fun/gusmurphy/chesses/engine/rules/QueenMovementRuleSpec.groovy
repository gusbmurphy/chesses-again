package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Coordinates
import fun.gusmurphy.chesses.engine.File
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.Rank
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece
import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.Coordinates.*
import static fun.gusmurphy.chesses.engine.rules.MoveLegality.*

class QueenMovementRuleSpec extends Specification {

    static queen = new Piece(PlayerColor.WHITE, QUEEN)
    static position = D5
    static board = new BoardStateBuilder().addPieceAt(queen, position).build()

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

    def "queens can move to any spot in the same file or rank"() {
        expect:
        new QueenMovementRule().evaluate(board, new Move(queen.id(), coordinates)) == LEGAL

        where:
        coordinates << coordinatesInSameRankOrFileAsQueen()
    }

    static List<Coordinates> coordinatesInSameRankOrFileAsQueen() {
        return Coordinates.values().findAll(c -> c.file() == File.D || c.rank() == Rank.FIVE)
    }

}
