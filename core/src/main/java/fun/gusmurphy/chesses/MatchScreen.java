package fun.gusmurphy.chesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import fun.gusmurphy.chesses.engine.boardstate.BoardState;
import fun.gusmurphy.chesses.engine.boardstate.BoardStateBuilder;

public class MatchScreen extends BaseScreen {

    private final Board board;

    public MatchScreen(final ChessesGame game) {
        super(game);
        BoardState initialBoardState = new BoardStateBuilder()
            .height(3)
            .width(7)
            .build();
        board = new Board(game, initialBoardState);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        board.render();
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

        board.draw();
    }

}
