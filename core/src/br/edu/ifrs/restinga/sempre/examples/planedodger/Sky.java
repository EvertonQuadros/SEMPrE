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
public class Sky extends BaseActor{

    public Sky(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        loadTexture("sky.png");
        setSpeed(25);
        setMotionAngle(180);
        
    }
    
    @Override
    public void act(float dt){
        
        super.act(dt);
        applyPhysics(dt);
        
        //if moved completely past left edge of screen:
        // shift right, past other instance.
        
        if(getX() + getWidth() < 0){
            moveBy(2 * getWidth(), 0);
        }
        
        
    }
    
    
    
}
