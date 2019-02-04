/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Not275
 */
public class SceneSegment {
    
    private Actor actor;
    private Action action;
    
    public SceneSegment(Actor actor, Action action){
        
        this.actor = actor;
        this.action = action;
        
    }
    
    public void start(){
        
        actor.clearActions();
        actor.addAction(action);
        
    }
 
    public boolean isFinished(){
        return (actor.getActions().size == 0);
    }
    
    public void finish(){
        
        //simulate 100000 seconds elapsed time to complete in-progress action;
        if(actor.hasActions()){
            actor.getActions().first().act(100000);
        }
        
        actor.clearActions();
        
    }
    
}
