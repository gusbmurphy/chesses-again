package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DraggedState extends PieceOnScreenState {

    public DraggedState(PieceDrawable pieceDrawable) {
        super(pieceDrawable);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {
        updateSpritePositionTo(cursorPosition);

        if (Gdx.input.justTouched()) {
            notifyListenersOfRelease(cursorPosition);
            updateSpriteCenter();
        }
    }

    @Override
    public void setDragStatus(boolean isNowDragged) {
        if (!isNowDragged) {
            pieceDrawable.setState(new DefaultState(pieceDrawable));
        }
    }

    private void updateSpritePositionTo(Vector2 newPosition) {
        pieceDrawable.sprite.setPosition(
            newPosition.x - pieceDrawable.sprite.getWidth() / 2,
            newPosition.y - pieceDrawable.sprite.getHeight() / 2
        );
    }

    private void notifyListenersOfRelease(Vector2 cursorPosition) {
        pieceDrawable.selectionListeners.forEach(listener ->
            listener.onPieceReleased(pieceDrawable.pieceId(), cursorPosition)
        );
    }

    private void updateSpriteCenter() {
        pieceDrawable.sprite.setCenter(pieceDrawable.position.x, pieceDrawable.position.y);
    }

}
