/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 * @author Not275
 */
public class CustomProgressBar extends ProgressBar{
    
    private final CustomProgressBarStyle redProgressBarStyle = new CustomProgressBarStyle(Color.BLACK, Color.RED, 200, 25);
    private final CustomProgressBarStyle greenProgressBarStyle = new CustomProgressBarStyle(Color.BLACK, Color.GREEN, 200, 25);
    
    public CustomProgressBar(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, new CustomProgressBarStyle(Color.BLACK, Color.RED, 200, 25));
    }

    @Override
    public boolean setValue(float value) {
        
        if(value < (getMaxValue() * 0.4)){
            setStyle(redProgressBarStyle);
        }
        else{
            setStyle(greenProgressBarStyle);
        }
        
        return super.setValue(value); //To change body of generated methods, choose Tools | Templates.
    }
    
}
