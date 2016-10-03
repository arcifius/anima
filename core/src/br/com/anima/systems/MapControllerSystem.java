package br.com.anima.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import br.com.anima.GameManager;
import br.com.anima.components.ControlledComponent;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Box2DUtils;
import br.com.anima.utils.Objects;
import br.com.anima.utils.RunnablePool;
import br.com.anima.utils.Values;

public class MapControllerSystem extends EntitySystem implements Initializable, Createable {

	private GameManager game;
	private Engine engine;
	private Entity player;
	private TiledMap[] maps;
	private OrthogonalTiledMapRenderer mapRenderer;
	private boolean[][] solids;
	private ImmutableArray<Body> tiledMapBodies;
	private Array<Entity> pointLights;
	private Array<Entity> enemies;
	private RunnablePool runnablePool;
	private boolean alreadyHurted = false;
	private int lastGate = -1;

	public MapControllerSystem(GameManager game) {
		this.game = game;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void create() {
		
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		
	}

	@Override
	public void update(float deltaTime) {

	}
	
	private void loadMap(int map, Vector2 newPosition){			
		//initSolidTiles(maps[map]);
	}

}
