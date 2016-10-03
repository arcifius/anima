package br.com.anima.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapComponent implements Component {

	public TiledMap tiledMap;
	public TiledMapRenderer renderer;
	
	public TiledMapComponent(TiledMap tiledMap) {
		this.tiledMap = tiledMap;		
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
}
