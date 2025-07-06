package fun.gusmurphy.chesses.engine.doubles

import fun.gusmurphy.chesses.engine.boardstate.BoardState
import fun.gusmurphy.chesses.engine.Move
import fun.gusmurphy.chesses.engine.rules.MoveLegalityRule
import fun.gusmurphy.chesses.engine.rules.MoveLegality

class LegalAlwaysRule implements MoveLegalityRule {

    @Override
    MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.LEGAL
    }

}
