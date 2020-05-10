package br.com.anima.screens;

import br.com.anima.Igniter;
import br.com.anima.stages.LoginStage;
import br.com.anima.utils.ButtonColor;
import br.com.anima.utils.ButtonDescription;
import br.com.anima.utils.Objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.additional.ButtonComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.LinkedList;

@Deprecated
public class LoginScreen implements Screen {
    private final Igniter game;

    private SceneLoader sl;

    private Viewport viewport;
    private ItemWrapper rootItem;

    private LoginStage loginStage;

    public LoginScreen(Igniter game) {
        this.game = game;
    }

    @Override
    public void show() {
        // SCREEN WIDTH / WORLD UNITS & SCREEN HEIGHT / WORLD UNITS
        viewport = new FitViewport(137, 77);

        sl = new SceneLoader();
        sl.loadScene("MainScene", viewport); // Load login scene
        rootItem = new ItemWrapper(sl.getRoot()); // Retrieve the root item

        //Enable buttons
        sl.addComponentsByTagName("button", ButtonComponent.class);

        //Login interface behavior
        ButtonComponent loginButton = rootItem.getChild("loginButton").getEntity().getComponent(ButtonComponent.class);
        loginButton.addListener(new ButtonComponent.ButtonListener() {
            public void touchUp() {
                //Nothing
            }

            public void touchDown() {
                //Nothing
            }

            public void clicked() {

                if (Objects.gameController.authenticateUser(loginStage.getLoginInputText(), loginStage.getPasswordInputText())) {
                    // game.setView(new SelectCharacterScreen(game));
                } else {

                    ButtonDescription close = new ButtonDescription("OKAY", ButtonColor.RED, new ClickListener() {

                        public void clicked(InputEvent event, float x, float y) {
                            loginStage.hideDialog();
                        }

                    });

                    LinkedList<ButtonDescription> buttons = new LinkedList<ButtonDescription>();
                    buttons.add(close);

                    loginStage.showDialog("AUTHENTICATION FAILED", "Please check your credentials and try again (:", buttons);
                }

            }
        });

        loginStage = new LoginStage(sl);
    }

    public void act() {

    }

    @Override
    public void render(float delta) {
        act();
        Gdx.gl.glClearColor(255 / 225f, 255 / 225f, 255 / 225f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sl.getEngine().update(Gdx.graphics.getDeltaTime()); // getting the
        // ashley engine and
        // updating it (it
        // will render
        // things with it's
        // own render
        // system)

        loginStage.act();
        loginStage.draw();
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
        this.loginStage.dispose();
    }

}
