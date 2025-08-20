package fun.gusmurphy.chesses.piece;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.piece.PieceId;

public interface PieceSelectionListener {
    void onPieceSelected(PieceId pieceId);
    void onPieceReleased(PieceId pieceId, Vector2 screenPosition);
}
