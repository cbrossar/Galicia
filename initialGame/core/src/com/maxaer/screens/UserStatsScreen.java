package com.maxaer.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.maxaer.database.User;
import com.maxaer.game.GameWindow;
import com.maxaer.threaded.SQLUserStatRetriever;

public class UserStatsScreen implements Screen
{ 
   private static final float BTN_SPACING = 15f; 
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont fineFont, largeFont;
   private Skin skin;
   private GlyphLayout layout;
   private Sprite backgroundSprite;
   private User user; 
   private final float LARGE_HEIGHT, FINE_HEIGHT;
   
   public UserStatsScreen(GameWindow window, User user)
   {
      // TODO Auto-generated constructor stub
      this.window = window;
      this.user = user; 
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();

      Texture background = new Texture(Gdx.files.internal("Backgrounds/600x600ScoreBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 24;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      fineFont = generator.generateFont(parameter);
     
      layout = new GlyphLayout();
      
      layout.setText(fineFont, "MaxAER");
      FINE_HEIGHT = layout.height;
     
      parameter.size = 36;
      largeFont = generator.generateFont(parameter);
      
      layout.setText(largeFont, "MaxAER");
      LARGE_HEIGHT = layout.height;
      
      //Retrieve the user stats here
      SQLUserStatRetriever retrieveStats = new SQLUserStatRetriever(this);
      retrieveStats.start();
   }
   
   public User getUser()
   {
      return user;
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
      //Draw everything to the screen
      largeFont.draw(batch, user.getUserName(), 350, 500 - 25);
      fineFont.draw(batch, "All time high score:  " + user.getHighScore(), 250, 500 - 25 - LARGE_HEIGHT - BTN_SPACING);
      fineFont.draw(batch, "Total deaths:  " + user.getDeathCount(), 250, 500 - 25 - LARGE_HEIGHT - FINE_HEIGHT - 2*BTN_SPACING);
      fineFont.draw(batch, "Deaths by milk:  " + user.getLavaDeaths(), 250, 500 - 25 - LARGE_HEIGHT - 2*FINE_HEIGHT - 3*BTN_SPACING);
      fineFont.draw(batch, "Smushed deaths:  " + user.getSmushDeaths(), 250, 500 - 25 - LARGE_HEIGHT - 3*FINE_HEIGHT - 4*BTN_SPACING);
      fineFont.draw(batch, "Distance traveled:  " + user.getTotalDistanceTraveled(), 250, 500 - 25 - LARGE_HEIGHT - 4*FINE_HEIGHT - 5*BTN_SPACING);

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
