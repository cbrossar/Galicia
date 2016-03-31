package com.maxaer.gameworld;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.maxaer.constants.GameConstants;
import com.maxaer.gameobjects.Block;

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
   private BitmapFont font;
   private int score = 0;
   private Vector<Block> blocks;
   
   public GameRenderer(GameWorld world){
      //Create the reference to the game world
      this.world = world;
      //Init the batch and the camera
      batch = new SpriteBatch();
      
      camera = new OrthographicCamera();
      //Set the dimensions of the camera
      camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      
      
      batch.setProjectionMatrix(camera.combined);
      
      font = new BitmapFont(true);
          
      font.setColor(Color.SALMON);
      
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
      
      //If the player has moved off the screen on the left, move him to the right
      if(world.getPlayerSprite().getX() + world.getPlayerSprite().getWidth() < 0){
         
         world.getPlayerBody().setTransform(new Vector2((Gdx.graphics.getWidth() - 1)/GameConstants.PIXEL_TO_METERS, world.getPlayerBody().getPosition().y), 0);
      
      } else if(world.getPlayerSprite().getX() > Gdx.graphics.getWidth()){
        //If the player has moved off the right of the screen, set them to be on the left of the screen 
         
         world.getPlayerBody().setTransform(new Vector2(0, world.getPlayerBody().getPosition().y), 0);
         
      }
      
      //Access the world's reference to Player, and update the position of it's sprite with respect to the body
      blocks = world.getBlocks();
      world.getPlayer().updatePosition();
      for(Block bl : blocks)
    	  bl.updatePosition();
      
      //Have the camera follow the player, but only in the y position
      batch.setProjectionMatrix(camera.combined);
      camera.position.set(camera.position.x, world.getPlayerSprite().getY() - 75, 0);
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
      batch.draw(world.getPlatformSprite(), world.getPlatformSprite().getX(),
    		  world.getPlatformSprite().getY(),world.getPlatformSprite().getOriginX(),
              world.getPlatformSprite().getOriginY(),world.getPlatformSprite().getWidth(),
              world.getPlatformSprite().getHeight(),world.getPlatformSprite().getScaleX(),
              world.getPlatformSprite().getScaleY(),world.getPlatformSprite().getRotation());
      for(Block b : blocks){
    		  batch.draw(b.getSprite(), b.getSprite().getX(),
    		  b.getSprite().getY(),b.getSprite().getOriginX(),
              b.getSprite().getOriginY(),b.getSprite().getWidth(),
              b.getSprite().getHeight(),b.getSprite().getScaleX(),
              b.getSprite().getScaleY(),b.getSprite().getRotation());
      }
      
      score = Math.max(score,  (int)Math.ceil(22-world.getPlayerBody().getPosition().y));
      
      font.setUseIntegerPositions(false);
      font.draw(batch, "Score: " + score, 0, camera.position.y - 275);
      font.draw(batch, "" + (int)Math.ceil(22-world.getPlayerBody().getPosition().y), 0, camera.position.y-260);
      
      batch.end();
      

      debug.render(world.getWorld(), debugMatrix);
      
   
   }
   

}
