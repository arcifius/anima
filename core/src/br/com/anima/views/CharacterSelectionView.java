package br.com.anima.views;

import br.com.anima.Igniter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public class CharacterSelectionView extends AbstractLmlView {
    public static final String ID = "characterSelection";
    private final Igniter igniter = (Igniter) Gdx.app.getApplicationListener();

    public CharacterSelectionView() {
        super(Igniter.newStage());
    }

    @LmlAction("enterGame")
    public void enter() {
        System.out.println("move to game screen");
        igniter.setView(GameView.class);
    }

    @LmlAction("select")
    public void select() {
        System.out.println("select character");

    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/characterSelection.lml");
    }

    @Override
    public String getViewId() {
        return ID;
    }
}