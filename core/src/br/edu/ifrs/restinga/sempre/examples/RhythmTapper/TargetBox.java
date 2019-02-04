/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.RhythmTapper;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 *
 * @author Not275
 */
public class TargetBox extends BaseActor{

    public TargetBox(float x, float y, Stage stage, String letter, Color color) {
        
        super(x, y, stage);
        
        init(letter, color);
        
    }
    
    private void init(String letter, Color color){
        
        loadTexture("box.png");
        setSize(64, 64);
        
        //add a centered label containing letter with given color;
        Label letterLabel = new Label(letter, BaseGame.labelStyleFreeFont);
        letterLabel.setSize(64, 64);
        letterLabel.setAlignment(Align.center);
        letterLabel.setColor(color);
        this.addActor(letterLabel);
        
    }
    
    public void pulse(){
        
        Action pulse = Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 0.05f), 
                                        Actions.scaleTo(1.0f, 1.0f, 0.05f));
        
        addAction(pulse);
        
    }
    
}
