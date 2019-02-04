/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.RhythmTapper;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Message extends BaseActor{
    
    public Animation perfect;
    public Animation great;
    public Animation good;
    public Animation almost;
    public Animation miss;
    
    private Animation countDown3;
    private Animation countDown2;
    private Animation countDown1;
    private Animation countDownGo;
    private Animation congratulations;
    private Sound blip;
    private Sound tone;
        
    public Message(float x, float y, Stage stage){
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        perfect = loadTexture("perfect.png");
        great = loadTexture("great.png");
        good = loadTexture("good.png");
        almost = loadTexture("almost.png");
        miss = loadTexture("miss.png");
        
        countDown3 = loadTexture("countdown-3.png");
        countDown2 = loadTexture("countdown-2.png");
        countDown1 = loadTexture("countdown-1.png");
        countDownGo = loadTexture("countdown-go.png");
        congratulations = loadTexture("congratulations.png");
        blip = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("blip.wav"));
        tone = Gdx.audio.newSound(BaseGame.util.getInternalFileHandle("tone.wav"));
        
    }
    
    public void pulseFade(){
        
        setOpacity(1);
        clearActions();
        Action pulseFade = 
            Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.5f), 
                    Actions.scaleTo(1.0f, 1.0f, 0.05f), 
                    Actions.delay(1), 
                    Actions.fadeOut(0.5f));
        
        addAction(pulseFade);
        
    }
    
    public void displayCountdown(){
        
        Action countdown = 
                Actions.sequence(Actions.run(() -> setAnimation(countDown3)),
                                 Actions.run(() -> blip.play()),
                                 Actions.alpha(1),
                                 Actions.scaleTo(1.1f, 1.1f, 0.05f),
                                 Actions.scaleTo(1.0f, 1.0f, 0.05f),
                                 Actions.delay(0.5f),
                                 Actions.fadeOut(0.4f),
                                 Actions.run(() -> setAnimation(countDown2)),
                                 Actions.run(() -> blip.play()),
                                 Actions.alpha(1),
                                 Actions.scaleTo(1.1f, 1.1f, 0.05f),
                                 Actions.scaleTo(1.0f, 1.0f, 0.05f),
                                 Actions.delay(0.5f),
                                 Actions.fadeOut(0.4f),
                                 Actions.run(() -> setAnimation(countDown1)),
                                 Actions.run(() -> blip.play()),
                                 Actions.alpha(1),
                                 Actions.scaleTo(1.1f, 1.1f, 0.05f),
                                 Actions.scaleTo(1.0f, 1.0f, 0.05f),
                                 Actions.delay(0.5f),
                                 Actions.fadeOut(0.4f),
                                 Actions.run(() -> setAnimation(countDownGo)),
                                 Actions.run(() -> tone.play()),
                                 Actions.alpha(1),
                                 Actions.fadeOut(1));
        addAction(countdown);
        
    }
    
    public void displayCongratulations(){
        
        setOpacity(0);
        setAnimation(congratulations);
        setScale(2);
        addAction(Actions.fadeIn(4));
        
    }
    
}
