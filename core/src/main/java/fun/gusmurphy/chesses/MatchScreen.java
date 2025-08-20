package fun.gusmurphy.chesses;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import fun.gusmurphy.chesses.piece.PieceDrawable;
import fun.gusmurphy.chesses.piece.PieceSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class MatchScreen extends BaseScreen implements PieceSelectionListener {

    private PieceId selectedPieceId;
    private List<PieceDrawable> pieceDrawables;

    public MatchScreen(final ChessesGame game) {
        super(game);
        BoardState initialBoardState = new BoardStateBuilder()
            .addPieceAt(new Piece(PlayerColor.BLACK, PieceType.ROOK), Coordinates.A1)
            .addPieceAt(new Piece(PlayerColor.WHITE, PieceType.BISHOP), Coordinates.C3)
            .height(3)
            .width(7)
            .build();
        BoardDrawable board = new BoardDrawable(game, initialBoardState);

        List<PieceDrawable> pieceDrawables = new ArrayList<>();
        BoardCoordinateStates coordinateStates = initialBoardState.allCoordinateStates();
        // TODO: This is looking ugly...
        for (Coordinates c : Coordinates.values()) {
            coordinateStates.forCoordinates(c).flatMap(BoardCoordinateState::piece).ifPresent(piece -> {
                Vector2 piecePosition = board.getScreenPositionForCenterOf(c);
                PieceDrawable pieceDrawable = new PieceDrawable(piece, game.getSpriteBatch(), piecePosition);
                pieceDrawable.listenToSelection(this);
                pieceDrawables.add(pieceDrawable);
            });
        }

        this.pieceDrawables = pieceDrawables;
        drawables.add(board);
        drawables.addAll(pieceDrawables);
        inputProcessors.addAll(pieceDrawables);
    }

    @Override
    public void onPieceSelected(PieceId pieceId) {
        if (selectedPieceId == null) {
            updateSelectedPiece(pieceId);
        }
    }

    @Override
    public void onPieceReleased(PieceId pieceId, Vector2 screenPosition) {
        if (selectedPieceId == pieceId) {
            unselectPiece(pieceId);
        }
    }

    private void updateSelectedPiece(PieceId pieceId) {
        selectedPieceId = pieceId;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(true);
    }

    private void unselectPiece(PieceId pieceId) {
        selectedPieceId = null;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(false);
    }

}
