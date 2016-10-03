package br.com.anima.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

import br.com.anima.GameManager;
import br.com.anima.entities.EntityFactory;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.systems.Box2DDebugRendererSystem;
import br.com.anima.systems.CameraSystem;
import br.com.anima.systems.ControlSystem;
import br.com.anima.systems.MapControllerSystem;
import br.com.anima.systems.RenderSystem;
import br.com.anima.systems.UpdateBox2DSystem;
import br.com.anima.utils.CharacterDescription;
import br.com.anima.utils.Monster;
import br.com.anima.utils.Objects;
import br.com.anima.utils.Values;

public class GameScreen implements Screen {

	private GameManager game;
	private Engine engine;
	private CharacterDescription localPlayer;
	
	public GameScreen(GameManager game, CharacterDescription localPlayer) {
		this.game = game;
		this.localPlayer = localPlayer;
	}

	@Override
	public void show() {
		Objects.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Objects.world = new World(Values.WORLD_GRAVITY, false);

		Gdx.gl.glClearColor(0.1378f, 0.1378f, 0.1378f, 1); // (31 / 225f) where
														   // 31 is the RGB

		this.engine = new Engine();

		/* ADDING ENTITIES */
		this.engine.addEntity(EntityFactory.createPlayer(94 * 32, 117 * 32, BodyType.DynamicBody));
		this.engine.addEntity(EntityFactory.createEnemy(Monster.TROLL, 300, 0, BodyType.DynamicBody));
		this.engine.addEntity(EntityFactory.createEnemy(Monster.TROLL, 900, 0, BodyType.StaticBody));
		this.engine.addEntity(EntityFactory.createEnemy(Monster.AIRPLANE, 900, 300, BodyType.StaticBody));

		/* ADDING SYSTEMS */
		this.engine.addSystem(new ControlSystem());
		this.engine.addSystem(new UpdateBox2DSystem());
		this.engine.addSystem(new CameraSystem());
		this.engine.addSystem(new MapControllerSystem(this.game));
		this.engine.addSystem(new RenderSystem());
		//this.engine.addSystem(new Box2DDebugRendererSystem());

		/* RETRIEVING ALL ADDED SYSTEMS */
		ImmutableArray<EntitySystem> systems = this.engine.getSystems();
		
		/* INITIALIZING SYSTEMS */		
		for (int i = 0; i < systems.size(); i++) {
			Initializable init = (Initializable) systems.get(i);
			init.init();
		}

		/* CREATING SYSTEMS AFTER ALL OF THEM ARE ALREADY INITIALIZED */
		for (int i = 0; i < systems.size(); i++) {
			if (systems.get(i) instanceof Createable) {
				Createable create = (Createable) systems.get(i);
				create.create();
			}
		}
		
		/* DEFAULT VALUES */
		Objects.camera.zoom = Values.CAMERA_ZOOM;		

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
