package br.com.anima.views;

import br.com.anima.Igniter;
import br.com.anima.entities.EntityFactory;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.systems.*;
import br.com.anima.types.Maps;
import br.com.anima.types.Monsters;
import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GameView extends AbstractLmlView {
    private Engine engine;
    private OrthographicCamera camera;

    public GameView() {
        super(Igniter.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/gameUi.lml");
    }

    @Override
    public String getViewId() {
        return "game";
    }

    @Override
    public void show() {
        this.camera = this.createCamera();
        this.engine = new Engine();

        // (31 / 225f) where 31 is the RGB
        Gdx.gl.glClearColor(0.1378f, 0.1378f, 0.1378f, 1);

        // Adding entities
        List<Entity> entities = this.getInitialEntities();
        entities.forEach(entity -> this.engine.addEntity(entity));

        // Adding systems
        this.addSystems();

        // Bootstrapping systems
        this.engine.getSystems().forEach(system -> {
            if (system instanceof Initializable) {
                ((Initializable) system).init();
            }

            if (system instanceof Createable) {
                ((Createable) system).create();
            }
        });
    }

    private List<Entity> getInitialEntities() {
        return new LinkedList<>(List.of(
                requireNonNull(EntityFactory.createMap(Maps.DEBAIN)),
                requireNonNull(EntityFactory.createPlayer(0, 0, 0.25F, BodyDef.BodyType.DynamicBody)),
                requireNonNull(EntityFactory.createEnemy(Monsters.TROLL, 288, 0, 1F, BodyDef.BodyType.DynamicBody)),
                requireNonNull(EntityFactory.createEnemy(Monsters.TROLL, 800, 0, 1F, BodyDef.BodyType.StaticBody)),
                requireNonNull(EntityFactory.createEnemy(Monsters.AIRPLANE, 800, 480, 0.25F, BodyDef.BodyType.StaticBody))
        ));
    }

    private void addSystems() {
        this.engine.addSystem(new ControlSystem());
        this.engine.addSystem(new MapControllerSystem());
        this.engine.addSystem(new RenderSystem(this.camera));
        this.engine.addSystem(new MovementSystem());
        this.engine.addSystem(new CameraSystem(this.camera));
        //this.engine.addSystem(new UpdateBox2DSystem());
        //this.engine.addSystem(new Box2DDebugRendererSystem());
    }

    private OrthographicCamera createCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, w / Values.TILE_SIZE, h / Values.TILE_SIZE);
        camera.zoom = Values.CAMERA_ZOOM;
        camera.update();

        return camera;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.engine.update(delta);
    }
}
