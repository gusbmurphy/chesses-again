package fun.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BoardRepresentation {

    private final SpriteBatch spriteBatch;
    private final Viewport viewport;
    private final Texture darkSquareTexture = new Texture("dark_square.png");
    private final Texture lightSquareTexture = new Texture("light_square.png");
    private final Rectangle bounds = new Rectangle();
    private final Vector2 cursorPosition = new Vector2();

    static private final int BOARD_WIDTH_IN_SQUARES = 8;
    public static final float SQUARE_SIZE = 40f;

    public BoardRepresentation(final ChessesGame game) {
        spriteBatch = game.getSpriteBatch();
        viewport = game.getViewport();
    }

    public void render() {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPosition);
    }

    public void draw() {
        float boardSize = boardSize();
        float bottomLeftX = bottomLeftX();
        float bottomLeftY = bottomLeftY();

        bounds.set(bottomLeftX, bottomLeftY, boardSize, boardSize);

        drawSpaces();
    }

    private void drawSpaces() {
        spriteBatch.begin();

        for (int x = 0; x < BOARD_WIDTH_IN_SQUARES; x++) {
            drawColumnAt(x);
        }

        spriteBatch.end();
    }

    private void drawColumnAt(int x) {
        for (int y = 0; y < BOARD_WIDTH_IN_SQUARES; y++) {
            drawSquareAt(x, y);
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
