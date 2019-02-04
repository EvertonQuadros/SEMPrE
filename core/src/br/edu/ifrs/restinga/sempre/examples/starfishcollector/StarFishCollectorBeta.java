/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class StarFishCollectorBeta extends GameBeta {
    
    ArrayList<ActorBeta> actors = new ArrayList();

    private final String assetsPath = "starfishcollector".concat(File.separator);
    
    private Turtle turtle;
    private ActorBeta starFish;
    private ActorBeta ocean;
    private ActorBeta winMessage;
    private ActorBeta gameOverMessage;
    private ActorBeta shark;
    
    private void initializeActors(){
    
        for(ActorBeta actor : actors){

            if(actor.getActorName().toLowerCase().contains("starfish")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("starfish.png"))));
            }
            else if(actor.getActorName().toLowerCase().contains("ocean")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("water.jpg"))));
            }
            else if(actor.getActorName().toLowerCase().contains("winmessage")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("you-win.png"))));
            }
            else if(actor.getActorName().toLowerCase().contains("gameovermessage")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("game-over.png"))));
            }
            else if(actor.getActorName().toLowerCase().contains("turtle")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("turtle-1.png"))));
            }
            else if(actor.getActorName().toLowerCase().contains("shark")){
                actor.setTexture(new Texture(Gdx.files.internal(assetsPath.concat("sharky.png"))));
            }
            
            mainStage.addActor(actor);
            
        }

    }

    @Override
    public void initialize() {
        
        ocean = new ActorBeta("ocean");

        starFish = new ActorBeta("starFish");
        starFish.setPosition(380, 380);
        
        turtle = new Turtle("turtle");
        turtle.setPosition(20, 20);
        
        winMessage = new ActorBeta("winMessage");
        winMessage.setPosition(180, 180);
        winMessage.setVisible(false);
        
        gameOverMessage = new ActorBeta("gameOverMessage");
        gameOverMessage.setPosition(180, 180);
        gameOverMessage.setVisible(false);
        
        actors.add(ocean);
        actors.add(starFish);
        
        for(int i = 0; i < 5; i++){
            
            ActorBeta actor = new ActorBeta("shark");
            actor.setPosition((float)(Math.random() * 521 + 100), (float)(Math.random() * 230 + 50));
            actors.add(actor);
            
        }
        
        actors.add(turtle);
        actors.add(winMessage);
        actors.add(gameOverMessage);
        
        initializeActors();
        
    }

    @Override
    public void update(float dt) {

        if(turtle.overlaps(starFish)){
            
            starFish.remove();
            turtle.remove();
            winMessage.setVisible(true);
        
        }
        else{
            
            for(ActorBeta actor : actors){
                
                if(actor.getActorName().contains("shark")){
                    
                    if(turtle.overlaps(actor)){
                        
                        turtle.remove();
                        gameOverMessage.setVisible(true);
                        break;
                        
                    }
                    
                }
                
            }
            
        }

    }
    
}
