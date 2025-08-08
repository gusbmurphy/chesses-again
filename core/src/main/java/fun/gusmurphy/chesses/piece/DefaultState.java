package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class DefaultState extends PieceOnScreenState {

    public DefaultState(PieceOnScreen pieceOnScreen) {
        super(pieceOnScreen);
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
            pieceOnScreen.setState(new DraggedState(pieceOnScreen));
        }
    }

    private boolean pieceWasJustClicked(Vector2 cursorPosition) {
        return Gdx.input.justTouched()
            && pieceOnScreen.bounds.contains(cursorPosition);
    }

    private void notifySelectionListeners() {
        pieceOnScreen.selectionListeners.forEach(
            listener -> listener.onPieceSelected(pieceOnScreen.pieceId())
        );
    }

}
