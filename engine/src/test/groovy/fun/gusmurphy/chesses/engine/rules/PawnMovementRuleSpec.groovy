package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*
import static fun.gusmurphy.chesses.engine.piece.PieceType.*

class PawnMovementRuleSpec extends MoveRuleSpecification {

    private static final MoveRule PAWN_RULE = new PawnMovementRule()

    def "the rule is only concerned with pawns"() {
        expect:
        PAWN_RULE.isRelevantForPieceType(pieceType) == expected

        where:
        pieceType | expected
        BISHOP    | false
        ROOK      | false
        KNIGHT    | false
        KING      | false
        QUEEN     | false
        PAWN      | true
    }

    def "a pawn can move a single spot forward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()
        def move = new Move(pawn.id(), moveCoordinates)

        expect:
        def result = PAWN_RULE.evaluate(board, move)
        evaluationIsLegalAndHasSimpleMoveEffect(result, move)

        where:
        color | moveCoordinates
        WHITE | D5
        BLACK | D3
    }

    def "on it's first move, a pawn can move two spots forward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()
        def move = new Move(pawn.id(), moveCoordinates)

        expect:
        def result = PAWN_RULE.evaluate(board, move)
        evaluationIsLegalAndHasSimpleMoveEffect(result, move)

        where:
        color | moveCoordinates
        WHITE | D6
        BLACK | D2
    }

    def "a pawn cannot move backward"() {
        given:
        def pawn = new Piece(color, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        def result = PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates))
        evaluationIsIllegalWithNoEffects(result)

        where:
        color | moveCoordinates
        WHITE | D3
        BLACK | D5
    }

    def "a pawn cannot move more than a single spot if it has already moved"() {
        given:
        def pawn = new Piece(color, PAWN).afterMove()
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        def result = PAWN_RULE.evaluate(board, new Move(pawn.id(), move))
        evaluationIsIllegalWithNoEffects(result)

        where:
        color | move
        WHITE | D6
        BLACK | D2
    }

    def "a pawn cannot move horizontally"() {
        given:
        def pawn = new Piece(color as PlayerColor, PAWN)
        def board = new BoardStateBuilder().addPieceAt(pawn, D4).build()

        expect:
        def result = PAWN_RULE.evaluate(board, new Move(pawn.id(), moveCoordinates as Coordinates))
        evaluationIsIllegalWithNoEffects(result)

        where:
        [color, moveCoordinates] << [[WHITE, BLACK], [C3, C4, C5, E4, E5, E6]].combinations()
    }

    def "a pawn cannot move straight ahead to a space occupied by another piece"() {
        given:
        def pawn = new Piece(WHITE, PAWN)
        def otherPiece = new Piece()
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, D4)
            .addPieceAt(otherPiece, D5)
            .build()
        def move = new Move(pawn.id(), D5)

        expect:
        def result = PAWN_RULE.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(result)
    }

}
