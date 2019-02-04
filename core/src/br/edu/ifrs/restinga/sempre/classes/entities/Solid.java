/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Solid extends BaseActor{
    
    private boolean enabled;
    
    public Solid(float x, float y, Stage stage){
        
        super(x, y, stage);
        
        init(672,32);
        
    }
    
    public Solid(float x, float y, float width, float height, Stage stage){
        
        super(x, y, stage);
        
        init(width, height);
        
    }
    
    public Solid(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init((float)properties.get("width"), (float)properties.get("height")); 
        
    }
    
    private void init(float width, float height){
     
        setSize(width, height);
        
        enabled = true;
        
        setBoundaryRectangle();
        
    }
    
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
    
    public boolean isEnabled(){
        return enabled;
    }
    
}
