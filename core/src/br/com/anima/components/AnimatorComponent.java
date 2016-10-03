package br.com.anima.components;

import java.awt.Dimension;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.anima.utils.AnimaAnimation;

public class AnimatorComponent implements Component {

	public AnimaAnimation[] animations;
	public int current;
	private boolean idle;
	
	public boolean isIdle() {
		return idle;
	}
	
	public void setIdle(boolean value) {
		this.idle = value;
	}

	public AnimatorComponent(AnimaAnimation... animations) {

		this.animations = animations;
		this.current = 0;

	}

	public Dimension getDimension() {
		TextureRegion t = this.animations[this.current].getKeyFrame(0);

		return new Dimension(t.getRegionWidth(), t.getRegionHeight());
	}

}
