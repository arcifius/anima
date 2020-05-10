package br.com.anima.screens;

import br.com.anima.Igniter;
import br.com.anima.utils.CharacterDescription;
import br.com.anima.utils.Objects;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

@Deprecated
public class GameScreen implements Screen {

    private Igniter game;
    private Engine engine;
    private CharacterDescription localPlayer;

    public GameScreen(Igniter game, CharacterDescription localPlayer) {
        this.game = game;
        this.localPlayer = localPlayer;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Objects.camera.update();
        Objects.world.step(delta, 6, 2);

        this.engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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
