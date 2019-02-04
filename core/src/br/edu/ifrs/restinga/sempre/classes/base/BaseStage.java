/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class BaseStage extends Stage{

    private boolean pause = false;

    public BaseStage(BaseScreen screen) {
        super();
    }
    
    public void pauseStage(){
        pause = true;
    }
    
    public void unPauseStage(){
        pause = false;
    }
    
    public boolean isPaused(){
        return pause;
    }
    
//    @Override
//    public void act() { //method not used in SEMPRE
//        
//        if(!isPaused()){
//            super.act(); //To change body of generated methods, choose Tools | Templates.
//        }
//        
//    }
//    
    @Override
    public void act(float delta) {
        
        if(!isPaused()){
            super.act(delta); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
