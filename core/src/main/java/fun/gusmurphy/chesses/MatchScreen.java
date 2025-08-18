package fun.gusmurphy.chesses;

import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class MatchScreen extends BaseScreen {

    public MatchScreen(final ChessesGame game) {
        super(game);
        BoardState initialBoardState = new BoardStateBuilder()
            .addPieceAt(new Piece(PlayerColor.BLACK, PieceType.ROOK), Coordinates.A1)
            .addPieceAt(new Piece(PlayerColor.WHITE, PieceType.BISHOP), Coordinates.C3)
            .height(3)
            .width(7)
            .build();
        BoardDrawable board = new BoardDrawable(game, initialBoardState);
        drawables.add(board);
        renderables.add(board);
    }

}
