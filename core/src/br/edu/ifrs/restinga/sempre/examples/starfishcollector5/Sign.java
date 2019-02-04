/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector5;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Sign extends BaseActor{
    
    private String text;
    private boolean viewing;
    
    public Sign (float x, float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    private void init(){
        
        loadTexture("sign.png");
        text = " ";
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
