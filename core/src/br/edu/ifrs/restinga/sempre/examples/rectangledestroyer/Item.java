/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.rectangledestroyer;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Item extends BaseActor{

    public enum Type {PADDLE_EXPAND, PADDLE_SHRINK,
                      BALL_SPEED_UP, BALL_SPEED_DOWN};
    
    private Type type;
    
    
    public Item(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        setRandomType();
        
        setSpeed(100);
        setMotionAngle(270);
        
        setSize(50, 50);
        setOrigin(25, 25);
        setBoundaryRectangle();
        
        setScale(0, 0);
        addAction(Actions.scaleTo(1, 1, 0.25f));
        
    }
    
    public void setType(Type type){
        
        this.type = type;
        
        String itemPath = "items".concat(System.getProperty("file.separator"));
        
        if(type == Type.PADDLE_EXPAND){
            loadTexture(itemPath.concat("paddle-expand.png"));
        }
        else if(type == Type.PADDLE_SHRINK){
            loadTexture(itemPath.concat("paddle-shrink.png"));
        }
        else if(type == Type.BALL_SPEED_UP){
            loadTexture(itemPath.concat("ball-speed-up.png"));
        }
        else if(type == Type.BALL_SPEED_DOWN){
            loadTexture(itemPath.concat("ball-speed-down.png"));
        }
        else{
            loadTexture(itemPath.concat("item-blank.png"));
        }
        
    }
    
    public void setRandomType(){
        setType(Type.values()[MathUtils.random(0, Type.values().length - 1)]);
    }
    
    public Type getType(){
        return type;
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        applyPhysics(dt);
        
        if(getY() < -50){
            remove();
        }
        
    }
    
}
