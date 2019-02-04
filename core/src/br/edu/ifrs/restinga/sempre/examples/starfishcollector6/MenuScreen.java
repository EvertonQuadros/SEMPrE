/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector6;

import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector3.StarfishGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 *
 * @author Not275
 */
public class MenuScreen extends BaseScreen {
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(Gdx.input.isKeyPressed(Keys.ENTER)){
            StarfishGame.setActiveScreen(new StoryScreen());
        }
        
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            Gdx.app.exit();
        }
     
        return false;
        
    }

    @Override
    public void initActors() {
        
        BaseActor ocean = new BaseActor(0f, 0f, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);
        
        BaseActor title = new BaseActor(0f, 0f, mainStage);
        title.loadTexture("starfish-collector.png");
        
        uiTable.add(title).colspan(2);
        
    }
    
    @Override
    public void initComponents() {
        
        TextButton startButton = new TextButton("Start", BaseGame.textbuttonStyle);
        startButton.setPosition(150, 150);
        uiStage.addActor(startButton);
        
            startButton.addListener((Event e) -> {
        
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    StarfishGame.setActiveScreen(new StoryScreen());
                }
                    
                return false;
                
            }
                
        );
        
        TextButton quitButton = new TextButton("Quit", BaseGame.textbuttonStyle);
        quitButton.setPosition(500, 150);
        uiStage.addActor(quitButton);
        
            quitButton.addListener((Event e) -> {

                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    Gdx.app.exit();
                }

                return false;
                
            }

        );
       
        uiTable.row().expandY();
        uiTable.add(startButton);
        uiTable.add(quitButton);
            
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}

    @Override
    public void update(float dt) {}
    
}
