package br.com.anima;

import br.com.anima.views.CharacterSelectionView;
import br.com.anima.views.GameView;
import br.com.anima.views.LoginView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.VisUI;

public class Igniter extends LmlApplicationListener {
    public static final int WIDTH = 1333, HEIGHT = 728;
    private Batch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        super.create();

        // Pre-load views
        addClassAlias(CharacterSelectionView.ID, CharacterSelectionView.class);

        // Initial view
        setView(GameView.class);
    }

    public Batch getBatch() {
        return batch;
    }

    /**
     * @return a new customized {@link Stage} instance.
     */
    public static Stage newStage() {
        // Getting our Core instance:
        Igniter manager = (Igniter) Gdx.app.getApplicationListener();
        return new Stage(new FitViewport(WIDTH, HEIGHT), manager.getBatch());
    }

    @Override
    protected LmlParser createParser() {
        // could we use this for custom skin?
        // Lml.parser(new Skin(Gdx.files.internal("skin.json"))).build();
        // see: https://github.com/kotcrab/vis-ui#modifying-skin
        return VisLml.parser()
                .i18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/bundle")))
                .build();
    }

    @Override
    public void render() {
        super.render();

        // TODO: this is being used only for test purposes
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            //setScreen(new LoginScreen(this));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Disposables.disposeOf(batch);
        VisUI.dispose();
    }

}
