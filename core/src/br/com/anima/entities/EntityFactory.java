package br.com.anima.entities;

import br.com.anima.components.*;
import br.com.anima.utils.Box2DUtils;
import br.com.anima.utils.Monster;
import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;

public final class EntityFactory {

    public static Entity createPlayer(int x, int y, float scale, BodyDef.BodyType bodyType) {
        Entity e = new Entity();

        AnimatorComponent animator = new AnimatorComponent("idle");
        animator.skeleton.setScale(scale, scale);

        e.add(animator);
        e.add(new VelocityComponent(0F, 0F));
        e.add(Box2DUtils.create(x * Values.TILE_SIZE, y * Values.TILE_SIZE, 32, 64, bodyType));
        e.add(new FollowComponent());
        e.add(new ControlledComponent());
        e.add(new PositionComponent(x * Values.TILE_SIZE, y * Values.TILE_SIZE));
        e.add(new MovementComponent(x, y));

        return e;
    }

    public static Entity createEnemy(Monster enemyType, float x, float y, float scale, BodyDef.BodyType bodyType) {
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
                AnimatorComponent animatorComponent = new AnimatorComponent("idle");
                animatorComponent.skeleton.setScale(scale, scale);

                e.add(animatorComponent);
                e.add(new VelocityComponent(0F, 0F));
                e.add(Box2DUtils.create(x, y, 32, 64, bodyType));
                e.add(new PositionComponent(x, y));

                break;

            default:
                return null;
        }


        return e;
    }

}
