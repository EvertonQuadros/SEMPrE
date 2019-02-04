/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Smoke extends BaseActor{
    
    public Smoke(float x, float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    public Smoke(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        init();
        
    }
    
    private void init(){
        
        loadTexture("smoke.png");
        addAction(Actions.fadeOut(0.5f));
        addAction(Actions.after(Actions.removeActor()));
        
    }
    
}
