package br.com.anima.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import br.com.anima.controller.GameController;

public class Objects {

	public static final SpriteBatch spriteBatch = new SpriteBatch();
	public static OrthographicCamera camera;
	public static World world;
	public static MapSize mapsize = new MapSize();
	public static GameController gameController = new GameController();
	public static AssetManager assetManager = new AssetManager();
	
}
