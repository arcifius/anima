package br.com.anima.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;

public class AnimatorComponent implements Component {

    public AnimationState state;
    public Skeleton skeleton;
    public String currentAnimation;

    public AnimatorComponent(String currentAnimation) {
        // TODO: move this outside
        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("spine/hero.atlas"));
        SkeletonJson json = new SkeletonJson(playerAtlas);
        SkeletonData playerSkeletonData = json.readSkeletonData(Gdx.files.internal("spine/hero.json"));
        AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);

        this.skeleton = new Skeleton(playerSkeletonData);
        this.state = new AnimationState(playerAnimationData);
        this.currentAnimation = currentAnimation;
    }

}
