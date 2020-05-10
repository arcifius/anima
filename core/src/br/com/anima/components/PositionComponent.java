package br.com.anima.components;

import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public float x;
    public float y;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAt(int x, int y)
    {
        return this.isAt(x, y, Float.MIN_NORMAL);
    }

    public boolean isAt(int x, int y, float epsilon)
    {
        float mx = (x * Values.TILE_SIZE);
        float my = (y * Values.TILE_SIZE);
        return Math.abs(this.x - mx) < epsilon && Math.abs(this.y - my) < epsilon;
    }
}
