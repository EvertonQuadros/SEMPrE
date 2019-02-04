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
public class Enemy extends BaseActor{

    public Enemy(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        String[] fileNames = {"planeRed0.png","planeRed1.png","planeRed2.png","planeRed1.png"};
        
        loadAnimationFromFiles(fileNames, 0.1f, true);
        
        setSpeed(100);
        setMotionAngle(180);
        setBoundaryPolygon(8); 
        
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        applyPhysics(dt);
        
    }
    
}
