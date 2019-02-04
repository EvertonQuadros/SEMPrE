/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Warp extends BaseActor{
    
    public Warp (float x, float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    private void init(){
        
        loadAnimationFromSheet("warp.png", 4, 8, 0.05f, true);
        
        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
        
    }
    
}
