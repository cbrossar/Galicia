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
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.maxaer.database.User;
import com.maxaer.game.GameWindow;

public class UserSettingsScreen implements Screen{
	
	private GameWindow window;
	private User user;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Sprite backgroundSprite;
	private TextButton musicOnOffBtn, easyBtn, mediumBtn, hardBtn;
	private Stage stage;
	private BitmapFont font, fieldFont;
	private Skin skin;

	private static final float BTN_SPACING = 10f;
	
	public UserSettingsScreen(GameWindow window, User user) {
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
		parameter.size = 18;
		parameter.color = Color.BLACK;
		generator.generateData(parameter);
		font = generator.generateFont(parameter);
		
		stage = new Stage();
	    Gdx.input.setInputProcessor(stage);
	    
	    createBasicSkin();
	    
	    if(user.getMusic()) {
	    	 musicOnOffBtn = new TextButton("OFF", skin);
	    } else {
	    	musicOnOffBtn = new TextButton("ON", skin);
	    }
	    
	    ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>();
	   
	    musicOnOffBtn.setPosition(Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/9, Gdx.graphics.getHeight()/2 +  Gdx.graphics.getHeight()/8);
	    musicOnOffBtn.setWidth(170);
	    stage.addActor(musicOnOffBtn);
	    	    
	    easyBtn = new TextButton("EASY", skin);
	    easyBtn.setPosition(musicOnOffBtn.getX(), musicOnOffBtn.getY() - musicOnOffBtn.getHeight() - 7*BTN_SPACING);
	    easyBtn.setWidth(170);
	    buttonGroup.add(easyBtn);
	    easyBtn.setChecked(true);
	    stage.addActor(easyBtn);
	    
	    
	   
	    
	    mediumBtn = new TextButton("MEDIUM", skin);
	    mediumBtn.setPosition(easyBtn.getX(), easyBtn.getY() - easyBtn.getHeight() - BTN_SPACING);
	    mediumBtn.setWidth(170);
	    buttonGroup.add(mediumBtn);
	    stage.addActor(mediumBtn);
	    
	    hardBtn = new TextButton("HARD", skin);
	    hardBtn.setPosition(mediumBtn.getX(), mediumBtn.getY() - mediumBtn.getHeight() - BTN_SPACING);
	    hardBtn.setWidth(170);
	    buttonGroup.add(hardBtn);
	    stage.addActor(hardBtn);
		
	    addActions();
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
      	textButtonStyle.down = skin.newDrawable("background", Color.LIGHT_GRAY);
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
   
   	private void addActions() {
		
   		musicOnOffBtn.addListener(new ChangeListener() {
           
           @Override
           public void changed(ChangeEvent event, Actor actor)
           {
        	   if(user.getMusic()) {
        		   musicOnOffBtn.setText("ON");
        		   user.setMusic(false);
        	   } else {
        		   musicOnOffBtn.setText("OFF");
        		   user.setMusic(true);
        	   }
           }
       });
   		
   		
   		easyBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				user.setDifficulty(1);
			}
   			
   		});
   		
   		mediumBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				user.setDifficulty(2);
			}
   			
   		});
   		
   		hardBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				user.setDifficulty(3);
			}
   			
   		});
	}
	   
	   
 
	@Override
	public void show() {
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
	    
	    backgroundSprite.draw(batch);
	    batch.end();
       
	    stage.act();
	    stage.draw();

	    if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){

	    	window.setScreen(new MenuScreen(window, user));
	    	dispose();
	    }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
