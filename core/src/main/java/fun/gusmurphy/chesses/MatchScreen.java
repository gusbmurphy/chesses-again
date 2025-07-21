package fun.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MatchScreen extends BaseScreen {

    private final BoardRepresentation boardRepresentation;

    public MatchScreen(final ChessesGame game) {
        super(game);
        boardRepresentation = new BoardRepresentation(game);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        boardRepresentation.render();
        drawScreen();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    private void drawScreen() {
        ScreenUtils.clear(Color.WHITE);
        game.getViewport().apply();
        game.getSpriteBatch().setProjectionMatrix(game.getViewport().getCamera().combined);
        game.getShapeRenderer().setProjectionMatrix(game.getViewport().getCamera().combined);

        boardRepresentation.draw();
    }

}
