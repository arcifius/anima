package br.com.anima.systems;

import java.awt.Dimension;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.FollowComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;

public class CameraSystem extends EntitySystem implements Initializable {
		
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<AnimatorComponent> animMapper = ComponentMapper.getFor(AnimatorComponent.class);
	
	@Override
	public void init() {	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.entities = getEngine().getEntitiesFor(Family.all(
				FollowComponent.class,
				AnimatorComponent.class,
				PositionComponent.class
			).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		this.entities = null;
	}

	@Override
	public void update(float deltaTime) {
		//System.out.println("cmu");
		if (this.entities.size() > 0) {
			PositionComponent target = posMapper.get(this.entities.get(0));			
			AnimatorComponent animComp = animMapper.get(this.entities.get(0));
			
			Dimension dimension = animComp.getDimension();
						
			float halfWidth  = Objects.camera.viewportWidth * 0.5f;
			float halfHeight = Objects.camera.viewportHeight * 0.5f;
			float mapSize = Objects.mapsize.map_size_pixels;
			float centerX = target.x + (dimension.width / 2);
			float centerY = target.y + (dimension.height / 2);						
			
			Objects.camera.position.x = MathUtils.clamp(centerX, halfWidth, mapSize - halfWidth);
			Objects.camera.position.y = MathUtils.clamp(centerY, halfHeight, mapSize - halfHeight);			
		}
		
		Objects.camera.update();
	}

}
