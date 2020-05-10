package br.com.anima.systems;

import br.com.anima.components.AnimatorComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.components.SpriteComponent;
import br.com.anima.interfaces.Initializable;
import br.com.anima.utils.Objects;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.esotericsoftware.spine.SkeletonRenderer;

public class RenderSystem extends EntitySystem implements Initializable {
    private ImmutableArray<Entity> entities, animators;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SkeletonRenderer skeletonRenderer;

    private final ComponentMapper<AnimatorComponent> animatorMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;
    private final ComponentMapper<PositionComponent> posMapper;

    public RenderSystem() {
        this.spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        this.animatorMapper = ComponentMapper.getFor(AnimatorComponent.class);
        this.posMapper = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
        this.animators = engine.getEntitiesFor(Family.all(AnimatorComponent.class).get());
    }

    @Override
    public void init() {
        // TODO: change this logic render system shouldn't decide which map should be rendered.
        TiledMap map = Objects.assetManager.get("maps/debain.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
        this.skeletonRenderer = new SkeletonRenderer();

        // TODO: Think about collisions, better.
        // Box2DUtils.detectCollision(mapRenderer.getMap().getLayers().get("collision"));
    }

    @Override
    public void update(float deltaTime) {
        mapRenderer.setView(Objects.camera);
        mapRenderer.render();
        this.renderEntities();
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
            PositionComponent positionComp = posMapper.get(animator);

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
