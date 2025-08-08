package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.math.Vector2;

public abstract class PieceOnScreenState {

    protected PieceOnScreen pieceOnScreen;

    PieceOnScreenState(PieceOnScreen pieceOnScreen) {
        this.pieceOnScreen = pieceOnScreen;
    }

    public abstract void processInput(Vector2 cursorPosition);

    public abstract void setDragStatus(boolean isNowDragged);

}
