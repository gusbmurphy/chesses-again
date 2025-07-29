package fun.gusmurphy.chesses.engine.piece;

import java.util.Objects;

public class PieceId {

    /**
     * The reason behind using a `long` for the ID implementation is that GWT does not
     * have an equivalent for UUID, oh well!
     */
    private final long id;

    public PieceId() {
        id = System.nanoTime();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PieceId pieceId = (PieceId) o;
        return Objects.equals(id, pieceId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
