package fun.gusmurphy.chesses.screen;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.ChessesGame;
import fun.gusmurphy.chesses.board.BoardDrawable;
import fun.gusmurphy.chesses.engine.ChessEngine;
import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.piece.PieceType;
import fun.gusmurphy.chesses.engine.rules.MoveLegality;
import fun.gusmurphy.chesses.piece.PieceDrawable;
import fun.gusmurphy.chesses.piece.PieceSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchScreen extends BaseScreen implements PieceSelectionListener {

    private PieceId selectedPieceId;
    private List<PieceDrawable> pieceDrawables;
    private final ChessEngine engine;
    private final BoardDrawable board;

    public MatchScreen(final ChessesGame game) {
        super(game);

        BoardState boardState = new BoardStateBuilder()
            .currentTurnColor(PlayerColor.WHITE)
            .addPieceAt(new Piece(PlayerColor.WHITE, PieceType.BISHOP), Coordinates.A1)
            .addPieceAt(new Piece(PlayerColor.BLACK, PieceType.ROOK), Coordinates.H8)
            .build();

        engine = ChessEngine.defaultEngine(boardState);
        board = setupBoard();
        setupPieceDrawables();
    }

    @Override
    public void onPieceSelected(PieceId pieceId) {
        if (selectedPieceId == null) {
            dragPiece(pieceId);
            setHighlightedMovesFor(pieceId);
        }
    }

    @Override
    public void onPieceReleased(PieceId pieceId, Vector2 screenPosition) {
        if (selectedPieceId == pieceId) {
            stopDraggingPiece(pieceId);
            board.clearHighlights();

            Optional<Coordinates> coordinatesForReleasePosition = board.getCoordinatesForScreenPosition(screenPosition);

            if (!coordinatesForReleasePosition.isPresent()) {
                return;
            }

            Move move = new Move(pieceId, coordinatesForReleasePosition.get());
            engine.makeMove(move);
            BoardState newBoardState = engine.currentBoardState();
            board.updateBoardState(newBoardState);
            pieceDrawables.forEach(pieceDrawable -> {
                PieceOnBoard pieceOnBoard = newBoardState.pieceOnBoardForId(pieceDrawable.pieceId());
                Coordinates updatedPieceCoordinates = pieceOnBoard.coordinates();
                pieceDrawable.setPositionCenter(board.getScreenPositionForCenterOf(updatedPieceCoordinates));
            });
        }
    }

    private BoardDrawable setupBoard() {
        BoardDrawable board = new BoardDrawable(game, engine.currentBoardState());
        drawables.add(board);
        return board;
    }

    private void setupPieceDrawables() {
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

    private void dragPiece(PieceId pieceId) {
        selectedPieceId = pieceId;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(true);
    }

    private void setHighlightedMovesFor(PieceId pieceId) {
        List<Coordinates> coordinatesToHighlight = new ArrayList<>();
        for (Coordinates coordinates : Coordinates.values()) {
            Move possibleMove = new Move(pieceId, coordinates);
            MoveLegality moveLegality = engine.checkLegalityOf(possibleMove);

            if (moveLegality == MoveLegality.LEGAL) {
                coordinatesToHighlight.add(coordinates);
            }
        }

        board.setCoordinatesToHighlight(coordinatesToHighlight.toArray(new Coordinates[0]));
    }

    private void stopDraggingPiece(PieceId pieceId) {
        selectedPieceId = null;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(false);
    }

}
