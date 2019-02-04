/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class NPC extends BaseActor{
    
    //text to be displayed
    private String text;
    //ID used to specific graphics and identifying NPcs with dynamic messages;
    private String id;
    
    private boolean viewing;
    
    
    
    public NPC(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        init(properties);
        
    }
    
    private void init(MapProperties properties){
      
        SetId((String)properties.get("id"));
        setText((String)properties.get("text"));
 
        viewing = false;
        
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isViewing() {
        return viewing;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }
    
    public void SetId(String id){
        
        this.id = id;
        
        if(id.equals("Gatekeeper")){
            loadTexture("npc-1.png");
        }
        else if(id.equals("Shopkeeper")){
            loadTexture("npc-2.png");
        }
        else{ //default image
            loadTexture("npc-3.png");
        }
        
        
    }
    
    public String getID(){
        return id;
    }
    
}
