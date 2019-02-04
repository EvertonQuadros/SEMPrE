/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

/**
 *
 * @author Not275
 */
public class CustomLabel extends Label{
    
     public CustomLabel(CharSequence text){
        super(text, BaseGame.getLabelStyle());
    }
    
    public CustomLabel(CharSequence text, LabelStyle labelStyle) {
        super(text, labelStyle);
    }
    
    public CustomLabel(CharSequence text, String tooltipText){
        
        super(text, BaseGame.getLabelStyle());
        init(tooltipText);
        
    }
    
    public CustomLabel(CharSequence text, String tooltipText, LabelStyle labelStyle){
        
        super(text, labelStyle);
        init(tooltipText);
        
    }

    private void init(String tooltipText){
        setToolTip(tooltipText);
    }
    
    public void setToolTip(String toolTipText){
        addListener(new TextTooltip(toolTipText, new CustomToolTipStyle()));
    }
    
}
