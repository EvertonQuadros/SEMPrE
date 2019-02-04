/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Arrow extends BaseActor{
    
    public Arrow(float x, float y, Stage stage){
        
        super(x, y, stage);
        init();
        
    }
    
    public Arrow(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        init();
        
    }
    
    private void init(){
        
        loadTexture("arrow.png");
        setSpeed(400);
        
    }

    @Override
    public void act(float dt) {
        
        super.act(dt);
        applyPhysics(dt);
        
    }
    
}
