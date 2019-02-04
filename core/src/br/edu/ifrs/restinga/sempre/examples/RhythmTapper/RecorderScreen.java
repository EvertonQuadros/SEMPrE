/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.RhythmTapper;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;

import br.edu.ifrs.restinga.sempre.utils.FileUtil;
import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.scenes.scene2d.Event;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 *
 * @author Not275
 */
public class RecorderScreen extends BaseScreen{

    Music music;
    SongData songData;
    float lastSongPosition;
    boolean recording;
    TextButton loadButton;
    TextButton recordButton;
    TextButton saveButton;
    
    @Override
    public void initialize() {
        
        recording = false;
       
        loadButton = new TextButton("Load Music File", BaseGame.textbuttonStyle);
        recordButton = new TextButton("Record Keystrokes", BaseGame.textbuttonStyle);
        saveButton = new TextButton("Save Keystrokes File", BaseGame.textbuttonStyle);
        
        loadButton.addListener(
        
            (Event e) -> {
                
               if(isTouchDownEvent(e)){
                   
                    if(recording){
                        music.dispose();
                    }
                   
                    FileHandle musicFile = FileUtil.showOpenDialog();
                   
                    if(musicFile != null){
                        
                        music = Gdx.audio.newMusic(musicFile);
                        songData = new SongData();
                        songData.setSongName(musicFile.name());
                        
                    }
                   
                    return true;
                    
               }
        
               return false;
               
            }
        
        );
        
        recordButton.addListener(
        
            (Event e) -> {
                
                if(isTouchDownEvent(e)){
                    
                    if(!recording){
                        
                        if(music != null){
                            
                            music.play();
                            recording = true;
                            lastSongPosition = 0;
                        
                        }
                        else{
                            BaseGame.util.showErrorMessage(new NullPointerException("Você deve selecionar um arquivo de som!"));
                        }
                        
                    }
                    
                    return true;
                    
                }
                
                return false;
                
            }
                
        );
        
        saveButton.addListener(
        
            (Event e) -> {
             
                if(isTouchDownEvent(e)){
                    
                    FileHandle textFile = FileUtil.showSaveDialog();
                    
                    if(textFile != null && songData != null){
                        songData.writeToFile(textFile);
                    }
                    
                    return true;
                    
                }
                
                return false;
                
            }
        
        );  
        
        uiTable.add(loadButton);
        uiTable.row();
        uiTable.add(recordButton);
        uiTable.row();
        uiTable.add(saveButton);
        
    }

    @Override
    public void update(float dt) {
       
        if(recording){
            
            if(music.isPlaying()){
                lastSongPosition = music.getPosition();
            }
            else{
                
                recording = false;
                songData.setSongDuration(lastSongPosition);
            
            }
            
        }
        
    }
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(recording){
            
            String key = Keys.toString(keyCode);
            Float time = music.getPosition();
            songData.addKeyTime(key, time);
            
        }
        
        return false;
        
    }

    @Override
    public void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initActors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initSounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initScenes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
