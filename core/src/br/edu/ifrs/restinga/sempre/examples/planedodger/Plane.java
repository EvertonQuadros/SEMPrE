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
public class Plane extends BaseActor {

    public Plane(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        String[] fileNames = {"planeGreen0.png","planeGreen1.png","planeGreen2.png","planeGreen1.png"};
        
        loadAnimationFromFiles(fileNames, 0.1f, true);
        
        setMaxSpeed(800);
        setBoundaryPolygon(8);
        
    }

    @Override
    public void act(float dt) {
        
        super.act(dt);
        
        //simulation of gravity force
        setAcceleration(800);
        accelerateAtAngle(270);
        
        applyPhysics(dt);
        
        //stop plane from passing through the ground;
        for(BaseActor ground : BaseActor.getList(this.getStage(), Ground.class)){
            
            if(this.overlaps(ground)){
                
                setSpeed(0);
                preventOverlap(ground);
                
            }
            
        }
        
        //stop plane from past top of screen;
        if(getY() + getHeight() > getWorldBounds().getHeight()){
            
            setSpeed(0);
            boundToWorld();
            
        }
        
    }
    
    public void boost(){
            
        setSpeed(300);
        setMotionAngle(90);

    }
    
}
