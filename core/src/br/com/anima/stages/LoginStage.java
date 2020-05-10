package br.com.anima.stages;

import br.com.anima.utils.ButtonDescription;
import br.com.anima.utils.Values;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.List;

/**
 * Created by Augusto Russo on 01/06/2016
 */
public class LoginStage extends Stage {

    private Skin skin;
    private TextField passInputField;
    private TextField loginInputField;
    private CompositeActor dialogBox;
    private SceneLoader sc;

    public LoginStage(SceneLoader sceneLoader) {
        super(new StretchViewport(1366, 768));

        this.sc = sceneLoader;

        //Loading components that can be used later
        CompositeItemVO _dialogBox = sceneLoader.loadVoFromLibrary("grayDialogBox");
        dialogBox = new CompositeActor(_dialogBox, sceneLoader.getRm());
        dialogBox.setVisible(false);
        this.addActor(dialogBox);

        // Load default skin
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        ItemWrapper rootItem = new ItemWrapper(sceneLoader.getRoot());

        // Login input transform
        TransformComponent loginInputTransform = rootItem.getChild("login").getEntity()
                .getComponent(TransformComponent.class);
        // Password input transform
        TransformComponent passInputTransform = rootItem.getChild("password").getEntity()
                .getComponent(TransformComponent.class);

        loginInputField = new TextField("", skin);
        loginInputField.setMessageText("Username");
        loginInputField.setPosition((loginInputTransform.x - 10) * Values.WORLD_UNITS,
                loginInputTransform.y * Values.WORLD_UNITS);
        loginInputField.setSize(300, 40);

        passInputField = new TextField("", skin);
        passInputField.setPasswordMode(true);
        passInputField.setPasswordCharacter('*');
        passInputField.setMessageText("Password");
        passInputField.setPosition((passInputTransform.x - 10) * Values.WORLD_UNITS,
                passInputTransform.y * Values.WORLD_UNITS);
        passInputField.setSize(300, 40);

        //this.addActor(loginInputField);
        //this.addActor(passInputField);

        Gdx.input.setInputProcessor(this);
    }

    public String getLoginInputText() {

        return loginInputField.getText();

    }

    public char[] getPasswordInputText() {

        return passInputField.getText().toCharArray();

    }

    public void showDialog(String title, String content, List<ButtonDescription> buttons) {

        Label lbl_title = (Label) dialogBox.getItem("dialogTitle");
        Label lbl_content = (Label) dialogBox.getItem("dialogContent");

        lbl_title.setText(title);
        lbl_content.setText(content);
        lbl_content.setWrap(true);

        //dialogBox.setZIndex(10);
        dialogBox.setPosition((Gdx.graphics.getWidth() / 2) - dialogBox.getWidth() / 2,
                (Gdx.graphics.getHeight() / 2) - dialogBox.getHeight() / 2);

        Table buttonsBar = new Table();
        buttonsBar.setWidth(dialogBox.getWidth());

        for (ButtonDescription button : buttons) {

            switch (button.getColor()) {
                case AMBER:
                    break;
                case BLUE:

                    break;
                case GREEN:
                    break;
                case RED:
                    CompositeItemVO _redButton = sc.loadVoFromLibrary("redButton");
                    CompositeActor redButton = new CompositeActor(_redButton, sc.getRm());

                    Label defaultState = (Label) redButton.getItem("defaultText");
                    Label pressedState = (Label) redButton.getItem("pressedText");

                    defaultState.setText(button.getText());
                    pressedState.setText(button.getText());
                    redButton.addListener(button.getListener());
                    redButton.setPosition(dialogBox.getWidth() - redButton.getWidth() - 5, redButton.getY() + 5);
                    dialogBox.addActor(redButton);
                    break;
                default:
                    break;
            }

        }

        //dialogBox.addActor(buttonsBar);
        dialogBox.setVisible(true);
    }

    public void hideDialog() {
        dialogBox.setVisible(false);
    }

}
