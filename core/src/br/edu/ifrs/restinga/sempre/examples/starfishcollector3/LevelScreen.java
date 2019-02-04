/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector3;

import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Turtle turtle;
    private BaseActor ocean;
    private boolean win = false;

    @Override
    public void initActors() {
        
        ocean = new BaseActor(0f, 0f, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);

        BaseActor.setWorldBounds(ocean);

        new StarFish(400,400, mainStage);
        new StarFish(500,100, mainStage);
        new StarFish(100,450, mainStage);
        new StarFish(200,250, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        turtle = new Turtle(20, 20, mainStage);
        
    }
    
    @Override
    public void initComponents() {}

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}
    
    @Override
    public void update(float dt) {
        
        for(BaseActor rockActor : BaseActor.getList(mainStage, Rock.class)){
            turtle.preventOverlap(rockActor);
        }
        
        for(BaseActor starfishActor : BaseActor.getList(mainStage, StarFish.class)){

            StarFish starfish = (StarFish) starfishActor;
            
            if(turtle.overlaps(starfish) && !starfish.isCollected()){
                
                starfish.collect();
                
                Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
                whirlpool.centerAtActor(starfish);
                whirlpool.setOpacity(0.25f);
                
            }
            
        }
        
        if(BaseActor.countActors(mainStage, StarFish.class) == 0 && !win){
         
            win = true;
            
            BaseActor youWinMessage = new BaseActor(0f, 0f, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
            
        }
        
        //As an entertaining and useful sidenote, if you switch the order of the objects in the preventOverlap
        //method, instead writing rock.preventOverlap(turtle), then the rock will be the object that moves; it will
        //appear as though the turtle is pushing the rock! This has the potential to be a useful game mechanic for a
        //future game project.
        
    }
    
}
