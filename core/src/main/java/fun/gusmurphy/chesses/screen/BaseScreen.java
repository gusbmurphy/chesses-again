package fun.gusmurphy.chesses.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import fun.gusmurphy.chesses.ChessesGame;
import fun.gusmurphy.chesses.Drawable;
import fun.gusmurphy.chesses.InputProcessor;
import fun.gusmurphy.chesses.Renderable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen implements Screen {

    protected final ChessesGame game;
    protected final Stage stage;
    protected final Skin skin;
    protected List<InputProcessor> inputProcessors = new ArrayList<>();
    protected List<Renderable> renderables = new ArrayList<>();
    protected List<Drawable> drawables = new ArrayList<>();

    private final Vector2 cursorPosition = new Vector2();

    BaseScreen(final ChessesGame game) {
        this.game = game;
        stage = new Stage();
        skin = setupSkin();
        Gdx.input.setInputProcessor(stage);
    }

    private Skin setupSkin() {
        final Skin skin;
        skin = new Skin(
            Gdx.files.internal("uiskin.json"),
            new TextureAtlas(
                Gdx.files.internal("uiskin.atlas")
            )
        );
        return skin;
    }

    @Override
    public void render(float delta) {
        cursorPosition.set(Gdx.input.getX(), Gdx.input.getY());
        game.getViewport().unproject(cursorPosition);

        processCursorPositionInput();

        ScreenUtils.clear(Color.WHITE);
        game.getViewport().apply();
        game.getSpriteBatch().setProjectionMatrix(game.getViewport().getCamera().combined);
        game.getShapeRenderer().setProjectionMatrix(game.getViewport().getCamera().combined);

        drawables.forEach(Drawable::draw);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void processCursorPositionInput() {
        inputProcessors.forEach(ip -> ip.processInput(cursorPosition));
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
