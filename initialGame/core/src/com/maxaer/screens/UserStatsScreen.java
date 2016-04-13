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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.maxaer.database.User;
import com.maxaer.database.UserScore;
import com.maxaer.game.GameWindow;
import com.maxaer.threaded.SQLHighScoreRetriever;
import com.maxaer.threaded.SQLUserStatRetriever;

public class UserStatsScreen implements Screen
{ 
   private static final float BTN_SPACING = 10f; 
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont fineFont, largeFont;
   private Skin skin;
   private GlyphLayout layout;
   private Sprite backgroundSprite;
   private ArrayList<UserScore> topScores;
   private User user; 
   
   public UserStatsScreen(GameWindow window, User user)
   {
      // TODO Auto-generated constructor stub
      this.window = window;
      this.user = user; 
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      SQLUserStatRetriever retrieveScores = new SQLUserStatRetriever(this);
      retrieveScores.start();
      
      Texture background = new Texture(Gdx.files.internal("Backgrounds/600x600ScoreBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 18;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      fineFont = generator.generateFont(parameter);
      
      parameter.size = 24;
      largeFont = generator.generateFont(parameter);
      
   }
   
   public User getUser()
   {
      return user;
   }
   
   public void setUserStats(ArrayList<UserScore> topScores)
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
      
      layout.setText(largeFont, user.getUserName());
      float largeH = layout.height;
      largeFont.draw(batch, user.getUserName(), 350, 500 - 25);
      layout.setText(fineFont, "Total distance jumped: "  + user.getTotalDistanceTraveled());
      float fineH = layout.height;
      fineFont.draw(batch, "Total distance jumped: "  + user.getTotalDistanceTraveled(), 350, 500 - 25 + layout.height + BTN_SPACING);
//      fineFont.draw(batch, topScores.get(i).toString(), 350, 500 - 25 + largeH);
//      fineFont.draw(batch, topScores.get(i).toString(), 350, 500 - 25*i);
//      fineFont.draw(batch, topScores.get(i).toString(), 350, 500 - 25*i);
//      fineFont.draw(batch, topScores.get(i).toString(), 350, 500 - 25*i);
      
      if(topScores != null)
         //Draw the high scores out
         for(int i = 0; i < topScores.size(); i++){
            fineFont.draw(batch, topScores.get(i).toString(), 350, 500 - 25*i);
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
