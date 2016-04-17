package com.maxaer.screens;

import java.io.File;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.maxaer.database.User;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.maxaer.game.GameWindow;
import com.maxaer.gameobjects.AnimatedSprite;
import com.maxaer.gameobjects.Player;
import com.maxaer.gameworld.GameRenderer;
import com.maxaer.gameworld.GameWorld;

/*
 * Class: GameScreen
 * Author: Peter Kaminski
 * Purpose: GameScreen serves as the main game screen for maxaer
 *          1. The game screen contains a GameWorld and a GameRenderer
 *              a. GameWorld is responsible for creating all the bodies in the world
 *              b. GameRenderer is responsible for handling all rendering in the game for this screen
 */
public class GameScreen implements Screen
{
   
   private GameWorld world;
   private GameRenderer renderer;

   private SpriteBatch batch;
   private AnimatedSprite animatedSprite;
   private Texture img;
   private TextureRegion[] animationFrames;
   private float elapsedTime;
   
   public GameScreen(GameWindow window, User user){
      world = new GameWorld(window, user);

   public GameScreen(GameWindow window, User user, boolean isMultiplayer){
	  if(isMultiplayer) {
		  world = new GameWorld(window, user, 0, isMultiplayer);
	  } else {
		  Random rand = new Random();
		  int i = rand.nextInt();
		  world = new GameWorld(window, user, i, isMultiplayer);
	  }

      renderer = new GameRenderer(world);
   }
   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void show() {
   }

   @Override
   public void hide() {    
   }

   @Override
   public void pause() {       
   }

   @Override
   public void resume() {      
   }

   @Override
   public void dispose() {
      //Clean up clean up
       world.dispose();
   }

   /*
    * Render is a very important function. It is called roughly 60 times per second, and it handles the updating of the screen
    *   1. Render calls world.update() to update the bodies in the world
    *   2. Render calls the render function for the GameRenderer--passing off game rendering to this class
    *   3. This render function is called from Game's render function. So it will be called when the game GameObject begins execution
    */
   
   @Override
   public void render(float delta)
   { 
      world.update(delta); 
   {  
      //Only update the world when it's single player, or the multiplayer is ready
      if(!world.isMultiplayer() || (world.isMultiplayer() && world.isMultiplayerReady()))
         world.update(delta); 

      boolean wasPaused = false;
      
      if(!world.getRunningWorld()){
    	  Gdx.graphics.setContinuousRendering(false);
    	  wasPaused = true;
    	  renderer.renderPauseScreen();
      }
      else if(wasPaused)
      {
    	  wasPaused = false;
    	  Gdx.graphics.requestRendering();
    	  renderer.falseRenderPauseScreen();
      }
      else {
    	  Gdx.graphics.requestRendering();
    	  elapsedTime += delta;
    	  //renderer.setElapsedTime(elapsedTime);
    	  renderer.render();  
      }
   }


}
