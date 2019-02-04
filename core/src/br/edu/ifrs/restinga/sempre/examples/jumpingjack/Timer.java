/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jumpingjack;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Timer extends BaseActor{
    
    public Timer(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init();
        
    }
    
    private void init(){
        
        loadTexture("items".concat(System.getProperty("file.separator")).concat("timer.png"));
        
        Action pulse = Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.5f),
                                        Actions.scaleTo(1.0f, 1.0f, 0.5f));
        
        addAction(Actions.forever(pulse));
        
    }
    
}
