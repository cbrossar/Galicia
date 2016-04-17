package com.maxaer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.maxaer.database.User;
import com.maxaer.game.GameWindow;

//Screen to display the different game mode types
public class GameModeScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font;
   private Skin skin;
   private Stage stage;
   private TextButton singleBtn, multiBtn;
   private Sprite backgroundSprite;
   private User user; 
   
   private static final float BTN_SPACING = 10f; 
   

   public GameModeScreen(GameWindow window, User user){
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("data/MonaKo.ttf"));

      Texture background = new Texture(Gdx.files.internal("Backgrounds/600x600GameModeBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));

      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 26;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      font = generator.generateFont(parameter);

      
      stage = new Stage();
      Gdx.input.setInputProcessor(stage);// Make the stage consume events

      createBasicSkin();
      
      singleBtn = new TextButton("1 person", skin); // Use the initialized skin
      singleBtn.setPosition(325, Gdx.graphics.getHeight()/3 + 50);
      singleBtn.setWidth(190);
      stage.addActor(singleBtn);
      
      multiBtn = new TextButton("Multiplayer", skin);
      multiBtn.setPosition(singleBtn.getX(), singleBtn.getY() - singleBtn.getHeight() - BTN_SPACING);
      multiBtn.setWidth(190);
      stage.addActor(multiBtn);
      
      addActions(); 
      
      this.window = window; 
      this.user = user;
   
   }
   
   //Create a skin for the menu button's -- found a tempate online for this code
   private void createBasicSkin(){
      //Create a font
      skin = new Skin();
      skin.add("default", font);

      //Create a texture
      Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
      pixmap.setColor(Color.WHITE);
      pixmap.fill();
      skin.add("background",new Texture(pixmap));

      //Create a button style
      TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
      textButtonStyle.up = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.down = skin.newDrawable("background", Color.LIGHT_GRAY);
      textButtonStyle.checked = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
      textButtonStyle.font = skin.getFont("default");
      skin.add("default", textButtonStyle);
      

   }
   
   public void addActions(){
      singleBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
                window.setScreen(new GameScreen(window, user, false));
                dispose();
            
         }
      });
      
      multiBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
                window.setScreen(new GameScreen(window, user, true));
                dispose();
            
         }
      });
      
   }

   @Override
   public void show()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void render(float delta) {
       Gdx.gl.glClearColor(0f, 0f, 0f, 1);
       
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       
       cam.update();
       batch.setProjectionMatrix(cam.combined);

       batch.begin();
      
       backgroundSprite.draw(batch);
       batch.end();
       
       stage.act();
       stage.draw();
       
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
      stage.dispose();
      batch.dispose();
   }

}
