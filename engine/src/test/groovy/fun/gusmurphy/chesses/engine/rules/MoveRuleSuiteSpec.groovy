package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.doubles.IllegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.LegalAlwaysWithNoEffectsRule
import fun.gusmurphy.chesses.engine.doubles.UnconcernedAlwaysRule
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType

class MoveRuleSuiteSpec extends MoveRuleSpecification {

    private static final TEST_PIECE = new Piece(PlayerColor.WHITE, PieceType.KING)
    private static final DUMMY_BOARD = new BoardStateBuilder().addPieceAt(TEST_PIECE, Coordinates.A7).build()
    private static final DUMMY_MOVE = new Move(TEST_PIECE.id(), Coordinates.A1)
    private static final MoveRule ALWAYS_LEGAL_RULE = new LegalAlwaysWithNoEffectsRule()
    private static final MoveRule ALWAYS_ILLEGAL_RULE = new IllegalAlwaysRule()

    def "with no rules, a move is legal"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite()

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsLegal(result)
    }

    def "with just one legal rule, a move is always legal"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite(new LegalAlwaysWithNoEffectsRule())

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsLegal(result)
    }

    def "with just one illegal rule, a move is always illegal"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite(new IllegalAlwaysRule())

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsIllegalWithNoEffects(result)
    }

    def "with two move evaluation rules, a move is legal if both allow it"() {
        given:
        def ruleOne = new LegalAlwaysWithNoEffectsRule()
        def ruleTwo = new LegalAlwaysWithNoEffectsRule()
        MoveRuleSuite ruleSuite = new MoveRuleSuite(ruleOne, ruleTwo)

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsLegal(result)
    }

    def "with two move evaluation rules, a move is illegal if one does not allow it"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite(ALWAYS_LEGAL_RULE, ALWAYS_ILLEGAL_RULE)

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsIllegalWithNoEffects(result)
    }

    def "only moves relevant for a the move's piece type are used"() {
        given:
        def pieceType = PieceType.PAWN
        def notThePieceType = PieceType.BISHOP
        def piece = new Piece(PlayerColor.WHITE, pieceType)
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        and: "a rule that's legal for the piece, and one that's illegal but not concerned with the piece"
        def legalRule = new LegalAlwaysWithNoEffectsRule(pieceType)
        def illegalRule = new IllegalAlwaysRule(notThePieceType)
        def suite = new MoveRuleSuite(legalRule, illegalRule)

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsLegal(result)
    }

    def "if the second rule says something is illegal, but the first is not relevant to the piece, the move is still illegal"() {
        given:
        def pieceType = PieceType.PAWN
        def notThePieceType = PieceType.BISHOP
        def piece = new Piece(PlayerColor.WHITE, pieceType)
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        and: "a rule that's legal for the piece, and one that's illegal but not concerned with the piece"
        def legalRule = new LegalAlwaysWithNoEffectsRule(notThePieceType)
        def illegalRule = new IllegalAlwaysRule(pieceType)
        def suite = new MoveRuleSuite(legalRule, illegalRule)

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsIllegalWithNoEffects(result)
    }

    def "if a legal rule is overridden by an illegal ruling, we use the illegal ruling"() {
        given:
        def overriddenRule = new LegalAlwaysWithNoEffectsRule()
        def overridingRule = new OverridingRule(new IllegalAlwaysRule(), overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsIllegalWithNoEffects(result)
    }

    def "if an illegal rule is overridden by a legal ruling, we use the legal ruling"() {
        given:
        def overriddenRule = new IllegalAlwaysRule()
        def overridingRule = new OverridingRule(new LegalAlwaysWithNoEffectsRule(), overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsLegal(result)
    }

    def "if a legal rule is overridden by an unconcerned ruling, we use the legal ruling"() {
        given:
        def overriddenRule = new LegalAlwaysWithNoEffectsRule()
        def overridingRule = new OverridingRule(new UnconcernedAlwaysRule(), overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsLegal(result)
    }

    def "if an illegal rule is overridden by a legal ruling, we use the legal ruling"() {
        given:
        def overriddenRule = new IllegalAlwaysRule()
        def overridingRule = new OverridingRule(new UnconcernedAlwaysRule(), overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsIllegalWithNoEffects(result)
    }
}
