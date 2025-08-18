package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.math.Vector2;

public abstract class PieceOnScreenState {

    protected PieceDrawable pieceDrawable;

    PieceOnScreenState(PieceDrawable pieceDrawable) {
        this.pieceDrawable = pieceDrawable;
    }

    public abstract void processInput(Vector2 cursorPosition);

    public abstract void setDragStatus(boolean isNowDragged);

}
