package com.maxaer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.maxaer.game.GameWindow;

public class MenuScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font;
   private GlyphLayout layout;
   public MenuScreen(GameWindow window){
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/MonaKo.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 26;
      parameter.color = Color.WHITE;
      generator.generateData(parameter);
      font = generator.generateFont(parameter);
      layout = new GlyphLayout();
      
      
      this.window = window; 
   
   }

   @Override
   public void show()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void render(float delta) {
       Gdx.gl.glClearColor(0, 0, 0.2f, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       cam.update();
       batch.setProjectionMatrix(cam.combined);

       batch.begin();
       layout.setText(font, "MaxÆR");
       font.draw(batch, "MaxÆR", (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2);
       layout.setText(font, "Press Space to Begin");
       font.draw(batch, "Press Space to Begin", (Gdx.graphics.getWidth() - layout.width)/2, Gdx.graphics.getHeight()/2 - 20);
       
       batch.end();

       //Set the window on touch
       if (Gdx.input.isKeyPressed(Keys.SPACE)) {
           window.setScreen(new GameScreen());
           dispose();
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
      
   }
   
}
