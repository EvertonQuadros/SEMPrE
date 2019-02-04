/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Shield extends BaseActor{
    
    public Shield(float x, float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    private void init(){
        
        loadTexture("shields.png");
        
        Action pulse = Actions.sequence(Actions.scaleTo(1.05f, 1.05f, 1), Actions.scaleTo(0.95f, 0.95f, 1));
        addAction(Actions.forever(pulse));
        
    }
    
}
