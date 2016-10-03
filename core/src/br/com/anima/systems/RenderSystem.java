package br.com.anima.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import br.com.anima.GameManager;
import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.ControlledComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.AnimaAnimation;
import br.com.anima.utils.Box2DUtils;
import br.com.anima.utils.Objects;
import br.com.anima.utils.Values;

public class RenderSystem extends EntitySystem implements Initializable {
	
	private GameManager game;
	private Engine engine;
	private TiledMap[] maps;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private ImmutableArray<Entity> entities, animators;	
	private Entity player;

	private ComponentMapper<SpriteComponent> spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<AnimatorComponent> animatorMapper = ComponentMapper.getFor(AnimatorComponent.class);
	private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
	
    private float elapsedTime = 0;

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		player = engine.getEntitiesFor(Family.all(ControlledComponent.class).get()).get(0);
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());		
		animators = engine.getEntitiesFor(Family.all(AnimatorComponent.class).get());
	}
	
	@Override
	public void init() {
		
		maps = new TiledMap[Values.MAPS];

		TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());

		maps[0] = mapLoader.load("maps/mapa.tmx");

		mapRenderer = new OrthogonalTiledMapRenderer(maps[0]);
		
		Box2DUtils.detectCollision(mapRenderer.getMap().getLayers().get("collision"));
		
		Objects.mapsize.changeMapDimensions(200, Values.TILE_SIZE);				
		
	}

	@Override
	public void update(float deltaTime) {

		mapRenderer.setView(Objects.camera);
		mapRenderer.render(Values.GROUND_LAYERS);

		Objects.spriteBatch.setProjectionMatrix(Objects.camera.combined);
		Objects.spriteBatch.begin();

		for (Entity sprite : entities) {
			SpriteComponent spriteComp = spriteMapper.get(sprite);
			
			spriteComp.sprite.draw(Objects.spriteBatch);
		}
		
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		for(Entity animator : animators) {
								
			AnimatorComponent animatorComp = animatorMapper.get(animator);
			PositionComponent positionComp = posMapper.get(animator);
			
			AnimaAnimation[] animations = animatorComp.animations;
			
			if(animatorComp.isIdle()) {
				Objects.spriteBatch.draw(animations[animatorComp.current].getKeyFrame(0.1F), positionComp.x, positionComp.y);
			} else {
				Objects.spriteBatch.draw(animations[animatorComp.current].getKeyFrame(elapsedTime, true), positionComp.x, positionComp.y);
			}
			
		}

		Objects.spriteBatch.end();
		
		mapRenderer.render(Values.OVER_LAYERS);
		
	}

}
