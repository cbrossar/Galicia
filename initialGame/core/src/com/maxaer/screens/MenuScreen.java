package com.maxaer.screens;

import com.badlogic.gdx.Gdx;
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



public class MenuScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font;
   private Skin skin;
   private Stage stage;
   private TextButton playBtn, scoresBtn, registerBtn, loginBtn, myStatsBtn;
   private Sprite backgroundSprite;
   
   private static final float BTN_SPACING = 10f; 
   

   public MenuScreen(GameWindow window, User user){
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("data/MonaKo.ttf"));

      Texture background = new Texture(Gdx.files.internal("Backgrounds/600x600Background.png"));
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
      
      playBtn = new TextButton("Play", skin); // Use the initialized skin
      playBtn.setPosition(420, Gdx.graphics.getHeight()/3 + 50);
      playBtn.setWidth(200);
      stage.addActor(playBtn);
      
      registerBtn = new TextButton("Sign up", skin);
      registerBtn.setPosition(playBtn.getX(), playBtn.getY() - playBtn.getHeight() - BTN_SPACING);
      registerBtn.setWidth(200);
      stage.addActor(registerBtn);
      
      loginBtn = new TextButton("Login", skin);
      loginBtn.setPosition(registerBtn.getX(), registerBtn.getY() - registerBtn.getHeight() - BTN_SPACING);
      loginBtn.setWidth(200);
      stage.addActor(loginBtn);
      
      
      scoresBtn = new TextButton("High Scores", skin);
      scoresBtn.setPosition(playBtn.getX(), loginBtn.getY() - loginBtn.getHeight() - BTN_SPACING);
      scoresBtn.setWidth(200);
      stage.addActor(scoresBtn);
      
      addActions(); 
      
      stage = new Stage();
      Gdx.input.setInputProcessor(stage);// Make the stage consume events

      createBasicSkin();
      
      playBtn = new TextButton("Play", skin); // Use the initialized skin
      playBtn.setPosition(325, Gdx.graphics.getHeight()/3 + 50);
      playBtn.setWidth(190);
      stage.addActor(playBtn);
      
      registerBtn = new TextButton("Sign up", skin);
      registerBtn.setPosition(playBtn.getX(), playBtn.getY() - playBtn.getHeight() - BTN_SPACING);
      registerBtn.setWidth(190);
      
      loginBtn = new TextButton("Login", skin);
      loginBtn.setPosition(registerBtn.getX(), registerBtn.getY() - registerBtn.getHeight() - BTN_SPACING);
      loginBtn.setWidth(190);
      

      scoresBtn = new TextButton("High Scores", skin);
      scoresBtn.setWidth(190);
      stage.addActor(scoresBtn);
      
      if(user.isGuest()){
         
         stage.addActor(registerBtn);
         stage.addActor(loginBtn);
         
         scoresBtn.setPosition(playBtn.getX(), loginBtn.getY() - loginBtn.getHeight() - BTN_SPACING);
      } else{
         myStatsBtn = new TextButton("My Stats", skin);
         myStatsBtn.setPosition(playBtn.getX(), playBtn.getY() - playBtn.getHeight() - BTN_SPACING);
         stage.addActor(myStatsBtn);
         
         scoresBtn.setPosition(playBtn.getX(), myStatsBtn.getY() - myStatsBtn.getHeight() - BTN_SPACING);
      }
      
      stage.addActor(scoresBtn);
     

      
      addActions(); 
      
      this.window = window; 
   
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
      textButtonStyle.down = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.checked = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
      textButtonStyle.font = skin.getFont("default");
      skin.add("default", textButtonStyle);
      

   }
   
   public void addActions(){
      playBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {

                window.setScreen(new GameScreen(window, new User("", "", true)));
                dispose();
            
         }
      });
      
      registerBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
            window.setScreen(new RegisterScreen(window));
            dispose(); 
            
         }
      });
      
      loginBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
            // TODO Auto-generated method stub
            window.setScreen(new LoginScreen(window));
            dispose();
         }
      });
      
      scoresBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
            window.setScreen(new HighScoreScreen(window));
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
