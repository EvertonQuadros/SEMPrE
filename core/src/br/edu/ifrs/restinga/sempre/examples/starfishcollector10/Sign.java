/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector10;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.TilemapActor;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Sign extends BaseActor{
    
    private String text;
    private boolean viewing;
    
    public Sign (Float x, Float y, Stage stage){
        
        super(x, y, stage);
        
        init(" ");
        
    }
    
    public Sign (MapProperties properties, Stage stage){
        
        super((Float) properties.get("x"),
              (Float) properties.get("y"), stage);

        init((String) properties.get("message"));
        
    }
    
    private void init(String message){
        
        loadTexture("sign.png");
        text = message;
        viewing = false;
        
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public String getText(){
        return text;
    }
    
    public void setViewing(boolean viewing){
        this.viewing = viewing;
    }
    
    public boolean isViewing(){
        return viewing;
    }
   
}
