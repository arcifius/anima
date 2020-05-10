package br.com.anima.views;

import br.com.anima.Igniter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisTextField;

public class LoginView extends AbstractLmlView {
    @LmlActor("loginInput")
    private VisTextField loginInput;
    @LmlActor("passwordInput")
    private VisTextField passwordInput;

    private final Igniter igniter = (Igniter) Gdx.app.getApplicationListener();

    public LoginView() {
        super(Igniter.newStage());
    }

    @LmlAction("signIn")
    public void signIn() {
        System.out.println(loginInput.getText());
        System.out.println(passwordInput.getText());

        igniter.setView(CharacterSelectionView.class);
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/login.lml");
    }

    @Override
    public String getViewId() {
        return "login";
    }
}