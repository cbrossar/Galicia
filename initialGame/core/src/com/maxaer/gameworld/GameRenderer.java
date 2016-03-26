package com.maxaer.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/*
 * Class: GameRender
 * Author: Peter Kaminski
 * Purpose: GameRenderer serves as the primary render for the game. 
 *          1. Game render contains the Orthographic camera
 *          2. Has a shape renderer to render the bodies
 *          3. Has a reference to the GameWorld to correctly render bodies
 *          
 */
public class GameRenderer
{
   private SpriteBatch batch;
   private GameWorld world;
   private OrthographicCamera camera;
   private Box2DDebugRenderer debug;
   private Matrix4 debugMatrix;
   
   public GameRenderer(GameWorld world){
      //Create the reference to the game world
      this.world = world;
      //Init the batch and the camera
      batch = new SpriteBatch();
      
      camera = new OrthographicCamera();
      //Set the dimensions of the camera
      camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      
      
      batch.setProjectionMatrix(camera.combined);
      
      
      debug = new Box2DDebugRenderer();
      
   }
   
   /*
    * All rendering goes on here. Super important method
    */
   public void render(){
      
      
      // Step the physics simulation forward at a rate of 45hz, recommended by LibGDX
      world.getWorld().step(1/45f, 6, 2);
      
      //Clear the screen here
      Gdx.gl.glClearColor(1, 1, 1, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      
      //Access the world's reference to Player, and update the position of it's sprite with respect to the body
      world.getPlayer().updatePlayerPosition();
      
//      camera.position.x = world.getPlayerBody().getPosition().x ;
//      camera.position.y = world.getPlayerBody().getPosition().y ;
//      Gdx.app.log("X", world.getPlayerBody().getPosition().x + "");
//      Gdx.app.log("Y", world.getPlayerBody().getPosition().y + "");
//      Gdx.app.log("xcam", camera.position.x + "");
//      Gdx.app.log("ycam", camera.position.y + "");
      //Update the camera here
      camera.update();
      
      //Begin batching sprites here. This will include blocks and the player
      batch.begin();
      

      debugMatrix = batch.getProjectionMatrix().cpy().scale(100f,
            100f, 0);
      //Render the Player sprite here
      batch.draw(world.getPlayerSprite(), world.getPlayerSprite().getX(), world.getPlayerSprite().getY(),world.getPlayerSprite().getOriginX(),
            world.getPlayerSprite().getOriginY(),
            world.getPlayerSprite().getWidth(),world.getPlayerSprite().getHeight(),world.getPlayerSprite().getScaleX(),world.getPlayerSprite().
                    getScaleY(),world.getPlayerSprite().getRotation());
      
      
      batch.end();
      

      debug.render(world.getWorld(), debugMatrix);
      
   
   }

}
