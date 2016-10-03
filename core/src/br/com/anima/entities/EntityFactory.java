package br.com.anima.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.Box2DComponent;
import br.com.anima.components.ControlledComponent;
import br.com.anima.components.FollowComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.components.VelocityComponent;
import br.com.anima.utils.AnimaAnimation;
import br.com.anima.utils.AnimationUtils;
import br.com.anima.utils.Box2DUtils;
import br.com.anima.utils.Monster;

public final class EntityFactory {

	public static Entity createPlayer(float x, float y, BodyDef.BodyType bodyType) {
		Entity e = new Entity();
		
		TextureAtlas persona = AnimationUtils.loadAtlas("persona1");
		
		String[] walkUp    = {"walk-up-0", "walk-up-1", "walk-up-2"};
		String[] walkDown  = {"walk-down-0", "walk-down-1", "walk-down-2"};
		String[] walkLeft  = {"walk-left-0", "walk-left-1", "walk-left-2"};
		String[] walkRight = {"walk-right-0", "walk-right-1", "walk-right-2"};
		
		Array<String> up = new Array<>(walkUp);		
		AnimaAnimation upAnim = AnimationUtils.createUsingRegions(1/10f, "walk_up", persona, up);
		
		Array<String> down = new Array<>(walkDown);		
		AnimaAnimation downAnim = AnimationUtils.createUsingRegions(1/10f, "walk_down", persona, down);
		
		Array<String> left = new Array<>(walkLeft);		
		AnimaAnimation leftAnim = AnimationUtils.createUsingRegions(1/10f, "walk_left", persona, left);
		
		Array<String> right = new Array<>(walkRight);		
		AnimaAnimation rightAnim = AnimationUtils.createUsingRegions(1/10f, "walk_right", persona, right);
		
		AnimatorComponent animatorComponent = new AnimatorComponent(downAnim, upAnim, leftAnim, rightAnim);
		
		e.add(animatorComponent);		
		e.add(new VelocityComponent(0F, 0F));		
		e.add(Box2DUtils.create(x, y, animatorComponent.getDimension().width, animatorComponent.getDimension().height, bodyType));
		e.add(new FollowComponent());
		e.add(new ControlledComponent());
		e.add(new PositionComponent(x, y));

		return e;
	}

	public static Entity createEnemy(Monster enemyType, float x, float y, BodyDef.BodyType bodyType) {
		Entity e = new Entity();
		
		switch (enemyType) {
		case TROLL:
			Texture texture = new Texture(Gdx.files.internal("gameSprites/viking.png"));
			Sprite sprite = new Sprite(texture);			
							
			sprite.setPosition(x, y);
			
			e.add(new VelocityComponent(0F, 0F));
			e.add(new SpriteComponent(sprite));
			e.add(Box2DUtils.create(sprite, bodyType));
			break;

		case AIRPLANE:
			
			TextureAtlas airplane = AnimationUtils.loadAtlas("spritesheet");
			
			String[] descSpinUp = {"0001", "0002", "0003", "0004", "0005",
								    "0006", "0007", "0008", "0009", "0010"};
			Array<String> spinUp = new Array<>(descSpinUp);		
			AnimaAnimation upSpin = AnimationUtils.createUsingRegions(1/15f, "rolling up", airplane, spinUp);
			
			String[] descSpinDown = {"0011", "0012", "0013", "0014", "0015",
					  "0016", "0017", "0018", "0019", "0020"};
			Array<String> spinDown = new Array<>(descSpinDown);		
			AnimaAnimation downSpin = AnimationUtils.createUsingRegions(1/15f, "rolling down", airplane, spinDown);
			
			AnimatorComponent animatorComponent = new AnimatorComponent(upSpin, downSpin);
			
			e.add(animatorComponent);
			e.add(new VelocityComponent(0F, 0F));
			e.add(Box2DUtils.create(x, y, animatorComponent.getDimension().width, animatorComponent.getDimension().height, bodyType));
			e.add(new PositionComponent(x, y));

			break;

		default:
			return null;
		}
		
		

		

		return e;
	}

}
