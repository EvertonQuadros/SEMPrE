/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Spaceship extends BaseActor {
    
    private final Thrusters thrusters;
    private final Shield shield;
    public int shieldPower;
    
    public Spaceship(float x, float y, Stage s) {
        
        super(x, y, s);
        
        thrusters = new Thrusters(0, 0, s);
        addActor(thrusters);

        shield = new Shield(0, 0, s);
        addActor(shield);
        
        init();
        
    }
    
    private void init(){
        
        loadTexture("spaceship.png");
        setBoundaryPolygon(8);
        
        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(10);
        
        shield.centerAtPosition(getWidth() /2, getHeight() /2);
        thrusters.centerAtPosition(-thrusters.getWidth() / 2, getHeight() /2);
        
        shieldPower = 100;
        
    }
    
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        float degressPerSecond = 120; //rotation speed
        
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            rotateBy(degressPerSecond * dt);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            rotateBy(-degressPerSecond * dt);
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){
            
            accelerateAtAngle(getRotation());
            thrusters.setVisible(true);
            
        }
        else{
            thrusters.setVisible(false);
        }
        
        shield.setOpacity(shieldPower / 100f);
        
        if (shieldPower <= 0){
            shield.setVisible(false);
        }
        
        applyPhysics(dt);
        wrapAroundWorld();
        
    }
    
    public void warp(){
        
        if(getStage() == null){
            return;
        }
        
        Warp warp1 = new Warp(0, 0, this.getStage());
        warp1.centerAtActor(this);
        this.setPosition(MathUtils.random(800), MathUtils.random(600));
        
        Warp warp2 = new Warp(0, 0, this.getStage());
        warp2.centerAtActor(this);
        
    }
    
    public void shoot(){
        
        if(getStage() == null){
            return;
        }
        
        Laser laser = new Laser(0f, 0f, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());
        
    }
    
}
