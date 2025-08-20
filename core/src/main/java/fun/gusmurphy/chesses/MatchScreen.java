package fun.gusmurphy.chesses;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import fun.gusmurphy.chesses.piece.PieceDrawable;

import java.util.ArrayList;
import java.util.List;

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

        List<PieceDrawable> pieces = new ArrayList<>();
        BoardCoordinateStates coordinateStates = initialBoardState.allCoordinateStates();
        // TODO: This is looking ugly...
        for (Coordinates c : Coordinates.values()) {
            coordinateStates.forCoordinates(c).flatMap(BoardCoordinateState::piece).ifPresent(piece -> {
                Vector2 piecePosition = board.getScreenPositionForCenterOf(c);
                PieceDrawable pieceDrawable = new PieceDrawable(piece, game.getSpriteBatch(), piecePosition);
                pieces.add(pieceDrawable);
            });
        }

        drawables.add(board);
        drawables.addAll(pieces);
    }

}
