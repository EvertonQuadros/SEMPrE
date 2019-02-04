/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actions;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;

/**
 *
 * @author Not275
 */
public class SetAnimationAction extends Action{

    protected Animation animationToDisplay;
    
    public SetAnimationAction(Animation a){
        animationToDisplay = a;
    }
    
    @Override
    public boolean act(float delta) {
    
        BaseActor baseActor = (BaseActor) target;
        baseActor.setAnimation(animationToDisplay);
        return true;
        
    }
    
}
