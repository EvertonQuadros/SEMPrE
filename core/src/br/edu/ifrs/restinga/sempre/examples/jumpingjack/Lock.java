/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jumpingjack;

import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Lock extends Solid{
    
    public Lock(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        loadTexture("items".concat(System.getProperty("file.separator"))
                           .concat("lock.png"));
        
        setColor((String)properties.get("color"));
        
    }
    
}
