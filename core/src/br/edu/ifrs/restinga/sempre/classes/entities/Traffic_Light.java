/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import br.edu.ifrs.restinga.sempre.classes.effects.Light;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Traffic_Light extends Entity{
    
    private Color color;
    
    private String face;
    
    private float timer;
    
    private Light light;
    
    private final float traffic_timer = 5;
    
    public Traffic_Light(MapProperties properties, Stage stage, String face, String texture, Color color) {
        
        super(properties, stage, texture);
        
        init(face, color);
        
    }
    
    private void init(String face, Color color){
        
        timer = 0;
        
        this.face = face;
        
        this.color = color;
 
        light = new Light(getX(), getY(), getStage(), face, color);
        light.centerAtPosition(getWidth() / 2, getHeight() /2);
        
        setLightPosition();
        
        this.addActor(light);
        
    }

    public String getFace(){
        return face;
    }
    
    public Light getLight(){
        return light;
    }
    
    private void setLightPosition(){
        
        float x;
        float y;
        
        if(face.equals("South")){
            
            x = 6;
            
            if(color.equals(Color.GREEN)){
                y = 74; 
            }
            else if(color.equals(Color.RED)){
                y = 89;
            }
            else{
                y = 82;
            }
            
        }
        else{
            
            x = 1;
            
            if(color.equals(Color.GREEN)){
                y = 74;
            }
            else if(color.equals(Color.RED)){
                y = 91;
            }
            else{
                y = 83;
            }//mudar isso;
            
        }
    
        light.setPosition(x, y);
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        timer += dt;
        
        if(timer > traffic_timer){
                
            timer = 0;

            if(color.equals(Color.RED)){
                color = Color.GREEN;
            }
            else if(color.equals(Color.GREEN)){
                color = Color.GOLD;
            }
            else{ //color = yellow
                color = Color.RED;
            }

            light.setColor(color);

            setLightPosition();
               
        }
        
    }
    
}
