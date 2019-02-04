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
public class Key extends BaseActor{
    
    public Key(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        loadTexture("items".concat(System.getProperty("file.separator")).concat("key.png"));
        rotateBy(10);
        
        setColor((String)properties.get("color"));
        
        Action tilt = Actions.sequence(Actions.rotateBy(-20, 0.5f),
                                       Actions.rotateBy(20, 0.5f));
        
        addAction(Actions.forever(tilt));
        
    }
    
}
