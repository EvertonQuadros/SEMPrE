/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.missinghomework;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;

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
public class MenuScreen extends BaseScreen{

    BaseActor title;
    
    @Override
    public void initActors() {
        
        BaseActor background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("notebook.jpg");
        background.setSize(800, 600);
        
        title = new BaseActor(0f, 0f, mainStage);
        title.loadTexture("missing-homework.png");
        
    }
    
    @Override
    public void initComponents() {
        
        TextButton startButton = new TextButton("Start", BaseGame.textbuttonStyle);
        TextButton quitButton = new TextButton("Quit", BaseGame.textbuttonStyle);
        
        startButton.addListener(
            
            (Event e) -> {
            
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    BaseGame.setActiveScreen(new StoryScreen());
                }
            
                return false;
                
            }
        
        );
        
        quitButton.addListener(
        
            (Event e) -> {
                
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    Gdx.app.exit();
                }
                
                return false;
                
            }
                
        );
        
        uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}
    
    @Override
    public void update(float dt) {}
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(Gdx.input.isKeyPressed(Keys.ENTER)){
            BaseGame.setActiveScreen(new StoryScreen());
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
            Gdx.app.exit();
        }
        
        return false;
        
    }
    
}
