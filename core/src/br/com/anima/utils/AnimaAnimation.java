package br.com.anima.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimaAnimation extends Animation {

	private String name;
	
	public AnimaAnimation(String name, float frameDuration, Array<? extends TextureRegion> keyFrames) {
		super(frameDuration, keyFrames);	
		this.name = name;
	}	

	public AnimaAnimation (String name, float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
		super(frameDuration, keyFrames, playMode);
		this.name = name;
	}
	
	public AnimaAnimation(String name, float frameDuration, TextureRegion ... keyFrames) {
		super(frameDuration, keyFrames);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
