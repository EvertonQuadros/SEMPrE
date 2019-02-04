/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Not275
 */
public class StarFishCollector extends BaseGame {
    
    private SpriteBatch batch;
    private ActorAlpha turtle;
    private ActorAlpha starfish;
    
    private Texture oceanTexture, winMessageTexture;
    private boolean isWinner;
    
    public StarFishCollector(){
        super("starfishcollector");
    }
    
    @Override
    public void create() {
 
        batch = new SpriteBatch();
        turtle = new ActorAlpha("turtle-1.png",20,20);
        starfish = new ActorAlpha("starfish.png",380,380);
        
        oceanTexture = BaseGame.util.getInternalTexture("water.jpg");
        winMessageTexture = BaseGame.util.getInternalTexture("you-win.png");
        
        isWinner = false;
        
    }
    
    @Override
    public void render(){
        
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            turtle.subsActorX(5);
        }
        
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            turtle.addActorX(5); 
        }
        
        if(Gdx.input.isKeyPressed(Keys.UP)){
            turtle.addActorY(5);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            turtle.subsActorY(5);
        }
        
        //update rectangle location
        turtle.getActorRectangle().setPosition(turtle.getActorX(), turtle.getActorY());
        
        //check win condition: turtle must be overlapping starfish
        if(turtle.getActorRectangle().overlaps(starfish.getActorRectangle())){
            isWinner = !isWinner;
        }
        
        //clear screen
        BaseGame.util.clearScreen();
        
        batch.begin();
        batch.draw(oceanTexture, 0, 0);
        
        if(!isWinner){
            
            batch.draw(starfish.getActorTexture(), starfish.getActorX(), starfish.getActorY());
            batch.draw(turtle.getActorTexture(), turtle.getActorX(), turtle.getActorY());
            
        }
        
        if(isWinner){
            
            batch.draw(winMessageTexture, 180, 180);
            
            starfish = new ActorAlpha("starfish.png",(float)(Math.random() * 640),(float)(Math.random() * 480));

            isWinner = !isWinner;
            
        }
        
        batch.end();
        
    }
 
}
