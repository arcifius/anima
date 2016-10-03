package br.com.anima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import br.com.anima.screens.GameScreen;
import br.com.anima.screens.LoginScreen;
import br.com.anima.screens.SelectCharacterScreen;

public class GameManager extends Game {
	
	@Override
	public void create () {
		setScreen(new LoginScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			//setScreen(new MainMenuScreen(this));
		}
	}

}
