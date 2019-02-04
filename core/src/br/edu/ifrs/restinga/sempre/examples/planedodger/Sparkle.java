/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.planedodger;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Sparkle extends BaseActor{

    public Sparkle(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        loadAnimationFromSheet("sparkle.png", 8, 8, 0.02f, false);
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        if(isAnimationFinished()){
            remove();
        }
        
    }
    
}
