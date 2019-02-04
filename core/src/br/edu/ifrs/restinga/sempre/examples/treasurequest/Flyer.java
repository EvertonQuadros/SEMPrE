/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Flyer extends BaseActor{
    
    public Flyer(float x, float y, Stage stage){
        super(x, y, stage);
    }
    
    public Flyer(MapProperties properties, Stage stage) {
        
        super(properties, stage);
    
        init();
    
    }
    
    protected void init(){
        
        loadAnimationFromSheet("enemy-flyer.png", 1, 4, 0.0f, true);
        setSize(48, 48);
        setBoundaryPolygon(6);
        
        setSpeed(MathUtils.random(50, 80));
        setMotionAngle(MathUtils.random(0, 360));
        
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        if(MathUtils.random(1, 120) == 1){
            setMotionAngle(MathUtils.random(0, 360));
        }
        
        applyPhysics(dt);
        boundToWorld();
        
    }
    
}
