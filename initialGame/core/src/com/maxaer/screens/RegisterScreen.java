package com.maxaer.screens;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.maxaer.database.SQLDriver;
import com.maxaer.database.User;
import com.maxaer.game.GameWindow;

public class RegisterScreen implements Screen
{
   private final GameWindow window; 
   private SpriteBatch batch;
   private OrthographicCamera cam;
   private BitmapFont font, fieldFont;
   private Skin skin;
   private Stage stage;
   private TextButton registerBtn;
   private TextField userNameField, passwordField, confirmField;
   private Sprite backgroundSprite;
   
   private static final float BTN_SPACING = 10f; 
   
   public RegisterScreen(GameWindow window){
      cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      cam.setToOrtho(false);
      
      batch = new SpriteBatch();
      
      Texture background = new Texture(Gdx.files.internal("Backgrounds/600x600RegisterBackground.png"));
      backgroundSprite = new Sprite(background);
      backgroundSprite.setPosition(0, 0);
      
      FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/BankGothic-Regular.ttf"));
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = 22;
      parameter.color = Color.BLACK;
      generator.generateData(parameter);
      font = generator.generateFont(parameter);
      
      FreeTypeFontGenerator fieldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/Orbitron Light.ttf"));
      parameter.size = 24;
      parameter.color = Color.GRAY;
      fieldFont = fieldGenerator.generateFont(parameter);
      
      
      stage = new Stage();
      Gdx.input.setInputProcessor(stage);// Make the stage consume events

      createBasicSkin();
      
      userNameField = new TextField("", skin); // Use the initialized skin
      userNameField.setPosition(330, Gdx.graphics.getHeight()/3 + 75);
      userNameField.setWidth(170);
      stage.addActor(userNameField);
      
      passwordField = new TextField("", skin);
      passwordField.setPosition(userNameField.getX(), userNameField.getY() - userNameField.getHeight() - BTN_SPACING);
      passwordField.setWidth(170);
      passwordField.setPasswordMode(true);
      passwordField.setPasswordCharacter('*');
      stage.addActor(passwordField);
      
      confirmField = new TextField("", skin);
      confirmField.setPosition(passwordField.getX(), passwordField.getY() - passwordField.getHeight() - BTN_SPACING);
      confirmField.setWidth(170);
      confirmField.setPasswordMode(true);
      confirmField.setPasswordCharacter('*');
      stage.addActor(confirmField);
      
      
      registerBtn = new TextButton("Register", skin);
      registerBtn.setPosition(userNameField.getX(), confirmField.getY() - confirmField.getHeight() - BTN_SPACING);
      registerBtn.setWidth(170);
      stage.addActor(registerBtn);
      
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
      
      WindowStyle windowStyle = new WindowStyle();
      windowStyle.titleFont = fieldFont;
      windowStyle.background = skin.newDrawable("background", Color.WHITE);
      
      
      Label.LabelStyle lblStyle = new Label.LabelStyle();
      lblStyle.font = fieldFont;
      lblStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
      
      skin.add("default", lblStyle);
      
      skin.add("default", windowStyle);
      

   }
   
   public void addActions(){
      
      registerBtn.addListener(new ChangeListener()
      {
         
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
            
            if(verifyPassword(passwordField.getText(), confirmField.getText()) && userNameFree(userNameField.getText())){
               //Store the user into SQL 
               String hash = hashPassword(passwordField.getText());
               SQLDriver driver = new SQLDriver();
               driver.connect();
               driver.addUser(userNameField.getText(), hash);
               driver.stop();
               
               User user = new User(userNameField.getText(), hash, false);
               //register user and change the screen
               window.setScreen(new GameScreen(window, user));
            } 
            
         }
      });
   }
   
   private String hashPassword(String password){

      MessageDigest messageDigest;
      try
      {
         messageDigest = MessageDigest.getInstance("SHA-256");
         messageDigest.update(password.getBytes());
         return new String(messageDigest.digest());
      }
      catch (NoSuchAlgorithmException e)
      {
         return "";
      }
   
   }
   
   //Check SQL to make sure the username is free
   public boolean userNameFree(String userName){
      
      SQLDriver driver = new SQLDriver();
      driver.connect();
      
      boolean exists = driver.userNameExists(userName);
      
      driver.stop();
      
      if(exists){
         Dialog dialog = new Dialog("", skin);
         dialog.text("Username taken");
         dialog.button("Ok", true);
         dialog.key(Keys.ENTER, true);
         dialog.show(stage);
      }
      
      return !exists;
      
   }
   
   //Method to check that the password has an uppercase and numerical character
   private boolean verifyPassword(String password, String retype){
      //Make sure the password is valid using regex
      if(Pattern.compile("[A-Z]").matcher(password).find() && Pattern.compile("[0-9]").matcher(password).find()){
         if(password.equals(retype)){
            return true;
         } else{
            Dialog dialog = new Dialog("", skin);
            dialog.text("Passwords don't match");
            dialog.button("Ok", true);
            dialog.key(Keys.ENTER, true);
            dialog.show(stage);
            
            return false;
         }
      } else{
         Dialog dialog = new Dialog("", skin);
         dialog.text("Password needs a numerical and upper case character");
         dialog.button("Ok", true);
         dialog.key(Keys.ENTER, true);
         dialog.show(stage);
         return false;
      }
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
          window.setScreen(new MenuScreen(window, new User("", "", true)));
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
