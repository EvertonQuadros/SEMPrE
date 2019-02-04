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
public class Despawn_Walker extends Solid{
    
    public Despawn_Walker(MapProperties properties, Stage stage) {
        super(properties, stage);
    }

    @Override
    public void act(float dt) {
     
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
    
        for(BaseActor actor : BaseActor.getList(getStage(), Persona.class)){
            
            Persona persona = (Persona)actor;
            
            if(overlaps(persona)){
                    
                persona.deselect();
                actor.remove();
                
            }
            
        }
     
    }
    
}
