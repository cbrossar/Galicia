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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.maxaer.game.GameWindow;

public class LoginScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font, fieldFont;
   private GlyphLayout layout;
   private Skin skin;
   private Stage stage;
   private TextButton loginBtn;
   private TextField userNameField, passwordField;
   private Sprite backgroundSprite;
   
   private static final float BTN_SPACING = 10f; 
   
   public LoginScreen(GameWindow window){
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      Texture background = new Texture(Gdx.files.internal("Backgrounds/LoginBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 26;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      font = generator.generateFont(parameter);
      
      parameter.size = 18;
      parameter.color = Color.GRAY;
      fieldFont = generator.generateFont(parameter);
      
      layout = new GlyphLayout();
      
      stage = new Stage();
      Gdx.input.setInputProcessor(stage);// Make the stage consume events

      createBasicSkin();
      
      userNameField = new TextField("Username", skin); // Use the initialized skin
      userNameField.setPosition(420, Gdx.graphics.getHeight()/3 + 30);
      userNameField.setWidth(200);
      stage.addActor(userNameField);
      
      passwordField = new TextField("Password", skin);
      passwordField.setPasswordMode(true);
      passwordField.setPasswordCharacter('.');
      passwordField.setPosition(userNameField.getX(), userNameField.getY() - userNameField.getHeight() - BTN_SPACING);
      passwordField.setWidth(200);
      stage.addActor(passwordField);
      
      
      loginBtn = new TextButton("Login", skin);
      loginBtn.setPosition(userNameField.getX(), passwordField.getY() - passwordField.getHeight() - BTN_SPACING);
      loginBtn.setWidth(200);
      stage.addActor(loginBtn);
      
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
      
      Pixmap curse = new Pixmap(1, 5, Pixmap.Format.RGB888);
      curse.setColor(Color.FIREBRICK);
      pixmap.fill();
      
      skin.add("cursor", new Texture(curse));
      curse.dispose();
      pixmap.dispose();

      //Create a button style
      TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
      textButtonStyle.up = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.down = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.checked = skin.newDrawable("background", Color.WHITE);
      textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
      textButtonStyle.font = skin.getFont("default");
      skin.add("default", textButtonStyle);
      
      TextFieldStyle textStyle = new TextFieldStyle();
      textStyle.font = fieldFont;
      textStyle.fontColor = Color.BLACK;
      textStyle.focusedFontColor = Color.ORANGE;
      textStyle.background = skin.newDrawable("background", Color.WHITE);
      textStyle.cursor = skin.newDrawable("cursor", Color.FIREBRICK);

      skin.add("default", textStyle);
      

   }
   
   public void addActions(){
      
      loginBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
            System.out.println(userNameField.getText() + " " + passwordField.getText());
            window.setScreen(new GameScreen(window));
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
          window.setScreen(new MenuScreen(window));
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
      stage.dispose();
      batch.dispose();
      skin.dispose();
   }

   

}
