package br.com.anima.utils;

public class MapSize {

	public int map_size;
	public int tile_size;
	public int map_size_pixels;
	
	public void changeMapDimensions(int map_size, int tile_size) {		
		this.map_size = map_size;
		this.tile_size = tile_size;
		
		this.map_size_pixels = map_size * tile_size;
	}
	
}
