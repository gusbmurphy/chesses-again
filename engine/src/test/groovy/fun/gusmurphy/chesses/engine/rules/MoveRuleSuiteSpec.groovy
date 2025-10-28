package fun.gusmurphy.chesses.engine.rules

import fun.gusmurphy.chesses.engine.coordinates.Coordinates
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.PlayerColor
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder
import fun.gusmurphy.chesses.engine.doubles.IllegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.LegalAlwaysRule
import fun.gusmurphy.chesses.engine.doubles.UnconcernedAlwaysRule
import fun.gusmurphy.chesses.engine.piece.Piece
import fun.gusmurphy.chesses.engine.piece.PieceType

class MoveRuleSuiteSpec extends MoveRuleSpecification {

    private static final TEST_PIECE = new Piece(PlayerColor.WHITE, PieceType.KING)
    private static final DUMMY_BOARD = new BoardStateBuilder().addPieceAt(TEST_PIECE, Coordinates.A7).build()
    private static final DUMMY_MOVE = new Move(TEST_PIECE.id(), Coordinates.A1)
    private static final MoveRule ALWAYS_LEGAL_RULE = new LegalAlwaysRule()
    private static final MoveRule ALWAYS_ILLEGAL_RULE = new IllegalAlwaysRule()

    def "with no rules, a move is legal"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite()

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        evaluationIsLegal(result)
    }

    def "with just one move evaluation rule, a move is legal or illegal based on just that one"() {
        given:
        MoveRuleSuite ruleSuite = new MoveRuleSuite(rule)

        expect:
        def result = ruleSuite.evaluate(DUMMY_BOARD, DUMMY_MOVE)
        assertion(result)

        where:
        rule                    | assertion
        new IllegalAlwaysRule() | { evaluationIsIllegal(it) }
        new LegalAlwaysRule()   | { evaluationIsLegal(it) }
    }

    def "with two move evaluation rules, a move is legal if both allow it"() {
        given:
        def ruleOne = new LegalAlwaysRule()
        def ruleTwo = new LegalAlwaysRule()
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
        evaluationIsIllegal(result)
    }

    def "only moves relevant for a the move's piece type are used"() {
        given:
        def pieceType = PieceType.PAWN
        def notThePieceType = PieceType.BISHOP
        def piece = new Piece(PlayerColor.WHITE, pieceType)
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        and: "a rule that's legal for the piece, and one that's illegal but not concerned with the piece"
        def legalRule = new LegalAlwaysRule(pieceType)
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
        def legalRule = new LegalAlwaysRule(notThePieceType)
        def illegalRule = new IllegalAlwaysRule(pieceType)
        def suite = new MoveRuleSuite(legalRule, illegalRule)

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        evaluationIsIllegal(result)
    }

    def "if a rule is overridden by another, we use an illegal or legal ruling from the override"() {
        given:
        def overridingRule = new OverridingRule(baseOverridingRule, overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        assertion(result)

        where:
        overriddenRule          | baseOverridingRule      || assertion
        new LegalAlwaysRule()   | new IllegalAlwaysRule() || { evaluationIsIllegal(it) }
        new IllegalAlwaysRule() | new LegalAlwaysRule()   || { evaluationIsLegal(it) }
    }

    def "if a rule is overridden by another that is unconcerned, we still use that base ruling"() {
        given:
        def overridingRule = new OverridingRule(new UnconcernedAlwaysRule(), overriddenRule)
        def suite = new MoveRuleSuite(overriddenRule, overridingRule)
        def piece = new Piece()
        def board = new BoardStateBuilder().addPieceAt(piece, Coordinates.E6).build()

        expect:
        def result = suite.evaluate(board, new Move(piece.id(), Coordinates.E4))
        assertion(result)

        where:
        overriddenRule          | assertion
        new LegalAlwaysRule()   | { evaluationIsLegal(it) }
        new IllegalAlwaysRule() | { evaluationIsIllegal(it) }
    }
}
