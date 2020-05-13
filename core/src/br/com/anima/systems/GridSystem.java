package br.com.anima.systems;

import br.com.anima.components.PositionComponent;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;

public class GridSystem extends EntitySystem {
    private final ComponentMapper<PositionComponent> posMapper;
    private ImmutableArray<Entity> entities;

    public GridSystem() {
        this.posMapper = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    @Override
    public void update(float delta) {

    }
}
