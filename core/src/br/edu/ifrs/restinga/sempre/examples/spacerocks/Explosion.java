/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Explosion extends BaseActor{
    
    public Explosion(Float x, Float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    private void init(){
        loadAnimationFromSheet("explosion.png", 6, 6, 0.03f, false);
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        if(isAnimationFinished()){
            this.remove();
        }
        
    }
    
     
    
}
