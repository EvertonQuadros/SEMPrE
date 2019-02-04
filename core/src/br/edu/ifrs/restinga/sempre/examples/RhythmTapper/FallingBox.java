/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.RhythmTapper;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class FallingBox extends BaseActor{
    
    public FallingBox(float x, float y, Stage stage){
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        loadTexture("box.png");
        setScale(0.75f, 0.75f);
        
    }
    
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        applyPhysics(dt);
        
    }
    
    public void flashOut(){
        
        float duration = 0.25f;
        Action flashOut = 
                Actions.parallel(Actions.scaleTo(1.5f, 1.5f, duration), 
                                 Actions.color(Color.WHITE, duration), 
                                 Actions.fadeOut(duration));
        
        addAction(flashOut);
        addAction(Actions.after(Actions.removeActor()));
        
    }
    
}
