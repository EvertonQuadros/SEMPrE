/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.missinghomework;

import br.edu.ifrs.restinga.sempre.classes.actions.Scene;
import br.edu.ifrs.restinga.sempre.classes.actions.SceneActions;
import br.edu.ifrs.restinga.sempre.classes.actions.SceneSegment;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 *
 * @author Not275
 */
public class StoryScreen extends BaseScreen{

    private Scene scene;
    private Background background;
    private Kelsoe kelsoe;
    private DialogBox dialogBox;
    private BaseActor continueKey;
    private Table buttonTable;
    private BaseActor end;
    
    @Override
    public void initActors() {
        
        background = new Background(0, 0, mainStage);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);
        
        kelsoe = new Kelsoe(0, 0, mainStage);
        
        continueKey = new BaseActor(0f, 0f, uiStage);
        continueKey.loadTexture("key-C.png");
        continueKey.setSize(32, 32);
        continueKey.setVisible(false);
        
        end = new BaseActor(0f, 0f, mainStage);
        end.loadTexture("the-end.png");
        end.centerAtActor(background);
        end.setScale(2);
        end.setOpacity(0); 
        
    }
    
    @Override
    public void initComponents() {
        
        dialogBox = new DialogBox(0, 0, uiStage, "dialog-translucent.png");
        dialogBox.setDialogSize(600, 150);
        dialogBox.setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, 1));
        dialogBox.setFontColor(Color.WHITE);
        dialogBox.setVisible(false);
        dialogBox.addActor(continueKey);
        
        continueKey.setPosition(dialogBox.getWidth() - continueKey.getWidth(), 0);
        
        buttonTable = new Table();
        buttonTable.setVisible(false);
        
        uiTable.add().expandY();
        uiTable.row();
        uiTable.add(buttonTable);
        uiTable.row();
        uiTable.add(dialogBox);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {
        
        scene = new Scene();
        mainStage.addActor(scene);
        
        hallway();
        
    }
    
    @Override
    public void update(float dt) { }
    
    public void hallway(){
        
        scene.clearSegments();
        
        background.setAnimation(background.hallway);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));
        
        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));
        
        this.addTextSequence("My name is Kelsoe Kismet. I am a studente at Taurus Ludus Academy.");
        this.addTextSequence("I can be a little forgetful sometimes. Right now, I'm looking for my homework.");
        
        scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
        scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
        
        scene.addSegment(new SceneSegment(background, Actions.run(() -> {classroom();})));
        
        scene.start();
        
    }
    
    public void classroom(){
        
        scene.clearSegments();
        background.setAnimation(background.classroom);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));
        
        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));
        
        this.addTextSequence("This is my classroom. My homework isn't here, though.");
        this.addTextSequence("Where should I look for my homework next?");
        
        scene.addSegment(new SceneSegment(buttonTable, Actions.show()));
        
        //setting up options;
        TextButton scienceLabButton = new TextButton("Look in the Science Lab", BaseGame.textbuttonStyle);
        scienceLabButton.addListener(
                
            (Event e) -> {
        
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    
                    scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
                    this.addTextSequence("That's great idea. I'll check the science lab.");
                    scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
                    scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1)));
                    scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
                    scene.addSegment(new SceneSegment(background, Actions.run(() -> {scienceLab();})));
                    
                }
                
                return false;
                
            }
        
        );
        
        TextButton libraryButton = new TextButton("Look in the Library", BaseGame.textbuttonStyle);
        libraryButton.addListener(
            
            (Event e) -> {
                
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                
                    scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
                    this.addTextSequence("That's a great idea. Maybe I left it in the library.");
                    scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
                    scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
                    scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
                    scene.addSegment(new SceneSegment(background, Actions.run(() -> {library();})));
                
                }
            
                return false;
                
            }
                
        );
        
        buttonTable.clearChildren();
        buttonTable.add(scienceLabButton);
        buttonTable.row();
        buttonTable.add(libraryButton);
        
        scene.start();
        
    }
    
    public void scienceLab(){
        
        scene.clearSegments();
        background.setAnimation(background.scienceLab);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));
        
        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));
        
        this.addTextSequence("This is the science lab.");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)));
        this.addTextSequence("My homework isn't here, though.");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)));
        this.addTextSequence("Now where should I go?");
        
        scene.addSegment(new SceneSegment(buttonTable, Actions.show()));
        
        //setting up options;
        
        TextButton classroomButton = new TextButton("return to the Classroom.",BaseGame.textbuttonStyle);
        classroomButton.addListener(
                
            (Event e) -> {

                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    
                    scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
                    this.addTextSequence("Maybe someone found it and put it in the classroom. I'll go check."); 
                    scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
                    scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
                    scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
                    scene.addSegment(new SceneSegment(background, Actions.run(() -> {classroom();})));
                    
                }

                return false;
                
            }
        
        );
        
        TextButton libraryButton = new TextButton("Look in the Library", BaseGame.textbuttonStyle);
        libraryButton.addListener(
                
            (Event e) -> {
        
                if((e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown)){
                    
                    scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
                    this.addTextSequence("That's a great idea. Maybe I left it in the library.");
                    scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
                    scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
                    scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
                    scene.addSegment(new SceneSegment(background, Actions.run(() -> {library();})));
                    
                } 
                
                return false;
                
            } 
        
        );
        
        buttonTable.clearChildren();
        buttonTable.add(classroomButton);
        buttonTable.row();
        buttonTable.add(libraryButton);
        
        scene.start();
        
    }
    
    public void library(){
        
        scene.clearSegments();
        
        background.setAnimation(background.library);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));
        
        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));
        
        this.addTextSequence("This is the library.");
        this.addTextSequence("Let me check the table where I was working earlier...");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.lookRight)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenRight(2)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)));
        this.addTextSequence("Aha! Here it is!");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(0.5f)));
        this.addTextSequence("Thanks for helping me to find it!");
        scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
        
        scene.addSegment(new SceneSegment(end, Actions.fadeIn(4)));
        
        scene.addSegment(new SceneSegment(background,  Actions.delay(10)));
        scene.addSegment(new SceneSegment(background, Actions.run(() -> {BaseGame.setActiveScreen(new MenuScreen());})));
        
        scene.start();
      
    }
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(keyCode == Keys.C){
            scene.loadNextSegment();
        }
        
        return false;
        
    }
    
    public void addTextSequence(String text){
        
        scene.addSegment(new SceneSegment(dialogBox, SceneActions.typewriter(text)));
        scene.addSegment(new SceneSegment(continueKey, Actions.show()));
        scene.addSegment(new SceneSegment(background, SceneActions.pause()));
        scene.addSegment(new SceneSegment(continueKey, Actions.hide()));
        
    }

}
