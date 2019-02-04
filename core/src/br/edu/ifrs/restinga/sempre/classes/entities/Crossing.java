/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import br.edu.ifrs.restinga.sempre.classes.actors.Persona;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Crossing extends Solid{
    
    private String orientation;
    
    private boolean carEnabled = false;
    private boolean walkerEnabled = false; 
    
    private Solid solid;
    
    public Crossing(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        orientation = (String)properties.get("orientation");
 
        solid = new Solid(0, 0, getStage());
        solid.loadTexture("white.png");
        solid.setWidth(getWidth() - 30);
        solid.setHeight(getHeight() - 30);
        solid.setColor(Color.RED);
        solid.setVisible(false);
        solid.setBoundaryRectangle();
        solid.centerAtActor(this);
        
    }
    
    public Solid getSolid(){
        return solid;
    }
    
    private void calculateCross(Traffic_Light trafficLight){
        
        if(trafficLight.getLight().getColor().equals(Color.RED)){
            
            carEnabled = false;
            walkerEnabled = true;
            solid.setColor(Color.RED);
            
        }
        else if(trafficLight.getLight().getColor().equals(Color.GREEN)){
             
            boolean enable = true;
            walkerEnabled = false;
            carEnabled = false;
 
            for(BaseActor actor : getList(getStage(), Persona.class)){
                
                if(actor.overlaps(solid)){
                    
                    enable = false;
                    solid.setColor(Color.RED);
                    
                }
                
            }
            
            if(enable){
                
                carEnabled = true;
                solid.setColor(Color.GREEN);
                
            }
            
        }
        else{
            
            carEnabled = false;
            walkerEnabled = false;
            solid.setColor(Color.YELLOW);
            
        }
       
    }

    public String getOrientation(){
        return orientation;
    }
    
    public boolean isCarEnabled(){
        return carEnabled;
    }
    
    public boolean isWalkerEnabled(){
        return walkerEnabled;
    }

    @Override
    public void act(float dt) {
    
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
    
        for(BaseActor actor : BaseActor.getList(this.getStage(), Traffic_Light.class)){

            Traffic_Light trafficLight = (Traffic_Light) actor;

            if(orientation.equals(trafficLight.getFace())){
                calculateCross(trafficLight);
            }
           
        }
        
    }
    
}
