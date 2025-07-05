package fun.gusmurphy.chesses.engine;

public class PlayerTurnRule implements MoveEvaluationRule {

    @Override
    public MoveLegality evaluate(BoardState boardState, Move move) {
        return MoveLegality.LEGAL;
    }

}
