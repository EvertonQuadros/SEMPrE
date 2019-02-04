/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


/**
 *
 * @author Not275
 */
public class CustomTextButton extends Button{
   
    private Label buttonLabel;
    private CustomLabelStyle labelStyle;
    
    public CustomTextButton(String text) {
        
        super(new CustomTextButtonStyle());
        
        init(text);
        
    }
 
    public CustomTextButton(ButtonStyle buttonStyle, String text) {
        
        super(buttonStyle);
        
        init(text);
        
    }
    
    private void init(String text){
        
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 20;
        parameters.color = Color.BLACK;
       
        
        labelStyle = new CustomLabelStyle();
        
        labelStyle.setParameters(parameters);
       
        buttonLabel = new Label(text, labelStyle);
      
        add(buttonLabel);
        
    }
    
    public Label getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(Label buttonLabel) {
        this.buttonLabel = buttonLabel;
    }
    
    public void addPressEffect(){
        addAction(Actions.sequence(Actions.color(Color.GRAY, 0.1f),  
                                   Actions.color(Color.WHITE, 0.1f)));
               
    }
    
}
