/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Rock extends BaseActor{
    
    public Rock (Float x, Float y, Stage stage){
    
        super(x, y, stage);
                
        init();
        
    }

    private void init(){

        loadTexture("rock.png");
        
        float random = MathUtils.random(30);
        
        addAction(Actions.forever(Actions.rotateBy(30 + random, 1)));
        
        setSpeed(50 + random);
        setMaxSpeed(50 + random);
        setDeceleration(0);
        
        setMotionAngle(MathUtils.random(360));
        
    }    

    @Override
    public void act(float dt) {
    
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        applyPhysics(dt);
        wrapAroundWorld();
    
    }
    
}
