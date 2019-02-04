/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.utils.Align;

/**
 *
 * @author Not275
 */
public class DialogBox extends BaseActor {
    
    private Label dialogLabel;
    private float padding = 16;
    public String texture = null;
    
    public DialogBox(float x, float y, Stage stage, String fileName){
        
        super(x, y, stage);
        
        texture = fileName;
        
        init();
     
    }
    
    private void init(){
   
        loadTexture(texture);
  
        dialogLabel = new Label(" ", BaseGame.getLabelStyle());
        
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel.setPosition(padding, padding);
        
        this.setDialogSize(getWidth(), getHeight());
        this.addActor(dialogLabel);
        
    }
    
    public void setPadding(float size){
       
        if(size < 0){
            padding = 0;
        }
        else{
            padding = size;
        }
        
    }
    
    public void setDialogSize(float width, float height){
        
        this.setSize(width, height);
        dialogLabel.setWidth(width - (2 * padding));
        dialogLabel.setHeight(height - (2 * padding));     
        
    }

    public Label getDialogLabel() {
        return dialogLabel;
    }

    public void setDialogLabel(Label dialogLabel) {
        this.dialogLabel = dialogLabel;
    }
 
    public void setText(String text){
        dialogLabel.setText(text);
    }
    
    public String getText(){
        return dialogLabel.getText().toString();
    }
    
    public void setFontScale(float scale){
        dialogLabel.setFontScale(scale);
    }
    
    public void setFontColor(Color color){
        dialogLabel.setColor(color);
    }
    
    public void setBackgroundColor(Color color){
        this.setColor(color);
    }
    
    public void alignTopLeft(){
        dialogLabel.setAlignment(Align.topLeft);
    }
    
    public void alignTopRight(){
        dialogLabel.setAlignment(Align.topRight);
    }
    
    public void alignCenter(){
        dialogLabel.setAlignment(Align.center);
    }

}
