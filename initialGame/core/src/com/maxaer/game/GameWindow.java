package com.maxaer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.maxaer.database.User;
import com.maxaer.screens.MenuScreen;

/*
 * Class: GameWindow, extends Game
 * Author: Peter Kaminski
 * Purpose: GameWindow extends the game class to create a standard ApplicationAdapter. It's primary focus is to serve as the entry point
 *          for our maxaer game. 
 *          1. a gamescreen is created and set as the primary screen for the game. 
 */
public class GameWindow extends Game{

	private Music music;
    @Override
    public void create() {
    	music = Gdx.audio.newMusic(Gdx.files.internal("data/Desiigner-Panda.mp3"));
    	music.play();
    	music.setLooping(true);
        setScreen(new MenuScreen(this, new User("", "", true)));
        
    }
    
    public Music getMusicPlayer()
    {
    	return music;
    }

}