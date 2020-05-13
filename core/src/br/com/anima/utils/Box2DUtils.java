package br.com.anima.utils;

import br.com.anima.components.Box2DComponent;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

@Deprecated
public class Box2DUtils {
    private static final Body createBody(float x, float y, float width, float height, BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;

        return null;
    }

    private static final Fixture createFixture(Body body, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();

        return fixture;
    }

    public static final Box2DComponent create(Sprite sprite, BodyType bodyType) {
        Box2DComponent box2DComponent = new Box2DComponent();

        Body body = createBody(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), bodyType);

        PolygonShape shape = new PolygonShape();

        Fixture fixture = createFixture(body, shape);

        box2DComponent.body = body;
        box2DComponent.fixture = fixture;

        // System.out.println("Box: " + Pixels.toMeters(sprite.getScaleX() *
        // sprite.getWidth() / 2) + " & " + Pixels.toMeters(sprite.getScaleY() *
        // sprite.getHeight() / 2));

        return box2DComponent;
    }

    public static final Box2DComponent create(float x, float y, int width, int height, BodyType bodyType) {
        return null;
    }

    public static final Box2DComponent createCircle(Sprite sprite) {

        Box2DComponent box2DComponent = new Box2DComponent();

        Body body = createBody(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(),
                BodyType.StaticBody);

        CircleShape circle = new CircleShape();

        Fixture fixture = createFixture(body, circle);

        box2DComponent.body = body;
        box2DComponent.fixture = fixture;

        return box2DComponent;
    }

    public static ImmutableArray<Body> detectCollision(MapLayer collisionLayer) {

        Array<Body> bodies = new Array<Body>();
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) collisionLayer;

        System.out.println(collisionLayer != null ? "Collision Layer loaded." : "Can't access collision layer!");

        /* Adding collision for marked tiles */
        for (int x = 0; x < tiledLayer.getWidth(); x++) {

            for (int y = 0; y < tiledLayer.getHeight(); y++) {

                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);

                if (cell != null && cell.getTile().getProperties().containsKey("solid")) {

                    PolygonShape polygon = new PolygonShape();
                    //polygon.setAsBox(Pixels.toMeters(Values.TILE_SIZE / 2), Pixels.toMeters(Values.TILE_SIZE / 2));

                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyType.StaticBody;
                    //bodyDef.position.set(Pixels.toMeters(new Vector2((x * Values.TILE_SIZE) + Values.TILE_SIZE / 2,
                    //		(y * Values.TILE_SIZE) + Values.TILE_SIZE / 2)));

                    //Body body = Objects.world.createBody(bodyDef);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.friction = 0;
                    fixtureDef.density = 1;
                    fixtureDef.shape = polygon;
                    //body.createFixture(fixtureDef);

                    // bodies.add(body);

                    polygon.dispose();

                }

            }

        }

        /* NOT TESTED YET, BUT SHOULD WORK GREAT WITH OBJECTS TOO */
        for (MapObject object : collisionLayer.getObjects()) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                PolygonShape polygon = new PolygonShape();
                // polygon.setAsBox(Pixels.toMeters(rect.width / 2), Pixels.toMeters(rect.height / 2));

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.StaticBody;
                // bodyDef.position.set(Pixels.toMeters(new Vector2(rect.x + rect.width / 2, rect.y + rect.height / 2)));

                // Body body = Objects.world.createBody(bodyDef);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.friction = 0;
                fixtureDef.density = 1;
                fixtureDef.shape = polygon;
                // body.createFixture(fixtureDef);

                // bodies.add(body);

                polygon.dispose();

                continue;
            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
            } else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject) object);
            } else {
                continue;
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.StaticBody;

            // Body body = Objects.world.createBody(bodyDef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.friction = 0;
            fixtureDef.density = 1;
            fixtureDef.shape = shape;
            // body.createFixture(fixtureDef);

            // bodies.add(body);

            shape.dispose();

        }

        return new ImmutableArray<Body>(bodies);
    }

    private static Shape getPolyline(PolylineMapObject object) {
        float[] vertices = object.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            Vector2 vector = new Vector2();
            vector.x = vertices[i * 2];
            vector.y = vertices[i * 2 + 1];

            // worldVertices[i] = Pixels.toMeters(new Vector2(vector));
        }

        ChainShape shape = new ChainShape();
        shape.createChain(worldVertices);
        return shape;
    }

    private static PolygonShape getPolygon(PolygonMapObject object) {
        PolygonShape shape = new PolygonShape();
        float[] vertices = object.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            // worldVertices[i] = Pixels.toMeters(vertices[i]);
        }

        shape.set(worldVertices);

        return shape;
    }

    public static void destroyBody(Body body) {
        Box2DComponent box2DComponent = new Box2DComponent();
        box2DComponent.body = body;
        destroyBody(box2DComponent);
    }

    public static void destroyBody(Box2DComponent box2DComponent) {
        if (box2DComponent == null) {
            return;
        }

        Body bodyToDestroy = box2DComponent.body;
        Fixture fixture = box2DComponent.fixture;

        if (fixture != null) {
            fixture.setUserData(null);
        }

        Array<Body> bodies = new Array<Body>();
        // Objects.world.getBodies(bodies);

        if (bodies.size == 0) {
        }

        for (Body body : bodies) {
            if (body == bodyToDestroy) {
                // Objects.world.destroyBody(body);
                return;
            }
        }
    }
}
