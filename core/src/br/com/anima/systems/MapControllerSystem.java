package br.com.anima.systems;

import br.com.anima.Igniter;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import br.com.anima.utils.RunnablePool;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class MapControllerSystem extends EntitySystem implements Initializable, Createable {

    private final Igniter igniter = (Igniter) Gdx.app.getApplicationListener();
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

    @Override
    public void init() {
        // TODO: centralize this, it will be hard to guess what is being preloaded if you don't.
        Objects.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        Objects.assetManager.load("maps/mapa.tmx", TiledMap.class);
        Objects.assetManager.load("maps/debain.tmx", TiledMap.class);
        Objects.assetManager.finishLoading();
    }

    @Override
    public void create() {

    }

    @Override
    public void update(float deltaTime) {

    }

    private void loadMap(int map, Vector2 newPosition) {
        //initSolidTiles(maps[map]);
    }

}
