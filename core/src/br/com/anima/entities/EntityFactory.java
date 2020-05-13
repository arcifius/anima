package br.com.anima.entities;

import br.com.anima.components.*;
import br.com.anima.types.Maps;
import br.com.anima.types.Monsters;
import br.com.anima.utils.Box2DUtils;
import br.com.anima.utils.Orientation;
import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.Map;

public final class EntityFactory {

    public static Entity createPlayer(int x, int y, float scale, BodyDef.BodyType bodyType) {
        Entity e = new Entity();

        AnimatorComponent animator = new AnimatorComponent("idle");
        animator.skeleton.setScale(scale, scale);

        e.add(animator);
        e.add(new VelocityComponent(0F, 0F));
        e.add(new FollowComponent());
        e.add(new ControlledComponent());
        e.add(new PositionComponent(x * Values.TILE_SIZE, y * Values.TILE_SIZE));
        e.add(new MovementComponent(x, y));

        return e;
    }

    public static Entity createMap(Maps map) {
        Entity e = new Entity();

        MapComponent mapper = new MapComponent();
        mapper.map = map;
        mapper.adjacent = Map.of(
                Orientation.UP, Maps.MAPA,
                Orientation.DOWN, Maps.MAPA,
                Orientation.LEFT, Maps.MAPA,
                Orientation.RIGHT, Maps.MAPA,
                Orientation.UP_LEFT, Maps.MAPA,
                Orientation.UP_RIGHT, Maps.MAPA,
                Orientation.DOWN_LEFT, Maps.MAPA,
                Orientation.DOWN_RIGHT, Maps.MAPA
        );

        e.add(mapper);

        return e;
    }

    public static Entity createEnemy(Monsters enemyType, float x, float y, float scale, BodyDef.BodyType bodyType) {
        Entity e = new Entity();

        switch (enemyType) {
            case TROLL:
                Texture texture = new Texture(Gdx.files.internal("gameSprites/viking.png"));
                Sprite sprite = new Sprite(texture);

                sprite.setPosition(x, y);

                e.add(new VelocityComponent(0F, 0F));
                e.add(new SpriteComponent(sprite));
                break;

            case AIRPLANE:
                AnimatorComponent animatorComponent = new AnimatorComponent("idle");
                animatorComponent.skeleton.setScale(scale, scale);

                e.add(animatorComponent);
                e.add(new PositionComponent(x, y));
                break;

            default:
                return null;
        }

        return e;
    }

}
