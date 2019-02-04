/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Spaceship spaceship;
    private boolean gameOver = false;

    @Override
    public void update(float dt) {
    
        for(BaseActor rock : BaseActor.getList(mainStage, Rock.class)){
     
            if(rock.overlaps(spaceship)){
                
                if(spaceship.shieldPower <= 0){
                    
                    Explosion boom = new Explosion(0f, 0f, mainStage);
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000, -1000);
                    
                    BaseActor messageLose = new BaseActor(0f, 0f, uiStage);
                    messageLose.loadTexture("message-lose.png");
                    messageLose.centerAtPosition(400, 300);
                    messageLose.setOpacity(0);
                    messageLose.addAction(Actions.fadeIn(1));
                    gameOver = true;
                    
                }
                else{
                    
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0f, 0f, uiStage);
                    boom.centerAtActor(rock);
                    rock.remove();
                  
                }
                
            }
            
            for(BaseActor laser : BaseActor.getList(mainStage, Laser.class)){
            
                if(laser.overlaps(rock)){

                    Explosion boom = new Explosion(0f, 0f, mainStage);
                    boom.centerAtActor(rock);
                    laser.remove();
                    rock.remove();
           
                }
            
            }
            
        }
        
        if(!gameOver && BaseActor.countActors(mainStage, Rock.class) == 0){
            
            BaseActor messageWin = new BaseActor(0f, 0f, uiStage);
            messageWin.loadTexture("message-win.png");
            messageWin.centerAtPosition(400, 300);
            messageWin.setOpacity(0);
            messageWin.addAction(Actions.fadeIn(1));
            gameOver = true;
            
        }
        
    }

    @Override
    public boolean keyDown(int keycode) {
        
        if(keycode == Keys.X){
            spaceship.warp();
        }
        
        if(keycode == Keys.SPACE){
            spaceship.shoot();
        }
        
        return false;
        
    }

    @Override
    public void initActors() {
       
        BaseActor space = new BaseActor(0f, 0f, mainStage);
        space.loadTexture("space.png");
        space.setSize(800, 600);
        BaseActor.setWorldBounds(space);
        
        spaceship = new Spaceship(400, 300, mainStage);
 
        new Rock(600f, 500f, mainStage);
        new Rock(600f, 300f, mainStage);
        new Rock(600f, 100f, mainStage);
        new Rock(400f, 100f, mainStage);
        new Rock(200f, 100f, mainStage);
        new Rock(200f, 300f, mainStage);
        new Rock(200f, 500f, mainStage);
        new Rock(400f, 500f, mainStage);
        
    }

    @Override
    public void initComponents() {}

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}
   
}
