package br.com.anima.views;

import br.com.anima.Igniter;
import br.com.anima.entities.EntityFactory;
import br.com.anima.interfaces.Createable;
import br.com.anima.interfaces.Initializable;
import br.com.anima.systems.ControlSystem;
import br.com.anima.systems.MapControllerSystem;
import br.com.anima.systems.MovementSystem;
import br.com.anima.systems.RenderSystem;
import br.com.anima.utils.Monster;
import br.com.anima.utils.Objects;
import br.com.anima.utils.Values;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;

import java.util.stream.Stream;

public class GameView extends AbstractLmlView {
    private Engine engine;

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
        Objects.camera = this.createCamera();
        Objects.world = new World(Values.WORLD_GRAVITY, false);

        // (31 / 225f) where 31 is the RGB
        Gdx.gl.glClearColor(0.1378f, 0.1378f, 0.1378f, 1);

        this.engine = new Engine();

        /* ADDING ENTITIES */
        this.engine.addEntity(EntityFactory.createPlayer(0, 0, 0.25F, BodyDef.BodyType.DynamicBody));
        this.engine.addEntity(EntityFactory.createEnemy(Monster.TROLL, 288, 0, 1F, BodyDef.BodyType.DynamicBody));
        this.engine.addEntity(EntityFactory.createEnemy(Monster.TROLL, 800, 0, 1F, BodyDef.BodyType.StaticBody));
        this.engine.addEntity(EntityFactory.createEnemy(Monster.AIRPLANE, 800, 480, 0.25F, BodyDef.BodyType.StaticBody));

        /* ADDING SYSTEMS */
        this.engine.addSystem(new ControlSystem());
        //this.engine.addSystem(new UpdateBox2DSystem());
        // this.engine.addSystem(new CameraSystem());
        this.engine.addSystem(new MapControllerSystem());
        this.engine.addSystem(new RenderSystem());
        //this.engine.addSystem(new Box2DDebugRendererSystem());
        this.engine.addSystem(new MovementSystem());

        /* RETRIEVING ALL ADDED SYSTEMS */
        ImmutableArray<EntitySystem> systems = this.engine.getSystems();

        // INITIALIZING SYSTEMS
        Stream.of(systems)
                .filter(system -> system instanceof Initializable)
                .forEach(system -> ((Initializable) system).init());

        // CREATING SYSTEMS
        for (int i = 0; i < systems.size(); i++) {
            if (systems.get(i) instanceof Createable) {
                Createable create = (Createable) systems.get(i);
                create.create();
            }
        }
    }

    private OrthographicCamera createCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 32, h / 32);
        camera.zoom = Values.CAMERA_ZOOM;
        camera.update();

        return camera;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Objects.world.step(delta, 6, 2);

        this.engine.update(delta);
    }
}
