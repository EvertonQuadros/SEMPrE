/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector10;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Rock extends BaseActor{
    
    private final String fileName = "rock.png";
    
    public Rock(Float x, Float y, Stage s) {
     
        super(x, y, s);
  
        init();
        
    }
    
    public Rock(MapProperties properties, Stage stage){
        
        super((Float)properties.get("x"),
              (Float)properties.get("y"), stage);
    
        init((float)properties.get("width"), (float)properties.get("height"));
       
    }
    
    private void init(){
        
        loadTexture(fileName);
        setBoundaryPolygon(8);
        
    }
    
    private void init(float width, float height){
        
        loadTexture(fileName);
        
        setWidth(width);
        setHeight(height);
        setBoundaryPolygon(8);
        
    }
    
}
