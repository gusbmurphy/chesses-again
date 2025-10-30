package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.events.PieceMovedEvent
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.rules.RuleEvaluation.Legality.*

class CastlingRuleSpec extends MoveRuleSpecification {

    private static MoveRule rule = new CastlingRule()

    def "castling moves the king and rook in one move"() {
        given:
        def (
            color,
            kingPosition,
            rookPosition,
            moveCoordinates,
            finalRookPosition
        ) = colorAndPositions
        def (board, king, rook) = setupBoard(color, kingPosition, rookPosition)
        def move = new Move(king.id(), moveCoordinates)

        expect:
        def evaluation = rule.evaluate(board, move)
        evaluation.legality() == LEGAL

        and:
        def effects = evaluation.effects()
        def firstEffect = effects.next()
        (firstEffect as PieceMovedEvent).pieceId() == king.id()
        (firstEffect as PieceMovedEvent).newCoordinates() == moveCoordinates

        def secondEffect = effects.next()
        (secondEffect as PieceMovedEvent).pieceId() == rook.id()
        (secondEffect as PieceMovedEvent).newCoordinates() == finalRookPosition

        where:
        colorAndPositions << legalCastlingMoves()
    }

    def "castling cannot happen if the relevant rook does not exist"() {
        given:
        def (color, kingPosition, _, moveCoordinates) = colorAndPositions
        def king = new Piece(color, KING)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .build()

        def move = new Move(king.id(), moveCoordinates)

        expect:
        def evaluation = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(evaluation)

        where:
        colorAndPositions << legalCastlingMoves()
    }

    def "castling cannot happen if there is a non-rook at the relevant position"() {
        given:
        def (color, kingPosition, rookPosition, moveCoordinates) = colorAndPositions
        def king = new Piece(color, KING)
        def nonRook = new Piece(color, PAWN)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(nonRook, rookPosition)
            .build()

        def move = new Move(king.id(), moveCoordinates)

        expect:
        def evaluation = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(evaluation)

        where:
        colorAndPositions << legalCastlingMoves()
    }

    def "the rule is unconcerned with basically any other move"() {
        given:
        def (board, king) = setupBoard(WHITE, E1, A1)
        def move = new Move(king.id(), moveCoordinates)

        expect:
        rule.evaluate(board, move).legality() == UNCONCERNED

        where:
        moveCoordinates << [G5, C3, E8]
    }

    def "the rule only applies to kings"() {
        expect:
        rule.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | true
        QUEEN     | false
        PAWN      | false
    }

    def "regular king rules are overridden"() {
        expect:
        rule.overrides(new KingMovementRule())
    }

    def "castling cannot happen if the king has moved"() {
        given:
        def (color, kingPosition, rookPosition, moveCoordinates) = colorAndPositions
        def king = new Piece(color, KING).afterMove()
        def rook = new Piece(color, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(rook, rookPosition)
            .build()
        def move = new Move(king.id(), moveCoordinates)

        expect:
        evaluationIsIllegalWithNoEffects(rule.evaluate(board, move))

        where:
        colorAndPositions << legalCastlingMoves()
    }

    def "castling cannot happen if the relevant rook has moved"() {
        given:
        def (color, kingPosition, rookPosition, moveCoordinates) = colorAndPositions
        def king = new Piece(color, KING)
        def rook = new Piece(color, ROOK).afterMove()
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(rook, rookPosition)
            .build()
        def move = new Move(king.id(), moveCoordinates)

        expect:
        evaluationIsIllegalWithNoEffects(rule.evaluate(board, move))

        where:
        colorAndPositions << legalCastlingMoves()
    }

    /**
     * @return an array of the board, king, and rook
     */
    private static setupBoard(PlayerColor color, Coordinates kingPosition, Coordinates rookPosition) {
        def king = new Piece(color, KING)
        def rook = new Piece(color, ROOK)
        def board = new BoardStateBuilder()
            .addPieceAt(king, kingPosition)
            .addPieceAt(rook, rookPosition)
            .build()

        return [board, king, rook]
    }

    /**
     * @return an array of the color, starting king position, starting rook position,
     * move coordinates, and the final rook position
     */
    private static legalCastlingMoves() {
        [
            [WHITE, E1, A1, C1, D1],
            [WHITE, E1, H1, G1, F1],
            [BLACK, E8, A8, C8, D8],
            [BLACK, E8, H8, G8, F8]
        ]
    }
}
