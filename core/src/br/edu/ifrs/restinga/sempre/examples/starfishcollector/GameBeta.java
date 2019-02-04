/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public abstract class GameBeta extends Game{

    protected Stage mainStage;
    
    @Override
    public void create() {
        
        mainStage = new Stage();
        initialize();
        
    }
    
    public abstract void initialize();
    
    @Override
    public void render(){
        
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);
        
        update(dt);
        
        BaseGame.util.clearScreen();
        mainStage.draw();
        
    }
    
    public abstract void update(float dt);
    
}
