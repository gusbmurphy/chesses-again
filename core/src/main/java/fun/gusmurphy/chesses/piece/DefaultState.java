package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DefaultState extends PieceOnScreenState {

    public DefaultState(PieceDrawable pieceDrawable) {
        super(pieceDrawable);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {
        if (pieceWasJustClicked(cursorPosition)) {
            notifySelectionListeners();
        }
    }

    @Override
    public void setDragStatus(boolean isNowDragged) {
        if (isNowDragged) {
            pieceDrawable.setState(new DraggedState(pieceDrawable));
        }
    }

    private boolean pieceWasJustClicked(Vector2 cursorPosition) {
        return Gdx.input.justTouched()
            && pieceDrawable.bounds.contains(cursorPosition);
    }

    private void notifySelectionListeners() {
        pieceDrawable.selectionListeners.forEach(
            listener -> listener.onPieceSelected(pieceDrawable.pieceId())
        );
    }

}
