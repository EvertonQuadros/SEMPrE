/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.planedodger;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Plane plane;
    
    private float starTimer;
    private final float starSpawnInterval = 4;
    private int score;
    private Label scoreLabel;
    
    private float enemyTimer;
    private float enemySpawnInterval;
    private float enemySpeed;
    private boolean gameOver;
    private BaseActor gameOverMessage;
    
    private Music backgroundMusic;
    private Sound sparkleSound;
    private Sound explosionSound;

    @Override
    public void initActors() {
        
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);
     
        plane = new Plane(100, 500, mainStage);
        BaseActor.setWorldBounds(800,600);
     
        starTimer = 0;
        score = 0;
        enemyTimer = 0;
        enemySpeed = 100;
        enemySpawnInterval = 3;
        
        gameOver = false;
        
    }
    
    @Override
    public void initComponents() {
        
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyleFreeFont);

        gameOverMessage = new BaseActor(0f, 0f, uiStage);
        gameOverMessage.loadTexture("game-over.png");
        gameOverMessage.setVisible(false);
        
        uiTable.pad(10);
        uiTable.add(scoreLabel);
        uiTable.row();
        uiTable.add(gameOverMessage).expandY();
        
    }

    @Override
    public void initSounds() {
        
        backgroundMusic = Gdx.audio.newMusic(BaseGame.util.getInternalFileHandle("Prelude-and-Action.mp3"));
        sparkleSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("explosion.wav"));
       
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.00f);
        backgroundMusic.play();
        
    }

    @Override
    public void initScenes() {}
    
    @Override
    public void update(float dt) {
       
        if(!gameOver){
        
            starTimer += dt;
            enemyTimer += dt;

            if(starTimer > starSpawnInterval){

                new Star(800, MathUtils.random(100, 500), mainStage);
                starTimer = 0;

            }

            if(enemyTimer > enemySpawnInterval){

                Enemy enemy = new Enemy(800, MathUtils.random(100, 500), mainStage);
                enemy.setSpeed(enemySpeed);

                enemyTimer = 0;
                enemySpawnInterval -= 0.10f;
                enemySpeed += 10;

                if(enemySpawnInterval < 0.5f){
                    enemySpawnInterval = 0.5f;
                }

                if(enemySpeed > 400){
                    enemySpeed = 400;
                }

            }

            for(BaseActor star : BaseActor.getList(mainStage, Star.class)){

                if(plane.overlaps(star)){

                    Sparkle sparkle = new Sparkle(0, 0, mainStage);
                    sparkle.centerAtActor(star);
                    sparkleSound.play();
                    
                    star.remove();
                    score++;
                    scoreLabel.setText(Integer.toString(score));

                }

            }

            for(BaseActor enemy : BaseActor.getList(mainStage, Enemy.class)){

                if(plane.overlaps(enemy)){

                    Explosion explosion = new Explosion(0, 0, mainStage);
                    explosion.centerAtActor(plane);
                    explosion.setScale(3);
                    explosionSound.play();
                    backgroundMusic.stop();
                    
                    plane.remove();
                    gameOver = true;
                    gameOverMessage.setVisible(true);

                }
                else{

                    if(enemy.getX() + enemy.getWidth() < 0){

                        score++;
                        scoreLabel.setText(Integer.toString(score));
                        enemy.remove();

                    }

                }

            }
        
        }
        
    }
    
    @Override
    public boolean keyDown(int keyCode){
        
        if(keyCode == Keys.SPACE){
            plane.boost();
        }
        
        return false;
        
    }
    
}
