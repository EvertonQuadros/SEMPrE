/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.rectangledestroyer10;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.TilemapActor;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import java.util.Random;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Paddle paddle;
    private Ball ball;
    
    private int score;
    private int balls;
    
    private Label scoreLabel;
    private Label ballsLabel;
    private Label messageLabel;
    
    private final float spawnProbability = 20;
    ArrayList<Item> items;
    
    private Sound bounceSound;
    private Sound brickBumpSound;
    private Sound wallBumpSound;
    private Sound itemAppearSound;
    private Sound itemCollectSound;
    
    private Music backgroundMusic;
    
    @Override
    public void initActors() {
       
        TilemapActor tilemapActor = new TilemapActor("customap.tmx", mainStage);
        
        tilemapActor.mappingTileObjects(Wall.class, mainStage);
        
        tilemapActor.mappingTileObjects(Brick.class, mainStage);
    
        MapObject mapObject = tilemapActor.getRectangleList("Start").get(0);
        MapProperties properties = mapObject.getProperties();
        
        paddle = new Paddle((float)properties.get("x"),
                            (float)properties.get("y"), mainStage);
        
        ball = new Ball(0, 0, mainStage);
        
        items = new ArrayList();
        
        score = 0;
        balls = 3;
     
    }
    
    @Override
    public void initComponents() {
        
        scoreLabel = new Label("Score: " + score, BaseGame.labelStyleFreeFont);
        ballsLabel = new Label("Balls: " + balls, BaseGame.labelStyleFreeFont);
        messageLabel = new Label("Click to Start", BaseGame.labelStyleFreeFont);
        messageLabel.setColor(Color.CYAN);
        
        uiTable.pad(5);
        uiTable.add(scoreLabel);
        uiTable.add().expandX();
        uiTable.add(ballsLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();
        
    }

    @Override
    public void initSounds() {
        
        bounceSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("boing.wav"));
        brickBumpSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("bump.wav"));
        wallBumpSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("bump-low.wav"));
        itemAppearSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("swoosh.wav"));
        itemCollectSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("pop.wav"));
        
        backgroundMusic = Gdx.audio.newMusic(BaseGame.util.getInternalFileHandle("Rollin-at-5.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.50f);
        backgroundMusic.play();
        
    }

    @Override
    public void initScenes() {}
 
    @Override
    public void update(float dt) {
        
        float mouseX = Gdx.input.getX();
        paddle.setX(mouseX - paddle.getWidth() / 2);
        paddle.boundToWorld();
       
        for(BaseActor wall : BaseActor.getList(mainStage, Wall.class)){
            
            if(ball.overlaps(wall)){

                ball.bounceOff(wall);
                wallBumpSound.play();

            }
        
        }
        
        for(BaseActor brick : BaseActor.getList(mainStage, Brick.class)){
            
            if(ball.overlaps(brick)){
                
                ball.bounceOff(brick);
                brickBumpSound.play();
                
                if(MathUtils.random(0, 100) < spawnProbability){
                    
                    Item i = new Item(0, 0, mainStage);
                    i.centerAtActor(brick);
                    itemAppearSound.play();
                    
                }
                
                brick.remove();
                
                score += 100;
                scoreLabel.setText("Score: " + score);
                
                break;
                
            }
            
        }
        
        for(BaseActor item : BaseActor.getList(mainStage, Item.class)){
            
            if(paddle.overlaps(item)){
            
                Item realItem = (Item) item;
 
                if(realItem.getType() == Item.Type.PADDLE_EXPAND){
                    paddle.setWidth(paddle.getWidth() * 1.25f);
                }
                else if(realItem.getType() == Item.Type.PADDLE_SHRINK){
                    paddle.setWidth((paddle.getWidth() * 0.80f));
                }
                else if(realItem.getType() == Item.Type.BALL_SPEED_UP){
                    ball.setSpeed(ball.getSpeed() * 1.50f);
                }
                else if(realItem.getType() == Item.Type.BALL_SPEED_DOWN){
                    ball.setSpeed(ball.getSpeed() * 0.90f);
                }
                
                if(paddle.getWidth() > 256.0f){
                    paddle.setWidth(256f);
                }
                else if(paddle.getWidth() < 64.0f){
                    paddle.setWidth(64f);
                }
                
                if(ball.getSpeed() < 150){
                    ball.setSpeed(150);
                }
                else if(ball.getSpeed() > 700){
                    ball.setSpeed(700);
                }
                
                paddle.setBoundaryRectangle();
                item.remove();
                itemCollectSound.play();
                
            }
            
        }
    
        if(ball.overlaps(paddle)){
            
            float ballCenterX = ball.getX() + ball.getWidth() / 2;
            float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
            float bounceAngle = MathUtils.lerp(150, 30, paddlePercentHit);
            
            ball.setMotionAngle(bounceAngle);
            bounceSound.play();
            
        }
        
        if(ball.getY() < -50 && BaseActor.countActors(mainStage, Brick.class) > 0){
            
            ball.remove();
            
            if(balls > 0){
                
                balls -= 1;
                ballsLabel.setText("Balls: " + balls);
                ball = new Ball(0, 0, mainStage);
                
                messageLabel.setText("Click to Start");
                messageLabel.setColor(Color.CYAN);
                messageLabel.setVisible(true);
                
            }
            else{
                
                messageLabel.setText("Game Over!");
                messageLabel.setColor(Color.RED);
                messageLabel.setVisible(true);
                
            }
            
            paddle.setWidth(128f);
            
            BaseActor.removeAllActors(mainStage, Item.class);
            
        }
        
        if(BaseActor.countActors(mainStage, Brick.class) == 0){
            
            messageLabel.setText("YOU WIN!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
            
        }
        
        if(ball.isPaused()){
            
            ball.setX(paddle.getX() + (paddle.getWidth() / 2) - (ball.getWidth() / 2));
            ball.setY(paddle.getY() + (paddle.getHeight() / 2) + (ball.getHeight() / 2));
            
        }
        
    }
    
    private void shuffleArray(Color[] colors){
        
        int index;
        Color temp;
        Random random = new Random();

        for (int i = colors.length - 1; i > 0; i--){

            index = random.nextInt(i + 1);
            temp = colors[index];
            colors[index] = colors[i];
            colors[i] = temp;

        }

    }
    
    @Override
    public boolean touchDown(int ScreenX, int ScreenY, int pointer, int button){
        
        if(ball.isPaused()){
            
            ball.setPaused(false);
            messageLabel.setVisible(false);
            
        }
        
        return false;
        
    }
    
}
