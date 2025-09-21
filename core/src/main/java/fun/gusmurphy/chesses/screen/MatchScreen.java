package fun.gusmurphy.chesses.screen;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.ChessesGame;
import fun.gusmurphy.chesses.board.BoardDrawable;
import fun.gusmurphy.chesses.engine.ChessEngine;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.piece.PieceDrawable;
import fun.gusmurphy.chesses.piece.PieceSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class MatchScreen extends BaseScreen implements PieceSelectionListener {

    private PieceId selectedPieceId;
    private List<PieceDrawable> pieceDrawables;
    private final ChessEngine engine;

    public MatchScreen(final ChessesGame game) {
        super(game);
        engine = ChessEngine.defaultEngine();
        BoardDrawable board = setupBoard();
        setupPieceDrawables(board);
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

    private BoardDrawable setupBoard() {
        BoardDrawable board = new BoardDrawable(game, engine.currentBoardState());
        drawables.add(board);
        return board;
    }

    private void setupPieceDrawables(BoardDrawable board) {
        pieceDrawables = new ArrayList<>();

        board.boardState()
            .coordinateStates()
            .all()
            .forEach(coordinateState -> setupPieceDrawableForCoordinateState(coordinateState, board));
    }

    private void setupPieceDrawableForCoordinateState(BoardCoordinateState coordinateState, BoardDrawable board) {
        if (!coordinateState.piece().isPresent()) {
            return;
        }

        Vector2 piecePosition = board.getScreenPositionForCenterOf(coordinateState.coordinates());

        Piece piece = coordinateState.piece().get();
        PieceDrawable pieceDrawable = new PieceDrawable(piece, game.getSpriteBatch(), piecePosition);

        pieceDrawable.listenToSelection(this);
        pieceDrawables.add(pieceDrawable);
        drawables.add(pieceDrawable);
        inputProcessors.add(pieceDrawable);
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
