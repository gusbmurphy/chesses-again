package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DraggedState extends PieceOnScreenState {

    public DraggedState(PieceOnScreen pieceOnScreen) {
        super(pieceOnScreen);
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
            pieceOnScreen.setState(new DefaultState(pieceOnScreen));
        }
    }

    private void updateSpritePositionTo(Vector2 newPosition) {
        pieceOnScreen.sprite.setPosition(
            newPosition.x - pieceOnScreen.sprite.getWidth() / 2,
            newPosition.y - pieceOnScreen.sprite.getHeight() / 2
        );
    }

    private void notifyListenersOfRelease(Vector2 cursorPosition) {
        pieceOnScreen.selectionListeners.forEach(listener ->
            listener.onPieceReleased(pieceOnScreen.pieceId(), cursorPosition)
        );
    }

    private void updateSpriteCenter() {
        pieceOnScreen.sprite.setCenter(pieceOnScreen.position.x, pieceOnScreen.position.y);
    }

}
