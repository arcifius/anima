package br.com.anima.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;

/**
 * Created by azakhary on 7/26/2015.
 */
public class UIStage extends Stage {

	private CompositeActor distancePane;

	public UIStage(SceneLoader sceneLoader) {
		super(new StretchViewport(1366, 768));

		Gdx.input.setInputProcessor(this);	
	}

	public void setDistanceValue(int distanceValue) {
		Label valueLbl = (Label) distancePane.getItem("value");
		valueLbl.setText(distanceValue + "");
	}
}
