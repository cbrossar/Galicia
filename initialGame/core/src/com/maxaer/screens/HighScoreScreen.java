package com.maxaer.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.maxaer.database.User;

import com.maxaer.database.UserScore;
import com.maxaer.game.GameWindow;
import com.maxaer.threaded.SQLHighScoreRetriever;

public class HighScoreScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font;
   private Skin skin;
   private Sprite backgroundSprite;
   private ArrayList<UserScore> topScores;
   private User user; 
   
   public HighScoreScreen(GameWindow window, User user)
   {
      // TODO Auto-generated constructor stub
      this.window = window;
      this.user = user; 
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      SQLHighScoreRetriever retrieveScores = new SQLHighScoreRetriever(this);
      retrieveScores.start();
      
      Texture background = new Texture(Gdx.files.internal("Backgrounds/MaxaerHighScoresBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 18;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      font = generator.generateFont(parameter);
      
   }
   
   public void setTopScores(ArrayList<UserScore> topScores)
   {
      this.topScores = topScores;
   }
   

   @Override
   public void show()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void render(float delta)
   {
      // TODO Auto-generated method stub
      Gdx.gl.glClearColor(0f, 0f, 0f, 1);
      
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      cam.update();
      batch.setProjectionMatrix(cam.combined);

      batch.begin();
     
      backgroundSprite.draw(batch);
      
      if(topScores != null)
         //Draw the high scores out
         for(int i = 0; i < topScores.size(); i++){
            font.draw(batch, topScores.get(i).toString(), 350, 500 - 25*i);
         }
      batch.end();
      
      if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
         window.setScreen(new MenuScreen(window, user));
      }
   }

   @Override
   public void resize(int width, int height)
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void pause()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void resume()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void hide()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void dispose()
   {
      // TODO Auto-generated method stub
      skin.dispose();
   }

}
