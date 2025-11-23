package fun.gusmurphy.chesses.engine

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.coordinates.Coordinates.*

/**
 * Scholar's mate is sometimes referred to as the four-move checkmate,
 * although there are other ways for checkmate to occur in four moves.
 * It is used almost exclusively by beginners. Defending against it
 * is very simple, and if it is parried, the attacker's position
 * usually worsens.
 *
 * From Wikipedia: https://en.wikipedia.org/wiki/Scholar%27s_mate
 */
class ScholarsMateSpec extends Specification {

    static engine = ChessEngine.defaultEngine()
    static whitePawn = engine.currentBoardState().pieceAtCoordinates(E2).get()
    static blackPawn = engine.currentBoardState().pieceAtCoordinates(E7).get()
    static whiteQueen = engine.currentBoardState().pieceAtCoordinates(D1).get()
    static blackLeftKnight = engine.currentBoardState().pieceAtCoordinates(B8).get()
    static whiteBishop = engine.currentBoardState().pieceAtCoordinates(F1).get()
    static blackRightKnight = engine.currentBoardState().pieceAtCoordinates(G8).get()

    def "Scholar's Mate"() {
        given:
        // e4 e5
        engine.makeMove(new Move(whitePawn.id(), E4))
        engine.makeMove(new Move(blackPawn.id(), E5))
        // Qh5 Nc6
        engine.makeMove(new Move(whiteQueen.id(), H5))
        engine.makeMove(new Move(blackLeftKnight.id(), C6))
        // Bc4 Nf6??
        engine.makeMove(new Move(whiteBishop.id(), C4))
        engine.makeMove(new Move(blackRightKnight.id(), F6))
        // Qxf7#
        engine.makeMove(new Move(whiteQueen.id(), F7))

        expect:
        def board = engine.currentBoardState()
        board.pieceAtCoordinates(C4).get().id() == whiteBishop.id()
        board.pieceAtCoordinates(C6).get().id() == blackLeftKnight.id()
        board.pieceAtCoordinates(E5).get().id() == blackPawn.id()
        board.pieceAtCoordinates(E4).get().id() == whitePawn.id()
        board.pieceAtCoordinates(F6).get().id() == blackRightKnight.id()
        board.pieceAtCoordinates(F7).get().id() == whiteQueen.id()
    }

}
