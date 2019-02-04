/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jumpingjack;

import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.TilemapActor;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Koala jack;
    private boolean gameOver;
    
    private Label messageLabel;
    
    private float time;   
    private Label timeLabel;
    
    private int coins;  
    private Label coinLabel;
    
    private Table keyTable;
    
    private ArrayList<Color> keyList;
    
    @Override
    public void initActors() {
        
        TilemapActor tilemapActor = new TilemapActor("customap.tmx", mainStage);
        
        tilemapActor.mappingRectangleObjects(Solid.class, mainStage);
        
        tilemapActor.mappingTileObjects(Flag.class, mainStage);
        
        tilemapActor.mappingTileObjects(Coin.class, mainStage);
        
        tilemapActor.mappingTileObjects(Timer.class, mainStage);
        
        tilemapActor.mappingTileObjects(Springboard.class, mainStage);
        
        tilemapActor.mappingTileObjects(Platform.class, mainStage);
        
        tilemapActor.mappingTileObjects(Key.class, mainStage);
        
        tilemapActor.mappingTileObjects(Lock.class, mainStage);
        
        MapObject startPoint = tilemapActor.getRectangleList("Start").get(0);
        MapProperties property = startPoint.getProperties();
        jack = new Koala(property, mainStage);
        
        gameOver = false;
        coins = 0;
        time = 10;
        
        keyList = new ArrayList();
        
    }

    @Override
    public void initComponents() {
    
        coinLabel = new Label("Coins: " + coins, BaseGame.labelStyleFreeFont);
        coinLabel.setColor(Color.GOLD);
        
        keyTable = new Table();
        
        timeLabel = new Label("Time: " + (int)time, BaseGame.labelStyleFreeFont);
        timeLabel.setColor(Color.LIGHT_GRAY);
        
        messageLabel = new Label("Message", BaseGame.labelStyleFreeFont);
        messageLabel.setVisible(false);
        
        uiTable.pad(20);
        uiTable.add(coinLabel);
        uiTable.add(keyTable).expandX();
        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}

    @Override
    public boolean keyDown(int keycode) {
        
        if(gameOver){
            return false;
        }
        
        if(keycode == Keys.SPACE){
            
            if(Gdx.input.isKeyPressed(Keys.DOWN)){
                
                for(BaseActor actor : BaseActor.getList(mainStage, Platform.class)){
                    
                    Platform platform = (Platform) actor;
                    
                    if(jack.belowOverlaps(platform)){
                        platform.setEnabled(false);
                    }
                    
                }
                
            }
            else if(jack.isOnSolid()){
                jack.jump();
            }
            
        }
        
        return false;
        
    }
    
    @Override
    public void update(float dt) {
    
        if(gameOver){
           return; 
        }  
        
        time -= dt;
        timeLabel.setText("Time: " + (int)time);
      
      
        if(time < 11){
            timeLabel.setColor(Color.RED);
        }
        else{
            timeLabel.setColor(Color.LIGHT_GRAY);
        }
        
        for(BaseActor actor : BaseActor.getList(mainStage, Timer.class)){
            
            Timer timer = (Timer) actor;
            
            if(jack.overlaps(timer)){
                
                time += 20;
                timer.remove();
                
            }
            
        }
        
        for(BaseActor actor : BaseActor.getList(mainStage, Solid.class)){
            
            Solid solid = (Solid) actor;
            
            //if(solid instanceof Platform){
                
                if(jack.isJumping() && jack.overlaps(solid) && !(solid instanceof Lock)){
                    solid.setEnabled(false);
                }
                
                if(jack.isJumping() && !jack.overlaps(solid) && !(solid instanceof Lock)){
                    solid.setEnabled(true);
                }
                
            //}
            
            if(solid instanceof Lock && jack.overlaps(solid)){
                
                Color lockColor = solid.getColor();
                
                if(keyList.contains(lockColor)){
                    
                    solid.setEnabled(false);
                    solid.addAction(Actions.fadeOut(0.5f));
                    solid.addAction(Actions.after(Actions.removeActor()));
                    
                }
       
            }

            if(jack.overlaps(solid) && solid.isEnabled()){

                Vector2 offSet = jack.preventOverlap(solid);

                if(offSet != null){

                    if(Math.abs(offSet.x) > Math.abs(offSet.y)){
                        jack.getVelocityVec().x = 0;
                    }
                    else{
                        jack.getVelocityVec().y = 0;
                    }

                }

            }
            
            if(jack.isFalling() && !jack.overlaps(solid) && !jack.belowOverlaps(solid)){
                solid.setEnabled(true);
            }
            
        }
        
        for(BaseActor key : BaseActor.getList(mainStage, Key.class)){
            
            if(jack.overlaps(key)){
                
                Color color = key.getColor();
                key.remove();
                
                BaseActor keyIcon = new BaseActor(0f, 0f, uiStage);
                keyIcon.loadTexture("key-icon.png");
                keyIcon.setColor(color);
                keyTable.add(keyIcon);
                keyList.add(color);
                
            }
            
        }
        
        for(BaseActor springboard : BaseActor.getList(mainStage, Springboard.class)){
            
            if(jack.belowOverlaps(springboard) && jack.isFalling()){
                jack.spring();
            }
            
        }
        
        for(BaseActor coin : BaseActor.getList(mainStage, Coin.class)){
            
            if(jack.overlaps(coin)){
                
                coins++;
                coinLabel.setText("Coins: " + coins);
                coin.remove();
               
            }
            
        }
   
        for(BaseActor flag : BaseActor.getList(mainStage, Flag.class)){
            
            if(jack.overlaps(flag)){
                
                messageLabel.setText("YOU WIN!!!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
                
            }
            
        }
        
        if(jack.getY() < -50 || time <= 0){
            
            messageLabel.setText("GAME OVER!!!");
            
            if(time <= 0){
                messageLabel.getText().append("\n   TIME UP!!!");
            }
            
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
            
        }
        
        
    }
    
}
