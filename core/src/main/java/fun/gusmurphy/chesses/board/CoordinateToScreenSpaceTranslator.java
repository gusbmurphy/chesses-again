package fun.gusmurphy.chesses.board;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.coordinates.Coordinates;

public interface CoordinateToScreenSpaceTranslator {

    Vector2 getScreenPositionForCenterOf(Coordinates coordinates);
}
