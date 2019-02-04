/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.rectangledestroyer;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Ball extends BaseActor{

    private boolean paused;
    
    public Ball(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){

        loadTexture("ball.png");
        setSpeed(400);
        setMotionAngle(90);
        setBoundaryPolygon(12);
        paused = true;
        
        
    }
    
    public boolean isPaused(){
        return paused;
    }
    
    public void setPaused(boolean bool){
        this.paused = bool;
    }

    public void bounceOff(BaseActor other){
        
        Vector2 vector = this.preventOverlap(other);
        
        if(Math.abs(vector.x) >= Math.abs(vector.y)){ //horizontal bounce;
            this.getVelocityVec().x *= -1;
        }
        else{ //vertical bounce;
            this.getVelocityVec().y *= -1;
        }
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt);
        
        if(!isPaused()){
            
            //simulate gravity;
            setAcceleration(10);
            accelerateAtAngle(270);
            applyPhysics(dt);
            
        }

    }
    
}
