package fun.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateState;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.piece.PieceOnScreen;
import fun.gusmurphy.chesses.piece.PieceOnScreenSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class BoardOnScreen implements PieceOnScreenSelectionListener, Drawable {

    private final SpriteBatch spriteBatch;
    private final Viewport viewport;
    private final Texture darkSquareTexture = new Texture("dark_square.png");
    private final Texture lightSquareTexture = new Texture("light_square.png");
    private final Rectangle bounds = new Rectangle();
    private final Vector2 cursorPosition = new Vector2();
    private final BoardState boardState;
    private final List<PieceOnScreen> piecesOnScreen = new ArrayList<>();
    private PieceId selectedPieceId;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    public static final float SQUARE_SIZE = 40f;

    public BoardOnScreen(final ChessesGame game, BoardState initialBoardState) {
        spriteBatch = game.getSpriteBatch();
        viewport = game.getViewport();
        boardState = initialBoardState;

        BoardCoordinateStates coordinateStates = boardState.allCoordinateStates();
        for (Coordinates c : Coordinates.values()) {
            coordinateStates.forCoordinates(c).flatMap(BoardCoordinateState::piece).ifPresent(piece -> {
                Vector2 piecePosition = getScreenPositionForCenterOf(c);
                PieceOnScreen pieceOnScreen = new PieceOnScreen(piece, spriteBatch, piecePosition);
                pieceOnScreen.listenToSelection(this);
                piecesOnScreen.add(pieceOnScreen);
            });
        }
    }

    public void render() {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);

        for (PieceOnScreen piece : piecesOnScreen) {
            piece.processInput(cursorPosition);
        }
    }

    public void draw() {
        float boardSize = boardSize();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();

        bounds.set(bottomLeftX, bottomLeftY, boardSize, boardSize);

        spriteBatch.begin();
        drawSpaces();
        piecesOnScreen.forEach(PieceOnScreen::draw);
        spriteBatch.end();
    }

    // TODO: What if all the piece selection stuff lived somewhere else?
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
        PieceOnScreen pieceOnScreen = piecesOnScreen
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceOnScreen.setDragStatus(true);
    }

    private void unselectPiece(PieceId pieceId) {
        selectedPieceId = null;
        PieceOnScreen pieceOnScreen = piecesOnScreen
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceOnScreen.setDragStatus(false);
    }

    private void drawSpaces() {
        BoardCoordinateStates coordinateStates = boardState.allCoordinateStates();
        // TODO: Maybe the squares should be able to draw themselves?
        for (Coordinates c : Coordinates.values()) {
            coordinateStates.forCoordinates(c).ifPresent(boardCoordinateState -> {
                CoordinatesXyAdapter xyAdapter = new CoordinatesXyAdapter(c);
                drawSquareAt(xyAdapter.x(), xyAdapter.y());
            });
        }
    }

    private void drawSquareAt(int x, int y) {
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();
        boolean isDark = (x % 2) == (y % 2);
        Texture texture = isDark ? darkSquareTexture : lightSquareTexture;
        float xPosition = x * SQUARE_SIZE + bottomLeftX;
        float yPosition = y * SQUARE_SIZE + bottomLeftY;
        spriteBatch.draw(texture, xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
    }

    private Vector2 getScreenPositionForCenterOf(Coordinates coordinates) {
        CoordinatesXyAdapter xyAdapter = new CoordinatesXyAdapter(coordinates);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;

        float x = xyAdapter.x() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldWidth / 2 - boardWidth / 2;
        float y = xyAdapter.y() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldHeight / 2 - boardWidth / 2;
        return new Vector2(x, y);
    }

    private float boardSize() {
        return BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;
    }

    private float bottomLeftX() {
        return viewport.getWorldWidth() / 2 - boardSize() / 2;
    }

    private float bottomLeftY() {
        return viewport.getWorldHeight() / 2 - boardSize() / 2;
    }

}
