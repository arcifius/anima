package br.com.anima.systems;

import java.awt.Dimension;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.Box2DComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Meters;

public class UpdateBox2DSystem extends EntitySystem implements Initializable {

	private ImmutableArray<Entity> staticEntities;
	private ImmutableArray<Entity> animatedEntities;

	private ComponentMapper<SpriteComponent> visMapper = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<Box2DComponent> boxMapper = ComponentMapper.getFor(Box2DComponent.class);
	private ComponentMapper<AnimatorComponent> animatorMapper = ComponentMapper.getFor(AnimatorComponent.class);
	private ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);

	@Override
	public void init() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		this.staticEntities = getEngine().getEntitiesFor(Family.all(SpriteComponent.class, Box2DComponent.class).get());
		this.animatedEntities = getEngine()
				.getEntitiesFor(Family.all(AnimatorComponent.class, Box2DComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {

		for (int i = 0; i < staticEntities.size(); i++) {

			SpriteComponent visual = visMapper.get(this.staticEntities.get(i));
			Box2DComponent box2d = boxMapper.get(this.staticEntities.get(i));

			Vector2 bodyPosition = box2d.body.getPosition();

			float newX = Meters.toPixels(bodyPosition.x) - visual.sprite.getWidth() / 2;
			float newY = Meters.toPixels(bodyPosition.y) - visual.sprite.getHeight() / 2;

			visual.sprite.setPosition(newX, newY);

		}

		for (int i = 0; i < animatedEntities.size(); i++) {

			AnimatorComponent animator = animatorMapper.get(this.animatedEntities.get(i));
			Box2DComponent box2d = boxMapper.get(this.animatedEntities.get(i));
			PositionComponent positionComp = posMapper.get(this.animatedEntities.get(i));

			Vector2 bodyPosition = box2d.body.getPosition();
			
			Dimension d = animator.getDimension();

			float newX = Meters.toPixels(bodyPosition.x) - d.width / 2;
			float newY = Meters.toPixels(bodyPosition.y) - d.height / 2;

			positionComp.x = newX;
			positionComp.y = newY;

		}

	}

}
