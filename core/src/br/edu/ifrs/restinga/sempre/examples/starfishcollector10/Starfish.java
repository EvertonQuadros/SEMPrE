/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector10;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Starfish extends BaseActor{
    
    private final String fileName = "starfish.png";
 
    private boolean collected;

    public Starfish(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init();

    }
  
    //used to substitute call in method from constructor (to solve IDE warning);
    private void init(){
        
        loadTexture(fileName);
        
        collected = false;

        Action spin = Actions.rotateBy(30, 1);
        this.addAction(Actions.forever(spin));
        
        setBoundaryPolygon(8);
        
    }
    
    public boolean isCollected(){
        return collected;
    }
    
    public void collect(){
        
        collected = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
        
    }

}
