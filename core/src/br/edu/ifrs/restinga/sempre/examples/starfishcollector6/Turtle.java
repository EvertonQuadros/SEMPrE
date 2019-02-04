/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector6;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Turtle extends BaseActor{
    
    private final String[] fileNames = {"turtle-1.png","turtle-2.png","turtle-3.png",
                          "turtle-4.png","turtle-5.png","turtle-6.png"}; 
    
    public Turtle(float x, float y, Stage s) {
        
        super(x, y, s);

        init();
        
    }
    
    //used to substitute call in method from constructor (to solve IDE warning);
    private void init(){
        
        loadAnimationFromFiles(fileNames,0.1f, true);
        
        setAcceleration(400); //100/400 = 0.25 to get full speed from rest
        setMaxSpeed(100); //100 pixels/second (max speed)
        setDeceleration(400);
        
    }
  
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            accelerateAtAngle(0);
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){
            accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            accelerateAtAngle(270);
        }
        
        applyPhysics(dt);
        
        setAnimationPaused(!isMoving());
        
        if(getSpeed() > 0){
            setRotation(getMotionAngle());  
        }
        
        boundToWorld();
        alignCamera();
        
    }
    
}
