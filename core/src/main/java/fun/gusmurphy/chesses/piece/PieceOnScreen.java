package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.piece.Piece;

import static fun.gusmurphy.chesses.Board.SQUARE_SIZE;

public class PieceOnScreen {

    protected final Piece piece;
    protected final Rectangle bounds;
    protected Sprite sprite;
    protected Vector2 position;

    private final static float PIECE_TO_SQUARE_SIZE_RATIO = 0.8f;
    private final SpriteBatch spriteBatch;

    public PieceOnScreen(Piece piece, SpriteBatch spriteBatch, Vector2 position) {
        this.piece = piece;
        this.spriteBatch = spriteBatch;
        this.position = position;
        bounds = new Rectangle();
        setSpriteFor(piece);
    }

    public void draw() {
        sprite.draw(spriteBatch);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    private void setSpriteFor(Piece piece) {
        sprite = PieceSprite.spriteFor(piece);
        sprite.setSize(SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO, SQUARE_SIZE * PIECE_TO_SQUARE_SIZE_RATIO);
        sprite.setCenter(position.x, position.y);
    }

}
