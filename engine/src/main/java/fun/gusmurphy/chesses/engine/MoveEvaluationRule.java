package fun.gusmurphy.chesses.engine;

public interface MoveEvaluationRule {

    MoveLegality evaluate(BoardState boardState, Move move);

}
