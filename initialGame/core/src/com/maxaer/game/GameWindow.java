package com.maxaer.game;

import com.maxaer.screens.MenuScreen;

/*
 * Class: GameWindow, extends Game
 * Author: Peter Kaminski
 * Purpose: GameWindow extends the game class to create a standard ApplicationAdapter. It's primary focus is to serve as the entry point
 *          for our maxaer game. 
 *          1. a gamescreen is created and set as the primary screen for the game. 
 */
public class GameWindow extends Game{

    @Override
    public void create() {
       
       setScreen(new MenuScreen(this));
        
    }

}