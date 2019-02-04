/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.effects;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Light extends BaseActor{
 
    public Light(Float x, Float y, Stage stage, String face, Color color) {
        
        super(x, y, stage);
        
        init(face, color);
        
    }
   
    private void init(String face, Color color){
        
        String type = "";
        
        if(face.equals("South")){
            type = type.concat("_front.png");
        }
        else{
            type = type.concat("_side.png");
        }

        loadTexture("actors"
                .concat(System.getProperty("file.separator")
                .concat("light")
                .concat(type)));
   
        this.setColor(color);
        
    }
    
}
