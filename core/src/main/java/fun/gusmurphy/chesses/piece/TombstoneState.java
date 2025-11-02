package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.math.Vector2;

public class TombstoneState extends PieceOnScreenState {

    TombstoneState() {
        super(null);
    }

    @Override
    public void processInput(Vector2 cursorPosition) {}

    @Override
    public void setDragStatus(boolean isNowDragged) {}

    @Override
    public boolean toBeRemoved() {
        return true;
    }
}
