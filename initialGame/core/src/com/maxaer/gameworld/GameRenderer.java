package com.maxaer.gameworld;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.maxaer.constants.GameConstants;
import com.maxaer.gameobjects.Block;
import com.maxaer.threaded.SQLStatUpdater;

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
   private SpriteBatch hudBatch;
   private Sprite backgroundSprite;
   private GameWorld world;
   private OrthographicCamera camera;
   private ShapeRenderer shapeRenderer;
   private Box2DDebugRenderer debug;
   private Matrix4 debugMatrix;
   private BitmapFont font, deathFont, nameFont, settingFont;
   private GlyphLayout layout; 
   private int score = 21;
   private int finalScore;
   private Vector<Block> blocks;
   
   public GameRenderer(GameWorld world){
      //Create the reference to the game world
      this.world = world;
      
      //Init the batch and the camera
      batch = new SpriteBatch();
      hudBatch = new SpriteBatch();
      
      shapeRenderer = new ShapeRenderer();
      
      camera = new OrthographicCamera();
      //Set the dimensions of the camera
      camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      
      batch.setProjectionMatrix(camera.combined);
      shapeRenderer.setProjectionMatrix(camera.combined);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 22;
      parameter.color = Color.WHITE;
      font = generator.generateFont(parameter);
      
      layout = new GlyphLayout(); 
      
      parameter.size = 24;
      deathFont = generator.generateFont(parameter);
      
      parameter.size = 20;
      parameter.color = Color.BLACK;
      nameFont = generator.generateFont(parameter);
      
      parameter.size = 12;
      settingFont = generator.generateFont(parameter);
      generator.dispose(); // don't forget to dispose to avoid memory leaks!
      
      Texture backgroundTexture = new Texture("Backgrounds/background.png");
      backgroundSprite = new Sprite(backgroundTexture);
      
      debug = new Box2DDebugRenderer();
            
   }
   
   /*
    * All rendering goes on here. Super important method
    */
   public void render() {
                       
	  // Step the physics simulation forward at a rate of 45hz, recommended by LibGDX
      world.getWorld().step(1/45f, 6, 2);
      
      
      
      Vector<Body> ibb = world.getInactiveBottomBlocks();
      if(!ibb.isEmpty()) {
    	  for(int i = 0; i < ibb.size(); i++) {
        	  ibb.get(i).setActive(false);
        	  ibb.remove(ibb.get(i));
          }
      }
      
      
      
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
      batch.enableBlending();
      batch.begin();
      

      renderBackground();

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
      
      
      if(!world.isGameOver())
         score = Math.max(score,  (int)Math.floor(4.7*(4.7-world.getPlayerBody() .getPosition().y)));
     
      batch.end();
      
      if(!world.isGameOver()){
         hudBatch.begin();
         
         font.setUseIntegerPositions(false);
         font.draw(hudBatch, "Score: " + score, 0, Gdx.graphics.getHeight() - 10);
         font.draw(hudBatch, "" + (int)Math.floor(4.7*(4.7-world.getPlayerBody() .getPosition().y)), 0, Gdx.graphics.getHeight() - 30);
         settingFont.draw(hudBatch, world.getUser().getUserName(), 0, Gdx.graphics.getHeight() - 50);
         layout.setText(settingFont, "Music: on");
         if(world.getMusicPlayer().isPlaying()) settingFont.draw(hudBatch, "Music: on", Gdx.graphics.getWidth() - layout.width - 5,  Gdx.graphics.getHeight() - 10);
         else settingFont.draw(hudBatch, "Music: off", Gdx.graphics.getWidth() - layout.width - 5,  Gdx.graphics.getHeight() - 10);
         
         
         hudBatch.end();
      }
      
      
      //Render the lava here
      Gdx.gl.glEnable(GL30.GL_BLEND);
      Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,  GL20.GL_ONE_MINUS_SRC_ALPHA);
      shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
      shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
      shapeRenderer.begin(ShapeType.Filled);
      shapeRenderer.setColor(.95f, .95f, .95f, .95f);
      shapeRenderer.rect(world.getLava().x, world.getLava().y, world.getLava().width, world.getLava().height);
      shapeRenderer.end();
      Gdx.gl.glDisable(GL30.GL_BLEND);
      
      if(checkLavaDeath() || world.isGameOver()){
         //Update the delay time by adding the time passed since the last delay 
    	  world.setGameOver(true);
    	  world.getPlayerBody().setLinearVelocity(0,0);
    	  
    	  //If the player has just died, we'll go ahead and send their score to SQL right now on a separate thread
          if(world.isJustDied()) { 
            world.setJustDied(false);
            
            //Take care of resetting the score
            finalScore = score;
            score = 21;
            
            //See which type of death it is
            if(!world.isLavaDeath()) world.setBlockDeath(true);
            
            //And update SQL
            sendScoreToSQL(world.getUser().getUserID(), finalScore);    
         
          }
         //Render the game over screen when the user is dead
         renderGameOverScreen();
          
      }
      
      debug.render(world.getWorld(), debugMatrix);
      
   }
   
   public void renderBackground(){
      backgroundSprite.setPosition(0, camera.position.y - 600);
      backgroundSprite.draw(batch);
   }
   
   public void renderPauseScreen() {
	   hudBatch.begin();
	   deathFont.setColor(Color.BLACK);
	   layout.setText(deathFont, "Game Paused");
	   float h1 = layout.height;
	   deathFont.draw(hudBatch, "Game Paused", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - h1)/2);
	   hudBatch.end();
	   
   }
   
   public void falseRenderPauseScreen() {
	   layout.reset();
   }
   public void renderGameOverScreen(){
//      //Clear the screen here
      hudBatch.begin();
      deathFont.setColor(Color.BLACK);
      layout.setText(deathFont, "Game over");
      float h1 = layout.height;
      deathFont.draw(hudBatch, "Game over", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - h1)/2);
      layout.setText(deathFont, "Score: " + score);
      float h2 = layout.height;
      deathFont.draw(hudBatch, "Score: " + finalScore, (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - h1)/2 - h2 - 15);
      layout.setText(deathFont, "Hit space to restart, enter for main menu");
      deathFont.draw(hudBatch, "Hit space to restart, enter for main menu", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - h1)/2 - h2 - layout.height - 30);
      hudBatch.end();
   }
   
   private void sendScoreToSQL(int userID, int score){

      if(!world.getUser().isGuest()){
         SQLStatUpdater updater = new SQLStatUpdater(world.getUser(), score, world.isLavaDeath());
         updater.start();
      }
   }
   

   //Check if the lava has surpassed the player
   public boolean checkLavaDeath(){
      if(world.getLava().getY() <= (world.getPlayer().getSprite().getY() + world.getPlayer().getSprite().getHeight() - 5)){
         world.setLavaDeath(true);
         return true;
      } else{
         return false;
      }
   }
   

}
