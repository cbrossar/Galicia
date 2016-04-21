package com.maxaer.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
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
   private BitmapFont font, deathFont, settingFont;
   private GlyphLayout layout; 
   private int score = 21;
   private int finalScore;
   private ArrayList<Block> blocks;
   private float elapsedTime;

   private Texture img;
   private TextureRegion[] animationFrames;
   private Animation animation;

   private float spacing, spacing1;

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

      parameter.color = Color.BLACK;
      parameter.size = 12;
      settingFont = generator.generateFont(parameter);
      generator.dispose(); // don't forget to dispose to avoid memory leaks!

      Texture backgroundTexture = new Texture("Backgrounds/background.png");
      backgroundSprite = new Sprite(backgroundTexture);

      debug = new Box2DDebugRenderer();



      initiateAnimations();
   }

   private void initiateAnimations() {
      batch = new SpriteBatch();
      img = new Texture("data/deathAnimation.png");

      TextureRegion[][] tempFrames = TextureRegion.split(img, 90, 120);

      animationFrames = new TextureRegion[4];
      int index = 0;

      for(int i = 0; i < 2; i++) {
         for (int j = 0; j < 2; j++) {
            animationFrames[index++] = tempFrames[i][j];
         }
      }
      int i = 0;
      while(animationFrames.length > i) { animationFrames[i].flip(false, true); i++; } 
      animation = new Animation(1/10f, animationFrames);
      animation.setPlayMode(PlayMode.NORMAL);
      elapsedTime = 0;
   }

   public void setElapsedTime(float elapsedTime2) {
      elapsedTime = elapsedTime2;
   }
   /*
    * All rendering goes on here. Super important method
    */
   public void render() {

      // Step the physics simulation forward at a rate of 45hz, recommended by LibGDX
      world.getWorld().step(1/45f, 6, 2);


      if(world.isMultiplayer() && !world.isMultiplayerReady()){
         renderWaitingScreen();
         return;

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

      if(!world.isGameOver()){
         score = Math.max(score,  (int)Math.floor(4.7*(4.7-world.getPlayerBody() .getPosition().y)));
         world.setCurrentScore(score);
      } else{
         //Setting the death score here
         world.setCurrentScore(GameConstants.DEATH_SCORE);
      }

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
         layout.setText(settingFont, "Multiplier: " + world.getUser().getDifficulty());
         settingFont.draw(hudBatch, "Multiplier: " + world.getUser().getDifficulty(), Gdx.graphics.getWidth() - layout.width - 5,  Gdx.graphics.getHeight() - 10 - 2*layout.height);

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

      //If the game is multiplayer, go ahead and render the opponent and their information
      if(world.isMultiplayer()){
         shapeRenderer.setColor(Color.LIGHT_GRAY);
         shapeRenderer.rect(world.getOpponent().x, world.getOpponent().y, world.getOpponent().width, world.getOpponent().height);

         hudBatch.begin();

         //If the other user is dead and we are also dead, then show the final screen
         if(!world.isMultiplayerFinished() && !world.isGameOver()){
            font.setColor(Color.BLACK);
            deathFont.setColor(Color.BLACK);
            if(world.getOpponentsScore() != 0){
               
               if(world.getOpponentID().length() == 0){
                  layout.setText(deathFont, "Opponent's score " + world.getOpponentsScore());
                  deathFont.draw(hudBatch, "Opponent's score " + world.getOpponentsScore(), (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight() - 10);
               } else{
                  layout.setText(deathFont, world.getOpponentID() + "'s score " + world.getOpponentsScore());
                  deathFont.draw(hudBatch, world.getOpponentID() + "'s score " + world.getOpponentsScore(), (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight() - 10);

               }
            } else{
               layout.setText(font, "Opponent disconnected");
               font.draw(hudBatch, "Opponent disconnected", (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight() - 10);

            }
            font.setColor(Color.WHITE);
         }
         hudBatch.end();
      }


      shapeRenderer.end();



      Gdx.gl.glDisable(GL30.GL_BLEND);

      if(checkLavaDeath() || world.isGameOver()){
         //Update the delay time by adding the time passed since the last delay 

    	  world.setGameOver(true);
    	  world.setLastHeight(0);

    	  if(world.getCreatedGame()) {
    		  elapsedTime = 0;
    		  world.setCreatedGame(false);
    	  }
    	  world.getPlayer().setJumpability(false);
    	
    	  elapsedTime += Gdx.graphics.getDeltaTime();

    	  if(!animation.isAnimationFinished(elapsedTime)) {


    	     Gdx.gl.glClearColor(1,0,0,1);
    	     Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT); 
    	     world.getPlayerBody().setLinearVelocity(0,0);
    	     world.getPlayerSprite().setTexture(new Texture("45x60player_gone.png"));

    	     batch.begin();

    	     batch.draw(animation.getKeyFrame(elapsedTime, true), world.getPlayer().getX()*GameConstants.PIXEL_TO_METERS - 40, world.getPlayer().getY()*GameConstants.PIXEL_TO_METERS- 85);
    	     batch.end();
    	  }


         //If the player has just died, we'll go ahead and send their score to SQL right now on a separate thread

         if(world.isJustDied()) { 
            world.setJustDied(false);
            world.setCurrentScore(GameConstants.DEATH_SCORE);
            //Update the world's score 
            //Take care of resetting the score
            finalScore = score * world.getUser().getDifficulty();
            score = 21;

            //See which type of death it is
            if(!world.isLavaDeath()) world.setBlockDeath(true);

            //And update SQL
            sendScoreToSQL(world.getUser().getUserID(), finalScore);    

         }

         //Render the game over screen when the user is dead
         renderGameOverScreen();

      }

      // debug.render(world.getWorld(), debugMatrix);


   }


   public void renderBackground(){
      backgroundSprite.setPosition(0, camera.position.y - 600);
      backgroundSprite.draw(batch);
   }

   public void renderWaitingBackground(){
      backgroundSprite.setPosition(0, camera.position.y - 600);
      backgroundSprite.draw(hudBatch);
   }

   public void renderWaitingScreen(){
      //Clear the screen here
      Gdx.gl.glClearColor(1, 1, 1, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      hudBatch.begin();
      renderWaitingBackground();
      deathFont.setColor(Color.BLACK);
      layout.setText(deathFont, "Waiting for opponent...");
      spacing = layout.height;
      deathFont.draw(hudBatch, "Waiting for opponent", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2);
      hudBatch.end();
   }

   public void renderPauseScreen() {
      hudBatch.begin();
      deathFont.setColor(Color.BLACK);
      layout.setText(deathFont, "Game Paused");
      spacing = layout.height;
      deathFont.draw(hudBatch, "Game Paused", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2);
      hudBatch.end();

   }

   public void falseRenderPauseScreen() {
      layout.reset();
   }
   public void renderGameOverScreen(){

      hudBatch.begin();

      deathFont.setColor(Color.BLACK);
      //Render the final multiplayer results here
      if(world.isMultiplayer() && world.isMultiplayerFinished()){
         if(world.getOpponentsScore()*world.getUser().getDifficulty() < finalScore){
            layout.setText(deathFont, "You win! " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty());
            deathFont.draw(hudBatch, "You win! " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty(), (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2);
         } else if(world.getOpponentsScore()*world.getUser().getDifficulty() == finalScore){
            layout.setText(deathFont, "You tied! " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty());
            deathFont.draw(hudBatch, "You tied! " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty(), (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2);

         } else {
            layout.setText(deathFont, "You lost :( " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty());
            deathFont.draw(hudBatch, "You lost :( " + finalScore + " - " + world.getOpponentsScore()*world.getUser().getDifficulty(), (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2);
         }
         
         layout.setText(deathFont, "Hit Enter to return the the menu");
         deathFont.draw(hudBatch, "Hit Enter to return the the menu", (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2 - layout.height - 5);
         
      } else{
         //If the world is multiplayer but it's not yet over, then render a different screen.
         if(world.isMultiplayer()) {
            if(world.getOpponentID().length() > 0){
               layout.setText(deathFont, "Opponent's score " + world.getOpponentsScore()*world.getUser().getDifficulty());
               deathFont.draw(hudBatch, "Opponent's score " + world.getOpponentsScore()*world.getUser().getDifficulty(), (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight())/2 + layout.height);
            } else{
               layout.setText(deathFont, world.getOpponentID() + "'s score " + world.getOpponentsScore()*world.getUser().getDifficulty());
               deathFont.draw(hudBatch, world.getOpponentID() + "'s score " + world.getOpponentsScore()*world.getUser().getDifficulty(), (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight())/2 + layout.height);  
            }
            
            layout.setText(deathFont, "You died. Waiting for your opponent to die...");
            spacing = layout.height;
            deathFont.draw(hudBatch, "You died. Waiting for your opponent to die...", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2 - 10);
            layout.setText(deathFont, "Your Score: " + finalScore);
            spacing1 = layout.height;
            deathFont.draw(hudBatch, "Your Score: " + finalScore, (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2 - spacing1 - 25);
         
            
         } else{ //If it was never multiplayer, render a normal game over screen
            layout.setText(deathFont, "Game over");
            spacing = layout.height;
            deathFont.draw(hudBatch, "Game over", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2);
            layout.setText(deathFont, "Score: " + score);
            spacing1 = layout.height;
            deathFont.draw(hudBatch, "Score: " + finalScore, (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2 - spacing1 - 15);
            if(world.isMultiplayer()) {
                layout.setText(deathFont, "Hit Enter to return to the main menu");
                deathFont.draw(hudBatch, "Hit Enter to return to the main menu", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2 - spacing1 - layout.height - 30);
               
            } else {
                layout.setText(deathFont, "Hit space to restart, enter for main menu");
                deathFont.draw(hudBatch, "Hit space to restart, enter for main menu", (Gdx.graphics.getWidth() - layout.width)/2, (Gdx.graphics.getHeight() - spacing)/2 - spacing1 - layout.height - 30);
            }

         }
         
      }

      hudBatch.end();

   }

   private void sendScoreToSQL(int userID, int score){

      if(!world.getUser().isGuest()){
         SQLStatUpdater updater = new SQLStatUpdater(world.getUser(), score, world.isLavaDeath());
         updater.start();
      }
   }

   public OrthographicCamera getCamera() {
      return camera;
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
