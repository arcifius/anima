package br.com.anima.components;

import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
    public int x;
    public int y;
    public float speed = 32;

    public MovementComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getScreenX()
    {
        return this.x * Values.TILE_SIZE;
    }

    public float getScreenY()
    {
        return this.y * Values.TILE_SIZE;
    }
}
