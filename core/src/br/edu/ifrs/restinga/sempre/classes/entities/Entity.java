/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import br.edu.ifrs.restinga.sempre.classes.actors.Persona;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Entity extends BaseActor {
    
    private Solid solid;
    
    public Entity(MapProperties properties, Stage stage, String texture) {
        
        super(properties, stage);
        init(texture, properties);
        
    }
    
    public Solid getSolid(){
        return solid;
    }
    
    private void init(String texture, MapProperties properties){
        
        loadTexture("actors"
                .concat(System.getProperty("file.separator"))
                .concat(texture));
  
        setBoundaryRectangle();
        
        solid = new Solid(properties, this.getStage());
        
    }
 
}
