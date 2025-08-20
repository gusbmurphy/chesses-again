package fun.gusmurphy.chesses;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.piece.PieceId;
import fun.gusmurphy.chesses.piece.PieceDrawable;
import fun.gusmurphy.chesses.piece.PieceOnScreenSelectionListener;

import java.util.List;

public class PieceSelectionManager implements PieceOnScreenSelectionListener {

    private PieceId selectedPieceId;
    private List<PieceDrawable> pieceDrawables;

    public PieceSelectionManager(List<PieceDrawable> pieceDrawables) {
        this.pieceDrawables = pieceDrawables;
        pieceDrawables.forEach(p -> p.listenToSelection(this));
    }

    @Override
    public void onPieceSelected(PieceId pieceId) {
        if (selectedPieceId == null) {
            updateSelectedPiece(pieceId);
        }
    }

    @Override
    public void onPieceReleased(PieceId pieceId, Vector2 screenPosition) {
        if (selectedPieceId == pieceId) {
            unselectPiece(pieceId);
        }
    }

    private void updateSelectedPiece(PieceId pieceId) {
        selectedPieceId = pieceId;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(true);
    }

    private void unselectPiece(PieceId pieceId) {
        selectedPieceId = null;
        PieceDrawable pieceDrawable = pieceDrawables
            .stream().filter(piece -> piece.pieceId() == pieceId).findFirst().get();
        pieceDrawable.setDragStatus(false);
    }

}
