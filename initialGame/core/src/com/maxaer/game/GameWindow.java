package com.maxaer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.maxaer.screens.GameScreen;

public class GameWindow extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Sprite sprite;
    Sprite block;
    Sprite platform;
    Texture blockImg;
    Texture platImg;
    Texture img;
    World world;
    Body body;
    Body blockBody;
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    BitmapFont font;


   // float torque = 0.0f;
    float torque = 0;
    boolean drawSprite = true;

    final float PIXELS_TO_METERS = 100f;
/*
 * Class: GameWindow, extends Game
 * Author: Peter Kaminski
 * Purpose: GameWindow extends the game class to create a standard ApplicationAdapter. It's primary focus is to serve as the entry point
 *          for our maxaer game. 
 *          1. a gamescreen is created and set as the primary screen for the game. 
 */
public class GameWindow extends Game{

    @Override
    public void create() {
       
       setScreen(new GameScreen());

        batch = new SpriteBatch();
        
        blockImg = new Texture("thinmint2.png");
        block = new Sprite(blockImg);
        block.setPosition(-200, 300);
        
        //add a block to the left of the screen
        BodyDef bb = new BodyDef();
        bb.type = BodyDef.BodyType.DynamicBody;
        bb.position.set((block.getX() + block.getWidth()/2) / PIXELS_TO_METERS,
                (block.getY() + block.getHeight()/2) / PIXELS_TO_METERS);
        world = new World(new Vector2(0, -11f),true);
        blockBody = world.createBody(bb);
        blockBody.setLinearVelocity(0f, -2f);
        blockBody.setGravityScale(0);
        
        
        PolygonShape bshape = new PolygonShape();
        bshape.setAsBox(block.getWidth()/2 / PIXELS_TO_METERS, block.getHeight()
                /2 / PIXELS_TO_METERS);

        FixtureDef fd = new FixtureDef();
        fd.shape = bshape;
        fd.density = 0;
        fd.restitution = 0f;
        

        blockBody.createFixture(fd);
        bshape.dispose();
        
        img = new Texture("player.png");
        sprite = new Sprite(img);

        sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);

        

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
        body = world.createBody(bodyDef);
        

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
                /2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
        
        platImg = new Texture("black.jpg");
        platform = new Sprite(platImg);
        platform.setPosition(-400, -250);		//hard code position
        
        
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set((platform.getX() + platform.getWidth()/2) / PIXELS_TO_METERS,
        (platform.getY() + platform.getHeight()/2) / PIXELS_TO_METERS);
        
        bodyEdgeScreen = world.createBody(bodyDef2);
        
        //creates the bottom platform
        FixtureDef fixtureDef2 = new FixtureDef();
        PolygonShape recShape = new PolygonShape();
        recShape.setAsBox(platform.getWidth()/2 / PIXELS_TO_METERS, 
        		platform.getHeight() /2 / PIXELS_TO_METERS);
       
        
        fixtureDef2.shape = recShape;
        
        bodyEdgeScreen.createFixture(fixtureDef2);
        recShape.dispose();

        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                getHeight());
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because
                if((contact.getFixtureA().getBody() == bodyEdgeScreen &&
                        contact.getFixtureB().getBody() == blockBody)
                        ||
                        (contact.getFixtureA().getBody() == blockBody &&
                                contact.getFixtureB().getBody() == bodyEdgeScreen)) {

                	blockBody.setLinearVelocity(0f, 0f);
                	System.out.println("hello");
                }
                
               
            }

            @Override
            public void endContact(Contact contact) {
            	 
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        
    }

    private float elapsed = 0;
    @Override
    public void render() {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        body.applyTorque(torque,true);
        body.getFixtureList().first().setRestitution(0);

        sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2 );
        
        block.setPosition((blockBody.getPosition().x * PIXELS_TO_METERS) - block.
                getWidth()/2 ,
        (blockBody.getPosition().y * PIXELS_TO_METERS) -block.getHeight()/2 );

        
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();

        if(drawSprite)
        {
            batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                    sprite.getOriginY(),
                    sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                            getScaleY(),sprite.getRotation());
            batch.draw(platform, platform.getX(), platform.getY(), platform.getOriginX(),
            		platform.getOriginY(), platform.getWidth(), platform.getHeight(), 
            		platform.getScaleX(), platform.getScaleY(), platform.getRotation());
            batch.draw(block, block.getX(), block.getY(), block.getOriginX(),
            		block.getOriginY(), block.getWidth(), block.getHeight(), 
            		block.getScaleX(), block.getScaleY(), block.getRotation());
        }
        
        batch.end();

        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {


        if(keycode == Input.Keys.RIGHT)
            body.applyForceToCenter(3f, 0f, true);
        if(keycode == Input.Keys.LEFT)
            body.applyForceToCenter(-3f,0f, true);

        if(keycode == Input.Keys.UP)
            body.applyForceToCenter(0f,10f,true);


        // If user hits spacebar, reset everything back to normal
        if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
            body.setLinearVelocity(0f, 0f);
            body.setAngularVelocity(0f);
            torque = 0f;
            sprite.setPosition(0f,0f);
            body.setTransform(0f,0f,0f);
        }
        if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1)
            drawSprite = !drawSprite;

        return true;
    }



    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


}