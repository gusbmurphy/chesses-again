package fun.gusmurphy.chesses.screen;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.ChessesGame;
import fun.gusmurphy.chesses.board.BoardDrawable;
import fun.gusmurphy.chesses.engine.ChessEngine;
import fun.gusmurphy.chesses.engine.Move;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.engine.piece.PieceOnBoard;
import fun.gusmurphy.chesses.engine.rules.RuleEvaluation;
import fun.gusmurphy.chesses.piece.PieceDrawable;
import fun.gusmurphy.chesses.piece.PieceSelectionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MatchScreen extends BaseScreen implements PieceSelectionListener {

    private PieceId selectedPieceId;
    private List<PieceDrawable> pieceDrawables;
    private final ChessEngine engine;
    private final BoardDrawable board;

    public MatchScreen(final ChessesGame game) {
        super(game);
        engine = ChessEngine.defaultEngine();
        board = setupBoard();
        setupPieceDrawables();
    }

    @Override
    public void render(float delta) {
        removePiecesToBeRemoved();
        super.render(delta);
    }

    private void removePiecesToBeRemoved() {
        List<PieceDrawable> piecesToBeRemoved =
                pieceDrawables.stream()
                        .filter(PieceDrawable::toBeRemoved)
                        .collect(Collectors.toList());

        // TODO: Why remove these from three different places?
        pieceDrawables.removeAll(piecesToBeRemoved);
        drawables.removeAll(piecesToBeRemoved);
        inputProcessors.removeAll(piecesToBeRemoved);
    }

    @Override
    public void onPieceSelected(PieceId pieceId) {
        if (selectedPieceId == null) {
            dragPiece(pieceId);
            setHighlightedMovesFor(pieceId);
            bringDrawableForPieceToFront(pieceId);
        }
    }

    @Override
    public void onPieceReleased(PieceId pieceId, Vector2 screenPosition) {
        if (selectedPieceId != pieceId) {
            return;
        }

        stopDraggingPiece(pieceId);
        board.clearHighlights();

        Optional<Coordinates> coordinatesForReleasePosition =
                board.getCoordinatesForScreenPosition(screenPosition);

        if (!coordinatesForReleasePosition.isPresent()) {
            return;
        }

        Move move = new Move(pieceId, coordinatesForReleasePosition.get());
        engine.makeMove(move);
        BoardState newBoardState = engine.currentBoardState();
        board.updateBoardState(newBoardState);
        pieceDrawables.forEach(
                pieceDrawable -> {
                    Optional<PieceOnBoard> pieceOnBoard =
                            newBoardState.pieceOnBoardForId(pieceDrawable.pieceId());

                    // TODO: Feels like there's some sort of polymorphism missing here...
                    if (pieceOnBoard.isPresent()) {
                        Coordinates updatedPieceCoordinates = pieceOnBoard.get().coordinates();
                        pieceDrawable.setPositionCenter(
                                board.getScreenPositionForCenterOf(updatedPieceCoordinates));
                    } else {
                        pieceDrawable.markForRemoval();
                    }
                });
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
                .forEach(
                        coordinateState ->
                                setupPieceDrawableForCoordinateState(coordinateState, board));
    }

    private void setupPieceDrawableForCoordinateState(
            BoardCoordinateState coordinateState, BoardDrawable board) {
        if (!coordinateState.piece().isPresent()) {
            return;
        }

        Vector2 piecePosition = board.getScreenPositionForCenterOf(coordinateState.coordinates());

        Piece piece = coordinateState.piece().get();
        PieceDrawable pieceDrawable =
                new PieceDrawable(piece, game.getSpriteBatch(), piecePosition);

        pieceDrawable.listenToSelection(this);
        pieceDrawables.add(pieceDrawable);
        drawables.add(pieceDrawable);
        inputProcessors.add(pieceDrawable);
    }

    private void dragPiece(PieceId pieceId) {
        selectedPieceId = pieceId;
        PieceDrawable pieceDrawable =
                pieceDrawables.stream()
                        .filter(piece -> piece.pieceId() == pieceId)
                        .findFirst()
                        .get();
        pieceDrawable.setDragStatus(true);
    }

    private void setHighlightedMovesFor(PieceId pieceId) {
        List<Coordinates> coordinatesToHighlight = new ArrayList<>();
        for (Coordinates coordinates : Coordinates.values()) {
            Move possibleMove = new Move(pieceId, coordinates);
            RuleEvaluation.Legality moveLegality = engine.checkLegalityOf(possibleMove);

            if (moveLegality == RuleEvaluation.Legality.LEGAL) {
                coordinatesToHighlight.add(coordinates);
            }
        }

        board.setCoordinatesToHighlight(coordinatesToHighlight.toArray(new Coordinates[0]));
    }

    private void bringDrawableForPieceToFront(PieceId pieceId) {
        PieceDrawable drawable =
                pieceDrawables.stream()
                        .filter(piece -> piece.pieceId() == pieceId)
                        .findFirst()
                        .get();

        int selectedDrawableIndex = drawables.indexOf(drawable);
        Collections.swap(drawables, selectedDrawableIndex, drawables.size() - 1);
    }

    private void stopDraggingPiece(PieceId pieceId) {
        selectedPieceId = null;
        PieceDrawable pieceDrawable =
                pieceDrawables.stream()
                        .filter(piece -> piece.pieceId() == pieceId)
                        .findFirst()
                        .get();
        pieceDrawable.setDragStatus(false);
    }
}
