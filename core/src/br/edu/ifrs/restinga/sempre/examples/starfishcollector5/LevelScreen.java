/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector5;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Turtle turtle;
    private BaseActor ocean;
    private boolean win = false;
    
    
    private Label starfishLabel;
    private ButtonStyle buttonStyle;
    private DialogBox dialogBox;
    
    @Override
    public void update(float dt) {
        
        starfishLabel.setText("Starfish Left: " + BaseActor.countActors(mainStage, StarFish.class));
        
        BaseActor.getList(mainStage, Rock.class).forEach((rockActor) -> {
            turtle.preventOverlap(rockActor);
        });
        
        for (BaseActor starfishActor : BaseActor.getList(mainStage, StarFish.class)) {
            
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

        for(BaseActor signActor : BaseActor.getList(mainStage, Sign.class)){
            
            Sign sign = (Sign)signActor;
            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(2, sign);
            
            if(nearby && !sign.isViewing()){
                
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
                
            }
            
            if(sign.isViewing() && !nearby){
                
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
                
            }
            
        }
        
        //As an entertaining and useful sidenote, if you switch the order of the objects in the preventOverlap
        //method, instead writing rock.preventOverlap(turtle), then the rock will be the object that moves; it will
        //appear as though the turtle is pushing the rock! This has the potential to be a useful game mechanic for a
        //future game project.
        
    }

    @Override
    public void initActors() {
        
        starfishLabel = new Label("Starfish Left: ", BaseGame.labelStyleFreeFont);
        starfishLabel.setColor(Color.CYAN);
        
        ocean = new BaseActor(0f, 0f, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        
        BaseActor.setWorldBounds(ocean);
        
        Sign sign1 = new Sign(20, 400, mainStage);
        Sign sign2 = new Sign(600, 300, mainStage);
        
        sign1.setText("West Starfish Bay");
        sign2.setText("East Starfish Bay");
        
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
    public void initComponents() {
        
        buttonStyle = new ButtonStyle();
        Texture buttonTexture = BaseGame.util.getInternalTexture("undo.png");
        
        TextureRegion buttonRegion = new TextureRegion(buttonTexture);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);
        
        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);
      
        restartButton.addListener( 
        
            (Event e) -> {

                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(InputEvent.Type.touchDown)){
                    StarfishGameV5.setActiveScreen(new LevelScreen());
                }

                return false;
                
            }
                
        );
        
        dialogBox = new DialogBox(0, 0, uiStage, "dialog.png");
        dialogBox.setBackgroundColor(new Color(0.6f, 0.6f, 0.8f, 1));
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(1.25f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);
        
        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
         
        uiTable.row();
        uiTable.add(dialogBox).colspan(3);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}
    
}
