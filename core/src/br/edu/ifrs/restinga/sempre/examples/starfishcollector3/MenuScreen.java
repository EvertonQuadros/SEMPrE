/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector3;

import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

/**
 *
 * @author Not275
 */
public class MenuScreen extends BaseScreen {

    @Override
    public void update(float dt) {
        
        if(Gdx.input.isKeyJustPressed(Keys.S)){
            StarfishGame.setActiveScreen(new LevelScreen());
        }
        
    }

    @Override
    public void initActors() {
        
        BaseActor ocean = new BaseActor(0f, 0f, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);
        
        BaseActor title = new BaseActor(0f, 0f, mainStage);
        title.loadTexture("starfish-collector.png");
        title.centerAtPosition(400, 300);
        title.moveBy(0, 100);
        
        BaseActor start = new BaseActor(0f, 0f, mainStage);
        start.loadTexture("message-start.png");
        start.centerAtPosition(400, 300);
        start.moveBy(0, -100);
        
    }
    
    @Override
    public void initComponents() {}

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}
    
}
