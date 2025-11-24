package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.piece.Piece

import static fun.gusmurphy.chesses.engine.piece.PieceType.*
import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*
import static fun.gusmurphy.chesses.engine.PlayerColor.*

class PawnTakingRuleSpec extends MoveRuleSpecification {

    static rule = new PawnTakingRule()

    def "the rule overrides the pawn movement rule"() {
        expect:
        rule.overrides(new PawnMovementRule())
    }

    def "the rule is relevant for pawns"() {
        expect:
        rule.isRelevantForPieceType(PAWN)
    }

    def "the rule is not relevant for non-pawns"() {
        expect:
        rule.isRelevantForPieceType(pieceType) == false

        where:
        pieceType << [KING, KNIGHT, QUEEN, BISHOP, ROOK]
    }

    def "a pawn cannot take a piece right in front of it"() {
        given:
        def pawn = new Piece(movingColor, PAWN)
        def otherPiece = new Piece(movingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .addPieceAt(otherPiece, moveCoordinates)
            .build()
        def move = board.enhanceMove(new Move(pawn.id(), moveCoordinates))

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(result)

        where:
        movingColor | moveCoordinates
        WHITE       | E7
        BLACK       | E5
    }

    def "a pawn can take a piece one spot diagonally from it"() {
        given:
        def pawn = new Piece(movingColor, PAWN)
        def otherPiece = new Piece(movingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .addPieceAt(otherPiece, moveCoordinates)
            .build()
        def move = board.enhanceMove(new Move(pawn.id(), moveCoordinates))

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsLegalAndHasSimpleMoveEffect(result, move)

        where:
        movingColor | moveCoordinates
        WHITE       | D7
        WHITE       | F7
        BLACK       | D5
        BLACK       | F5
    }

    def "a pawn can NOT take a piece one spot diagonally from it in the wrong direction"() {
        given:
        def pawn = new Piece(movingColor, PAWN)
        def otherPiece = new Piece(movingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .addPieceAt(otherPiece, moveCoordinates)
            .build()
        def move = board.enhanceMove(new Move(pawn.id(), moveCoordinates))

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(result)

        where:
        movingColor | moveCoordinates
        WHITE       | D5
        WHITE       | F5
        BLACK       | D7
        BLACK       | F7
    }

    def "a pawn can NOT take a piece more than one spot diagonally from it"() {
        given:
        def pawn = new Piece(movingColor, PAWN)
        def otherPiece = new Piece(movingColor.opposite())
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .addPieceAt(otherPiece, moveCoordinates)
            .build()
        def move = board.enhanceMove(new Move(pawn.id(), moveCoordinates))

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsIllegalWithNoEffects(result)

        where:
        movingColor | moveCoordinates
        WHITE       | G8
        WHITE       | C8
        BLACK       | C4
        BLACK       | G4
    }

    def "the rule doesn't care about non-taking moves"() {
        given:
        def pawn = new Piece(WHITE, PAWN)
        def board = new BoardStateBuilder()
            .addPieceAt(pawn, E6)
            .build()
        def move = board.enhanceMove(new Move(pawn.id(), moveCoordinates))

        expect:
        def result = rule.evaluate(board, move)
        evaluationIsUnconcernedWithNoEffects(result)

        where:
        moveCoordinates << [E5, H2, B3] // Just some random moves...
    }

}
