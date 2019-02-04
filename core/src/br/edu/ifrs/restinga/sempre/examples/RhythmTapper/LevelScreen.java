/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.RhythmTapper;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.components.CustomLabelStyle;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private ArrayList<String> keyList;
    private ArrayList<Color> colorList;
    private ArrayList<TargetBox> targetBoxList;
    private ArrayList<ArrayList<FallingBox>> fallingLists; 
    
    private Music gameMusic;
    private SongData songData;
    
    private final float leadTime = 12;
    private float advanceTimer;
    private float spawnHeight;
    private float noteSpeed;
    
    private Message message;
    private Label scoreLabel;
    private int score;
    private int maxScore;
    private Label timeLabel;
    private float songDuration;

    private Sound tone;
    
    private boolean hasPlayed;
    
    @Override
    public void initActors() {
        
        hasPlayed = false;
        
        BaseActor background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("space.png");
        background.setSize(800, 600);
        BaseActor.setWorldBounds(background);

        keyList = new ArrayList();
        String[] keyArray = {"F","G","H","J"};
        Collections.addAll(keyList, keyArray);

        colorList = new ArrayList();
        Color[] colorArray = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        Collections.addAll(colorList, colorArray);

        Table targetTable = new Table();
        targetTable.setFillParent(true);
        targetTable.add().colspan(4).expandY();
        targetTable.row();
        mainStage.addActor(targetTable);

        targetBoxList = new ArrayList();

        for(int i = 0; i < 4; i++){

            TargetBox targetBox = new TargetBox(0, 0, mainStage, keyList.get(i), colorList.get(i));
            targetBoxList.add(targetBox);
            targetTable.add(targetBox).pad(32);

        }
       
        fallingLists = new ArrayList();

        for(int i = 0; i < 4; i++){

            fallingLists.add(new ArrayList<>());

        }

        advanceTimer = 0;
        spawnHeight = 650;
        noteSpeed = (spawnHeight - targetBoxList.get(0).getY()) / 3;
        
    }
    
    @Override
    public void initComponents() {
        
        BaseGame.labelStyleFreeFont = new CustomLabelStyle(BaseGame.util.getInternalFileHandle("Kirsty.ttf"));
   
        TextButton startButton = new TextButton("Start", BaseGame.textbuttonStyle);
        startButton.addListener(

            (Event e) -> {

                if(isTouchDownEvent(e)){

                    FileHandle dataFileHandle = BaseGame.util.getInternalFileHandle("custom.key");
                    songData = new SongData();
                    songData.readFromFile(dataFileHandle);
                    songData.resetIndex();

                    FileHandle songFileHandle = BaseGame.util.getInternalFileHandle(songData.getSongName());
                    gameMusic = Gdx.audio.newMusic(songFileHandle);
                    startButton.setVisible(false);

                    songDuration = songData.getSongDuration();
                    score = 0;
                    maxScore = 100 * songData.keyTimeCount();
                    scoreLabel.setText("Score: " + score + "\n" + "Max: " + maxScore);
                    timeLabel.setText("Time: " + 0 + "\n" + "End: " + (int)songDuration);

                    message.displayCountdown();
                    
                    return true;

                }

                return false;

            }

        );
       
        scoreLabel = new Label("Score: 0" + "\n" + "Max: 0", BaseGame.labelStyleFreeFont);
        scoreLabel.setAlignment(Align.right);

        timeLabel = new Label("Time: 0" + "\n" + "End: 0", BaseGame.labelStyleFreeFont);
        timeLabel.setAlignment(Align.right);

        message = new Message(0, 0, uiStage);
        message.setOpacity(0);

        uiTable.pad(10);
        uiTable.add(startButton).width(200).left();
        uiTable.add(timeLabel).width(150);
        uiTable.add(scoreLabel).width(200).right();
        uiTable.row();
        uiTable.add(message).colspan(3).expandX().expandY();
        
    }

    @Override
    public void initSounds() {
        tone = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("tone.wav"));
    }

    @Override
    public void initScenes() {}
    
    @Override
    public void update(float dt) {
    
        if(songData != null){
            
            if(advanceTimer + dt > 3 && !hasPlayed){
                
                gameMusic.play();
                hasPlayed = true;
                advanceTimer = 0;
                
            }
     
//            if(advanceTimer < leadTime){
            advanceTimer += dt;
//            }
//            else{
//                //advanceTimer = leadTime + gameMusic.getPosition();
//                advanceTimer = advanceTimer + gameMusic.getPosition();
//            }
            
            System.out.printf("\n" + advanceTimer);
            
            while(!songData.isFinished() && hasPlayed && advanceTimer >= songData.getCurrentKeyTime().getTime()){
                
                String key = songData.getCurrentKeyTime().getKey();
                int i = keyList.indexOf(key);
                
                FallingBox fallingBox = new FallingBox(targetBoxList.get(i).getX(), spawnHeight, mainStage);
                fallingBox.setSpeed(noteSpeed);
                fallingBox.setMotionAngle(270);
                fallingBox.setColor(colorList.get(i));
                
                fallingLists.get(i).add(fallingBox);
                
                songData.advanceIndex();
               
            }
            
            for(int i = 0; i < 4; i++){

                ArrayList<FallingBox> fallingList = fallingLists.get(i);

                if(fallingList.size() > 0){

                    FallingBox fallingBox = fallingList.get(0);
                    TargetBox targetBox = targetBoxList.get(i);

                    if(fallingBox.getY() < targetBox.getY() && !fallingBox.overlaps(targetBox)){

                        message.setAnimation(message.miss);
                        message.pulseFade();
                        fallingList.remove(fallingBox);
                        fallingBox.setSpeed(0);
                        fallingBox.flashOut(); //remove from stage immediately;

                    }

                }

            }
            
            if(gameMusic.isPlaying()){
                timeLabel.setText("Time: " + (int)advanceTimer + "\n" + "End: " + (int)songDuration);             
            }
  
//            if(!gameMusic.isPlaying() && songData.isFinished()){
//        
//                message.displayCongratulations();
//                songData = null;
//         
//            }

            //if(!gameMusic.isPlaying() && advanceTimer >= (songDuration + 4)){
            
            if(!gameMusic.isPlaying() && advanceTimer > (int)songDuration + 2){ //2 secs more to finish all remaining failling boxes.
            
                message.displayCongratulations();
                songData = null;

            }
            
        }
        
    }
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(songData != null){
            
            String keyString = Keys.toString(keyCode);
            
            if(keyList.contains(keyString)){
                
                int i = keyList.indexOf(keyString);
                TargetBox targetBox = targetBoxList.get(i);
                targetBox.pulse();
                
                ArrayList<FallingBox> fallingList = fallingLists.get(i);
         
                if(fallingList.isEmpty()){
                    
                    message.setAnimation(message.miss);
                    message.pulseFade();
                    
                }
                else{
                    
                    FallingBox fallingBox = fallingList.get(0);
                    float distance = Math.abs(fallingBox.getY() - targetBox.getY());
                    
                    if(distance < 32){
                        tone.play();
                    }
                    
                    if(distance < 8){
                        
                        message.setAnimation(message.perfect);
                        score += 100;
                        
                    }
                    else if(distance < 16){
                        
                        message.setAnimation(message.great);
                        score += 80;
                        
                    }
                    else if(distance < 24){
                        
                        message.setAnimation(message.good);
                        score += 50;
                        
                    }
                    else if(distance < 32){
                        
                        message.setAnimation(message.almost);
                        score += 20;
                        
                    }
                    else{
                        message.setAnimation(message.miss);
                    }
                    
                    message.pulseFade();
                    scoreLabel.setText("Score: " + score + "\n" + "Max: " + maxScore);
                    
                    fallingList.remove(fallingBox);
                    fallingBox.setSpeed(0);
                    fallingBox.flashOut();
                    
                }
                
            }
            
        }
        
        return false;
        
    }

}
