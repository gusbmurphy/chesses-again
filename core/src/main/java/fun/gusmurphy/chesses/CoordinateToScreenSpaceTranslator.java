package fun.gusmurphy.chesses;

import com.badlogic.gdx.math.Vector2;
import fun.gusmurphy.chesses.engine.Coordinates;

public interface CoordinateToScreenSpaceTranslator {

    Vector2 getScreenPositionForCenterOf(Coordinates coordinates);

}
