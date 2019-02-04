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
public class Laser extends BaseActor{
    
    public Laser(Float x, Float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    private void init(){
        
        loadTexture("laser.png");
        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
        
        setSpeed(400);
        setMaxSpeed(400);
        setDeceleration(0);
     
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        applyPhysics(dt);
        wrapAroundWorld();
        
    }
    
}
