package fun.gusmurphy.chesses.engine.piece;

import java.util.Objects;
import java.util.UUID;

public class PieceId {

    private final UUID uuid;

    public PieceId() {
        uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PieceId pieceId = (PieceId) o;
        return Objects.equals(uuid, pieceId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

}
