package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceId;

import java.util.ArrayList;
import java.util.List;

import static fun.gusmurphy.chesses.BoardOnScreen.SQUARE_SIZE;

public class PieceOnScreen {

    protected final Rectangle bounds;
    protected Sprite sprite;
    protected Vector2 position;
    protected final List<PieceOnScreenSelectionListener> selectionListeners = new ArrayList<>();

    private final Piece piece;
    private final static float PIECE_TO_SQUARE_SIZE_RATIO = 0.8f;
    private final SpriteBatch spriteBatch;
    private PieceOnScreenState state;

    public PieceOnScreen(Piece piece, SpriteBatch spriteBatch, Vector2 position) {
        this.piece = piece;
        this.spriteBatch = spriteBatch;
        this.position = position;
        this.state = new DefaultState(this);
        bounds = new Rectangle();
        setSpriteFor(piece);
    }

    public void draw() {
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public PieceId pieceId() {
        return piece.id();
    }

    public void setDragStatus(boolean isNowDragged) {
        state.setDragStatus(isNowDragged);
    }

    public void processInput(Vector2 cursorPosition) {
        state.processInput(cursorPosition);
    }

    public void listenToSelection(PieceOnScreenSelectionListener listener) {
        selectionListeners.add(listener);
    }

    protected void setState(PieceOnScreenState newState) {
        state = newState;
    }

    private void setSpriteFor(Piece piece) {
        sprite = PieceSprite.spriteFor(piece);
        sprite.setSize(SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO, SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO);
        sprite.setCenter(position.x, position.y);
    }

}
