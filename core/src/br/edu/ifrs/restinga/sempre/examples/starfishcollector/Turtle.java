/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 *
 * @author Not275
 */
public class Turtle extends ActorBeta{
    
    public Turtle(String name) {
        super(name);
    }
    
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            this.moveBy(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            this.moveBy(1, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){
            this.moveBy(0, 1);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            this.moveBy(0, -1); 
        }
        
    }
    
}
