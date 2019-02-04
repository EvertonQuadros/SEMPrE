/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Not275
 */
public class Hero extends BaseActor{
    
    private Animation north;
    private Animation south;
    private Animation east;
    private Animation west;
    private float facingAngle;

    public Hero(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init();
        
    }
    
    private void init(){
        
        int rows = 4;
        int cols = 4;
        
        Texture texture = 
                new Texture(BaseGame.util.getInternalFileHandle("hero.png"), true);
        
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        float frameDuration  = 0.2f;
        
        TextureRegion[][] textureRegion
                = TextureRegion.split(texture, frameWidth, frameHeight);
        
        Array<TextureRegion> textureArray = new Array();
 
        for(int row = 0; row < rows; row++){
        
            for(int col = 0; col < cols; col++){
                textureArray.add(textureRegion[row][col]);
            }
 
            if(row == 0){
                south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(row == 1){
                west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(row == 2){
                east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else{
                north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }

            textureArray.clear();
            
        }
        
        setAnimation(south);
        facingAngle = 270;
        
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        
    }

    public float getFacingAngle(){
        return facingAngle;
    }
    
    public void getHit(){
        addAction(Actions.repeat(5, Actions.sequence(Actions.color(Color.RED, 0.1f),Actions.color(Color.WHITE))));
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        //pause animation when character is not moving;
        if(getSpeed() == 0){
            setAnimationPaused(true);
        }
        else{
     
            setAnimationPaused(false);
            
            //set direction animation
            float angle = getMotionAngle();
            if(angle >= 45 && angle <= 135){
                
                facingAngle = 90;
                setAnimation(north);
                
            }
            else if(angle >= 135 && angle < 225){
                
                facingAngle = 180;
                setAnimation(west);
                
            }
            else if(angle >= 225 && angle <= 315){
                
                facingAngle = 270;
                setAnimation(south);
                
            }
            else{
                
                facingAngle = 0;
                setAnimation(east);
                
            }
            
        }
     
        alignCamera();
        boundToWorld();
        applyPhysics(dt);
        
    }
    
}
    

