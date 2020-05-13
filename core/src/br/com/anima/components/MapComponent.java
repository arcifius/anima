package br.com.anima.components;

import br.com.anima.types.Maps;
import br.com.anima.utils.Orientation;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.MapRenderer;

import java.util.Map;

public class MapComponent implements Component {

    public MapRenderer renderer;
    public Maps map;
    public boolean active;
    public Map<Orientation, Maps> adjacent;

}
