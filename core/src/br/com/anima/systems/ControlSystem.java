package br.com.anima.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.Box2DComponent;
import br.com.anima.components.ControlledComponent;
import br.com.anima.components.FollowComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import br.com.anima.utils.Values;

public class ControlSystem extends EntitySystem implements InputProcessor, Initializable {

	private ImmutableArray<Entity> entities;

	private ComponentMapper<Box2DComponent> bodyMapper = ComponentMapper.getFor(Box2DComponent.class);
	private ComponentMapper<AnimatorComponent> animatorMapper = ComponentMapper.getFor(AnimatorComponent.class);

	public boolean leftPressed = false;
	public boolean rightPressed = false;
	public boolean upPressed = false;
	public boolean downPressed = false;
	
	@Override
	public void init() {
		Gdx.input.setInputProcessor(this);		
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.entities = getEngine().getEntitiesFor(Family.all(ControlledComponent.class, Box2DComponent.class, AnimatorComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		this.entities = null;
	}

	@Override
	public void update(float deltaTime) {
		// System.out.println("ctu");
		if (this.entities.size() > 0) {
			Box2DComponent box2D = bodyMapper.get(this.entities.get(0));
			AnimatorComponent animatorComp = animatorMapper.get(this.entities.get(0));

			Vector2 velocity = new Vector2();			
			
			if(!this.leftPressed && !this.rightPressed && !this.upPressed && !this.downPressed) {				
				animatorComp.setIdle(true);
			} else {				
				animatorComp.setIdle(false);
				
				if (this.leftPressed) {
					velocity.x -= 500 * deltaTime;
					animatorComp.current = 2;
				}

				if (this.rightPressed) {
					velocity.x += 500 * deltaTime;
					animatorComp.current = 3;
				}

				if (this.upPressed) {
					velocity.y += 500 * deltaTime;
					animatorComp.current = 1;
				}

				if (this.downPressed) {
					velocity.y -= 500 * deltaTime;
					animatorComp.current = 0;
				}
			}

			box2D.body.setLinearVelocity(velocity);			

		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.A) {
			this.leftPressed = true;
		} else if (keycode == Input.Keys.D) {
			this.rightPressed = true;
		}
		
		if (keycode == Input.Keys.W) {
			this.upPressed = true;
		} else if (keycode == Input.Keys.S) {
			this.downPressed = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.A) {
			this.leftPressed = false;
		} else if (keycode == Input.Keys.D) {
			this.rightPressed = false;
		}
		
		if (keycode == Input.Keys.W) {
			this.upPressed = false;
		} else if (keycode == Input.Keys.S) {
			this.downPressed = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {		
		return false;
	}

}
