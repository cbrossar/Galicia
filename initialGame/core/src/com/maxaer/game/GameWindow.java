package com.maxaer.game;

import com.maxaer.screens.GameScreen;

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
       
       setScreen(new GameScreen());

//        //Now create our platform shape here
//        BodyDef platformDef = new BodyDef();
//        platformDef.type = BodyDef.BodyType.StaticBody;
//        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS + 10f;
//        
//        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
//        // debug renderer
//        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
//        //bodyDef2.position.set(0,
////                h-10/PIXELS_TO_METERS);
//        platformDef.position.set(0,0);
//       
//        FixtureDef fixtureDef2 = new FixtureDef();
//        EdgeShape edgeShape = new EdgeShape();
//        edgeShape.set(-w/2,-h/2,w/2,-h/2);
//        fixtureDef2.shape = edgeShape;
//         
//        platform = world.createBody(platformDef);
//        platform.createFixture(fixtureDef2);
//        edgeShape.dispose();

    }





}