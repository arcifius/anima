package br.com.anima.systems;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.MovementComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Values;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

public class MovementSystem extends EntitySystem implements Initializable {
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<MovementComponent> movementMapper;
    private final ComponentMapper<AnimatorComponent> animatortMapper;

    public MovementSystem() {
        this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
        this.movementMapper = ComponentMapper.getFor(MovementComponent.class);
        this.animatortMapper = ComponentMapper.getFor(AnimatorComponent.class);
    }

    @Override
    public void init() {
        // Nothing yet
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        this.entities = getEngine().getEntitiesFor(
                Family.all(PositionComponent.class, MovementComponent.class, AnimatorComponent.class).get()
        );
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.entities = null;
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : this.entities) {
            PositionComponent position = this.positionMapper.get(entity);
            MovementComponent movement = this.movementMapper.get(entity);
            AnimatorComponent animator = this.animatortMapper.get(entity);

            float speed = (movement.speed * deltaTime);

            if (position.isAt(movement.x, movement.y)) {
                continue;
            }

            position.x += position.x < (movement.x * Values.TILE_SIZE) ? speed : -speed;
            position.y += position.y < (movement.y * Values.TILE_SIZE) ? speed : -speed;

            if (position.isAt(movement.x, movement.y, speed)) {
                position.x = movement.x * Values.TILE_SIZE;
                position.y = movement.y * Values.TILE_SIZE;

                // TODO: after walking each tile idle will be quickly called and walking continuously will be weird
                animator.currentAnimation = "idle";
            } else {
                animator.currentAnimation = "walk";
            }
        }
    }
}
