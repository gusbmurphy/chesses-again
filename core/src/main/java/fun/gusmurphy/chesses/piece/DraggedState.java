package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DraggedState extends PieceOnScreenState {

    public DraggedState(PieceDrawable pieceDrawable) {
        super(pieceDrawable);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {
        pieceDrawable.setPositionCenter(cursorPosition);

        if (Gdx.input.justTouched()) {
            notifyListenersOfRelease(cursorPosition);
        }
    }

    @Override
    public void setDragStatus(boolean isNowDragged) {
        if (!isNowDragged) {
            pieceDrawable.setState(new DefaultState(pieceDrawable));
        }
    }

    private void notifyListenersOfRelease(Vector2 cursorPosition) {
        pieceDrawable.selectionListeners.forEach(
                listener -> listener.onPieceReleased(pieceDrawable.pieceId(), cursorPosition));
    }
}
