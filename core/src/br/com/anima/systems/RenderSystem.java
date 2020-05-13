package br.com.anima.systems;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.MapComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.spine.SkeletonRenderer;

public class RenderSystem extends EntitySystem implements Initializable {
    private ImmutableArray<Entity> entities, animators, maps;
    private SkeletonRenderer skeletonRenderer;

    private final ComponentMapper<AnimatorComponent> animatorMapper;
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;
    private final ComponentMapper<MapComponent> mapMapper;
    private final OrthographicCamera camera;

    public RenderSystem(OrthographicCamera camera) {
        this.spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        this.animatorMapper = ComponentMapper.getFor(AnimatorComponent.class);
        this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
        this.mapMapper = ComponentMapper.getFor(MapComponent.class);
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
        this.animators = engine.getEntitiesFor(Family.all(AnimatorComponent.class).get());
        this.maps = engine.getEntitiesFor(Family.all(MapComponent.class).get());
    }

    @Override
    public void init() {
        this.skeletonRenderer = new SkeletonRenderer();

        // TODO: Move this to another system.
        // TiledMapTileLayer collision = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("collision");
        // Box2DUtils.detectCollision(mapRenderer.getMap().getLayers().get("collision"));
    }

    @Override
    public void update(float deltaTime) {
        this.renderMaps();
        this.renderEntities();
    }

    public void renderMaps() {
        for (Entity map : this.maps) {
            MapComponent mapComponent = this.mapMapper.get(map);
            if (mapComponent.renderer != null) {
                mapComponent.renderer.setView(this.camera);
                mapComponent.renderer.render();
            }
        }
    }

    public void renderEntities() {
        float delta = Gdx.graphics.getDeltaTime();

        Objects.spriteBatch.begin();

        for (Entity sprite : entities) {
            SpriteComponent spriteComp = spriteMapper.get(sprite);
            spriteComp.sprite.draw(Objects.spriteBatch);
        }

        for (Entity animator : animators) {
            AnimatorComponent animatorComp = animatorMapper.get(animator);
            PositionComponent positionComp = positionMapper.get(animator);

            // TODO: move this to another system
            animatorComp.state.update(delta);
            animatorComp.state.apply(animatorComp.skeleton);
            if (this.shouldUpdateAnimation(animatorComp)) {
                animatorComp.state.setAnimation(0, animatorComp.currentAnimation, true);
            }

            // TODO: move this to another system
            animatorComp.skeleton.setPosition(positionComp.x + 20, positionComp.y - 5);
            animatorComp.skeleton.updateWorldTransform();

            skeletonRenderer.draw(Objects.spriteBatch, animatorComp.skeleton);
        }

        Objects.spriteBatch.end();
    }

    private boolean shouldUpdateAnimation(AnimatorComponent component) {
        // TODO: change this logic to:
        // Animation jumpAnimation = skeletonData.findAnimation("jump");
        // ...
        // if (skeleAnim.state.GetCurrent(0) != jumpAnimation) { ... }

        if (component.state.getCurrent(0) == null) {
            return true;
        }

        return !component.state.getCurrent(0).toString().equals(component.currentAnimation);
    }

}
