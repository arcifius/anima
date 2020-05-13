package br.com.anima.systems;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.Box2DComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

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

            //float newX = Meters.toPixels(bodyPosition.x) - visual.sprite.getWidth() / 2;
            //float newY = Meters.toPixels(bodyPosition.y) - visual.sprite.getHeight() / 2;

            visual.sprite.setPosition(0, 0);
        }

        for (int i = 0; i < animatedEntities.size(); i++) {
            Box2DComponent box2d = boxMapper.get(this.animatedEntities.get(i));
            PositionComponent positionComp = posMapper.get(this.animatedEntities.get(i));
            AnimatorComponent animator = animatorMapper.get(this.animatedEntities.get(i));
            box2d.body.getPosition().set(positionComp.x, positionComp.y);
        }

    }

}
