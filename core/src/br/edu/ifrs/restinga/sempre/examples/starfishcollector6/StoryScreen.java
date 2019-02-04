/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector6;

import br.edu.ifrs.restinga.sempre.classes.actions.SceneActions;
import br.edu.ifrs.restinga.sempre.classes.actions.Scene;
import br.edu.ifrs.restinga.sempre.classes.actions.SceneSegment;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.components.CustomLabelStyle;
import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class StoryScreen extends BaseScreen{

    Scene scene;
    
    BaseActor continueKey;
    BaseActor background;
    BaseActor turtle;
    
    DialogBox dialogBox;

    @Override
    public void update(float dt) {
        
        if(scene.isSceneFinished()){
            BaseGame.setActiveScreen(new LevelScreen());
        }
        
    }
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(keyCode == Keys.C && continueKey.isVisible()){
            scene.loadNextSegment();
        }
        
        return false;
        
    }

    @Override
    public void initActors() {
        
        background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("oceanside.png");
        background.setSize(800, 600);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);
     
        turtle = new BaseActor(0f, 0f, mainStage);
        turtle.loadTexture("turtle-big.png");
        turtle.setPosition(-turtle.getWidth(), 0);
        
    }
    
    @Override
    public void initComponents() {
        
        FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 32;
        BaseGame.labelStyleFreeFont.setParameters(fontParameters);
   
        dialogBox = new DialogBox(0, 0, uiStage, "dialog-translucent.png");
        dialogBox.setDialogSize(600, 200);
        dialogBox.setBackgroundColor(new Color(0.6f, 0.6f, 0.8f, 1));
        dialogBox.setFontScale(1.25f);
        dialogBox.setVisible(false);
        
        uiTable.add(dialogBox).expandX().expandY().bottom();
 
        continueKey = new BaseActor(0f, 0f, uiStage);
        continueKey.loadTexture("key-C.png");
        continueKey.setSize(32, 32);
        continueKey.setVisible(false);
        continueKey.setPosition(dialogBox.getWidth() - continueKey.getWidth(), 0);
     
        dialogBox.addActor(continueKey);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {
        
        scene = new Scene();
        mainStage.addActor(scene);
 
        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(turtle, SceneActions.moveToScreenCenter(2)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));
        
        scene.addSegment(new SceneSegment(dialogBox, SceneActions.setText("I want to be very best...Starfish Collector!")));
        
        scene.addSegment(new SceneSegment(continueKey, Actions.show()));
        scene.addSegment(new SceneSegment(background, SceneActions.pause()));
        scene.addSegment(new SceneSegment(continueKey, Actions.hide()));
        
        scene.addSegment(new SceneSegment(dialogBox, SceneActions.setText("I've got to collect them all!")));
        
        scene.addSegment(new SceneSegment(continueKey, Actions.show()));
        scene.addSegment(new SceneSegment(background, SceneActions.pause()));
        scene.addSegment(new SceneSegment(continueKey, Actions.hide()));
        
        scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
        scene.addSegment(new SceneSegment(turtle, SceneActions.moveToOutsideRight(1)));
        scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
        
        scene.start();
        
    }
    
}
