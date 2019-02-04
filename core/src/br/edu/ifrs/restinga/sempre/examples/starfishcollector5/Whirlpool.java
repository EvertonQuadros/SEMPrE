/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector5;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Whirlpool extends BaseActor{
    
    public Whirlpool(float x, float y, Stage s) {
        
        super(x, y, s);
     
        String fileName = "whirlpool.png";
        
        loadFromSheet(fileName);
        
    }
    
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        if(isAnimationFinished()){
            remove();
        }
        
    }
    
    //used to substitute call in method from constructor (to solve IDE warning);
    private void loadFromSheet(String fileName){
        loadAnimationFromSheet(fileName, 2, 5, 0.1f, false);
    }
    
}
