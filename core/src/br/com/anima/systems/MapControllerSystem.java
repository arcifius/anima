package br.com.anima.systems;

import br.com.anima.components.MapComponent;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class MapControllerSystem extends EntitySystem implements Initializable, Createable {
    private final ComponentMapper<MapComponent> mapMapper;
    private ImmutableArray<Body> tiledMapBodies;
    private ImmutableArray<Entity> entities;
    private Array<Entity> pointLights;
    private Array<Entity> enemies;
    private boolean[][] solids;

    public MapControllerSystem() {
        this.mapMapper = ComponentMapper.getFor(MapComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(MapComponent.class).get());

        if (entities.size() > 1) {
            throw new RuntimeException("Only one entity can hold MapComponent!");
        }
    }

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
        TiledMap map = Objects.assetManager.get("maps/debain.tmx");
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            // TODO: improve this to not create a new instance of renderer on each frame
            MapComponent mapper = this.mapMapper.get(entity);
            TiledMap map = Objects.assetManager.get("maps/" + mapper.map + ".tmx");
            mapper.renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
        }
    }

    private void loadMap(int map, Vector2 newPosition) {
        //initSolidTiles(maps[map]);
    }

}
