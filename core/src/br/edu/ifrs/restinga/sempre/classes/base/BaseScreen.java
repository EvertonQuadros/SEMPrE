/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author Not275
 */
public abstract class BaseScreen implements Screen, InputProcessor{
    
    protected BaseStage mainStage;
    protected BaseStage uiStage;
    protected Table uiTable;

    public BaseScreen(){
        init();
    }
    
    private void init(){
        initialize();
    }
    
    public void initialize(){
        
        mainStage = new BaseStage(this);
        uiStage = new BaseStage(this);
        
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        
        initActors();
        initComponents();
        initSounds();
        initScenes();
        
    }

    public BaseStage getMainStage(){
        return mainStage;
    }
    
    public abstract void initActors();
    
    public abstract void initComponents();
    
    public abstract void initSounds();
    
    public abstract void initScenes();
    
    public abstract void update(float dt);
    
    @Override
    public void render (float dt){
        
        uiStage.act(dt);
        mainStage.act(dt);
        
        update(dt);
        
        BaseGame.util.clearScreen();
        
        mainStage.draw();
        uiStage.draw();
        
    }
    
    //methods required by Screen interface
    @Override
    public void resize(int width, int height){}
    
    @Override
    public void pause(){}
    
    @Override
    public void resume(){}
    
    @Override
    public void dispose(){}
    
    @Override
    public void show(){
    
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
       
    }
    
    @Override
    public void hide(){
       
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
     
    }

    @Override
    public boolean keyDown(int keycode) {return false;}

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {return false;}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

    @Override
    public boolean mouseMoved(int screenX, int screenY) {return false;}

    @Override
    public boolean scrolled(int amount) {return false;}
    
    public boolean isTouchDownEvent(Event e){
        return (e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown);
    }
    
}
