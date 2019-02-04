/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jumpingjack;

import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Koala extends BaseActor{
    
    private Animation stand;
    private Animation walk;
    private Animation jump;
    
    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float maxVerticalSpeed;
    private float gravity;
    private float jumpSpeed;
    
    private BaseActor belowSensor;
    
    private final String path = "koala".concat(System.getProperty("file.separator"));
    
    public Koala(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init();
        
    }
    
    private void init(){
        
        stand = loadTexture(path.concat("stand.png"));
        
        String[] walkFileNames = {path.concat("walk-1.png"),
                                  path.concat("walk-2.png"),
                                  path.concat("walk-3.png"),
                                  path.concat("walk-2.png")};
     
        walk = loadAnimationFromFiles(walkFileNames, 0.2f, true);
        
        jump = loadTexture(path.concat("jump.png"));
        
        walkAcceleration = 800;
        walkDeceleration = 800;
        maxHorizontalSpeed = 200;
        maxVerticalSpeed = 1000;
        gravity = 700;
        jumpSpeed = 450;
        setBoundaryPolygon(6);
        
        belowSensor = new BaseActor(0f, 0f, this.getStage());
        belowSensor.loadTexture("white.png");
        belowSensor.setSize(this.getWidth() - 8, 8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(true);
        
    }

    public boolean belowOverlaps(BaseActor other){
        return belowSensor.overlaps(other);
    }
    
    public boolean isOnSolid(){
 
        for(BaseActor actor : BaseActor.getList(getStage(), Solid.class)){
           
            Solid solid = (Solid) actor;

            if(belowOverlaps(solid) && solid.isEnabled()){
                return true;
            }
            
        }
        
        return false;
        
    }
    
    public void jump(){
        getVelocityVec().y = jumpSpeed;
    }
    
    public boolean isFalling(){
        return (getVelocityVec().y < 0);
    }
 
    public void spring(){
        getVelocityVec().y = 1.5f * jumpSpeed;
    }
    
    public boolean isJumping(){
        return (getVelocityVec().y > 0);
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            getAccelerationVec().add(-walkAcceleration, 0);
        }
        
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            getAccelerationVec().add(walkAcceleration, 0);
        }
        
        getAccelerationVec().add(0, -gravity);
        
        getVelocityVec().add(getAccelerationVec().x * dt, getAccelerationVec().y * dt);
        
        if(!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)){
            
            float decelerationAmount = walkDeceleration * dt;
            float walkDirection;
            
            if(getVelocityVec().x > 0){
                walkDirection = 1;
            }
            else{
                walkDirection = -1;
            }
            
            float walkSpeed = Math.abs(getVelocityVec().x);
            
            walkSpeed -= decelerationAmount;
            
            if(walkSpeed < 0){
                walkSpeed = 0;
            }
            
            getVelocityVec().x = walkSpeed * walkDirection;
            
        }
        
        getVelocityVec().x = MathUtils.clamp(getVelocityVec().x, -maxHorizontalSpeed, maxHorizontalSpeed);
        getVelocityVec().y = MathUtils.clamp(getVelocityVec().y, -maxVerticalSpeed, maxVerticalSpeed);
        
        moveBy(getVelocityVec().x * dt, getVelocityVec().y * dt);
        getAccelerationVec().set(0, 0);
        
        belowSensor.setPosition(getX() + 4, getY() - 8);
        
        alignCamera();
        boundToWorld();
        
        if(this.isOnSolid()){
           
            belowSensor.setColor(Color.GREEN);
            
            if(getVelocityVec().x == 0){
                setAnimation(stand);
            }
            else{
                setAnimation(walk); 
            }
            
        }
        else{
            belowSensor.setColor(Color.RED);
            setAnimation(jump);
            
        }
        
        if(getVelocityVec().x > 0){ // face right
            setScaleX(1);
        }
        
        if(getVelocityVec().x < 0){ // face left
            setScaleX(-1);
        }
        
    }

    @Override
    public void boundToWorld() {
       
        //check left edge
        if(getX() < 0){
            setX(0);
        }
        
        //check right edge
        if(getX() + getWidth() > getWorldBounds().width){
            setX(getWorldBounds().width - getWidth());
        }
        
        //check bottom edge //no check bottom (falloff game over)
//        if(getY() < 0){
//            setY(0);
//        }
        
        //check top edge
        if(getY() + getHeight() >getWorldBounds().height){
            setY(getWorldBounds().height - getHeight());
        }
        
    }
    
}
