package br.com.anima.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import br.com.anima.utils.Values;

public class Box2DDebugRendererSystem extends EntitySystem implements Initializable {
	
	private Box2DDebugRenderer renderer;
	
	@Override
	public void init() {
		renderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void update(float deltaTime) {
		renderer.render(Objects.world, Objects.camera.combined.cpy().scl(Values.PIXELS_PER_METER));
	}
	
}
