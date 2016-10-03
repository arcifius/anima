package br.com.anima.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

//Rate example: 1 / 15f

public class AnimationUtils {

	public static TextureAtlas loadAtlas(String atlasName) {
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("gameSprites/" + atlasName + ".atlas"));
		return textureAtlas;
	}

	public static AnimaAnimation createUsingRegions(float rate, String animationName, TextureAtlas textureAtlas, Array<String> regionNames) {

		Array<AtlasRegion> regions = new Array<AtlasRegion>();

		for (String regionName : regionNames) {
			AtlasRegion region = textureAtlas.findRegion(regionName);

			if (region != null) {
				regions.add(region);
			} else {
				System.err.println("Region with name " + regionName + " not found!");
			}
		}

		if (regions.size > 0) {
			AnimaAnimation animation = new AnimaAnimation(animationName, rate, regions);			
			return animation;			
		} else {
			return null;
		}
	}

	public static AnimaAnimation createUsingAtlas(String animationName, float rate, String atlasName) {
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("gameSprites/" + atlasName + ".atlas"));
		AnimaAnimation animation = new AnimaAnimation(animationName, rate, textureAtlas.getRegions());
		return animation;
	}

}
