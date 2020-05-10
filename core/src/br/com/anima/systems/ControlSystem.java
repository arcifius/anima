package br.com.anima.systems;

import br.com.anima.components.ControlledComponent;
import br.com.anima.components.MovementComponent;
import br.com.anima.components.PositionComponent;
import br.com.anima.interfaces.Initializable;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class ControlSystem extends EntitySystem implements InputProcessor, Initializable {
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<MovementComponent> movementMapper;

    public boolean rightPressed = false;
    public boolean leftPressed = false;
    public boolean downPressed = false;
    public boolean upPressed = false;

    public ControlSystem() {
        this.positionMapper = ComponentMapper.getFor(PositionComponent.class);
        this.movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void init() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        this.entities = getEngine().getEntitiesFor(
                Family.all(
                        ControlledComponent.class,
                        PositionComponent.class,
                        MovementComponent.class
                ).get()
        );
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.entities = null;
    }

    @Override
    public void update(float deltaTime) {
        if (this.entities.size() > 0) {
            PositionComponent position = positionMapper.get(this.entities.first());
            MovementComponent movement = movementMapper.get(this.entities.first());

            if (position.isAt(movement.x, movement.y)) {
                if (this.leftPressed) {
                    movement.x--;
                }

                if (this.rightPressed) {
                    movement.x++;
                }

                if (this.upPressed) {
                    movement.y++;
                }

                if (this.downPressed) {
                    movement.y--;
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            this.leftPressed = true;
        } else if (keycode == Input.Keys.D) {
            this.rightPressed = true;
        }

        if (keycode == Input.Keys.W) {
            this.upPressed = true;
        } else if (keycode == Input.Keys.S) {
            this.downPressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) {
            this.leftPressed = false;
        } else if (keycode == Input.Keys.D) {
            this.rightPressed = false;
        }

        if (keycode == Input.Keys.W) {
            this.upPressed = false;
        } else if (keycode == Input.Keys.S) {
            this.downPressed = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
