package br.com.anima.systems;

import br.com.anima.components.FollowComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.interfaces.Initializable;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraSystem extends EntitySystem implements Initializable {
    private final ComponentMapper<PositionComponent> positionMapper;
    private ImmutableArray<Entity> entities;
    private final OrthographicCamera camera;

    public CameraSystem(OrthographicCamera camera) {
        this.camera = camera;
        this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public void init() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.entities = getEngine().getEntitiesFor(Family.all(
                FollowComponent.class,
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
            PositionComponent target = this.positionMapper.get(this.entities.first());

            float halfWidth = this.camera.viewportWidth * 0.5f;
            float halfHeight = this.camera.viewportHeight * 0.5f;
            float mapSize = 100F;
            float centerX = target.x * 32;
            float centerY = target.y * 32;

            this.camera.position.x = MathUtils.clamp(centerX, halfWidth, mapSize - halfWidth);
            this.camera.position.y = MathUtils.clamp(centerY, halfHeight, mapSize - halfHeight);
        }

        this.camera.update();
    }

}
