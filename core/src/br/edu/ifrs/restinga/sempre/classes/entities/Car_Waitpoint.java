/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Car_Waitpoint extends Solid{
    
    private String orientation;
    private boolean active;
    
    public Car_Waitpoint(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
 
    private void init(MapProperties properties){
        
        orientation = (String)properties.get("orientation");
        
    }
 
    public String getOrientation(){
        return orientation;
    }

    public boolean isActive(){
       return active; 
    }
    
    @Override
    public void act(float dt){
            
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        for(BaseActor actor : BaseActor.getList(getStage(), Crossing.class)){
            
            Crossing crossing = (Crossing)actor;
            
            if(crossing.getOrientation().equals(getOrientation())){
                active = crossing.getSolid().getColor().equals(Color.RED);
            }
            
        }
        
//        for(BaseActor actor : BaseActor.getList(getStage(), Traffic_Light.class)){
//            
//            Traffic_Light trafficLight = (Traffic_Light)actor;
//            
//            if(trafficLight.getFace().equals(getOrientation())){
//                active = trafficLight.getLight().getColor().equals(Color.RED);
//            }
//     
//        }
    
    }
    
}
