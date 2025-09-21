package fun.gusmurphy.chesses.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import fun.gusmurphy.chesses.ChessesGame;
import fun.gusmurphy.chesses.Drawable;
import fun.gusmurphy.chesses.engine.Coordinates;
import fun.gusmurphy.chesses.engine.boardstate.BoardCoordinateStates;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;

public class BoardDrawable implements Drawable, CoordinateToScreenSpaceTranslator {

    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport viewport;
    private final Texture darkSquareTexture = new Texture("dark_square.png");
    private final Texture lightSquareTexture = new Texture("light_square.png");
    private final Rectangle bounds = new Rectangle();
    private final BoardState boardState;
    private Coordinates[] coordinatesToHighlight;

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    private static final Color HIGHLIGHT_COLOR = new Color(0, 1, 1, 0.5f);
    public static final float SQUARE_SIZE = 40f;

    public BoardDrawable(final ChessesGame game, BoardState initialBoardState) {
        spriteBatch = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        viewport = game.getViewport();
        boardState = initialBoardState;
        coordinatesToHighlight = new Coordinates[0];
    }

    public void draw() {
        float boardSize = boardSize();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();

        bounds.set(bottomLeftX, bottomLeftY, boardSize, boardSize);

        drawSpaces();
        drawHighlightedSpaces();
    }

    public Vector2 getScreenPositionForCenterOf(Coordinates coordinates) {
        CoordinatesXyAdapter xyAdapter = new CoordinatesXyAdapter(coordinates);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float boardWidth = BOARD_WIDTH_IN_SQUARES * SQUARE_SIZE;

        float x = xyAdapter.x() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldWidth / 2 - boardWidth / 2;
        float y = xyAdapter.y() * SQUARE_SIZE + SQUARE_SIZE / 2 + worldHeight / 2 - boardWidth / 2;
        return new Vector2(x, y);
    }

    public BoardState boardState() {
        return boardState;
    }

    public void setCoordinatesToHighlight(Coordinates[] coordinatesToHighlight) {
        this.coordinatesToHighlight = coordinatesToHighlight;
    }

    public void clearHighlights() {
        this.coordinatesToHighlight = new Coordinates[0];
    }

    private void drawSpaces() {
        spriteBatch.begin();
        BoardCoordinateStates coordinateStates = boardState.coordinateStates();
        // TODO: Maybe the squares should be able to draw themselves?
        for (Coordinates c : Coordinates.values()) {
            coordinateStates.forCoordinates(c).ifPresent(boardCoordinateState -> {
                CoordinatesXyAdapter xyAdapter = new CoordinatesXyAdapter(c);
                drawSquareAt(xyAdapter.x(), xyAdapter.y());
            });
        }
        spriteBatch.end();
    }

    private void drawHighlightedSpaces() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Coordinates coordinates : coordinatesToHighlight) {
            Vector2 center = getScreenPositionForCenterOf(coordinates);
            float xPosition = center.x - SQUARE_SIZE / 2;
            float yPosition = center.y - SQUARE_SIZE / 2;

            shapeRenderer.setColor(HIGHLIGHT_COLOR);
            shapeRenderer.rect(xPosition, yPosition, SQUARE_SIZE, SQUARE_SIZE);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
