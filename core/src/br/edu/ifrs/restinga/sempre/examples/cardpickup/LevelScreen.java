/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.cardpickup;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private ArrayList<Pile> pileList;
    
    private Label messageLabel;
    
    private boolean won;
    
    @Override
    public void initActors() {
        
        BaseActor background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("felt.jpg");
        BaseActor.setWorldBounds(background);
        
        for(int rank = 0; rank < Card.rankNames.length; rank++){
            
            for(int suit = 0; suit < Card.suitNames.length; suit++){
                
                int x = MathUtils.random(0, 800);
                int y = MathUtils.random(0, 300);
                
                Card card = new Card(x, y, mainStage);
                card.setRankValues(rank, suit);
                
                card.toBack();
                
            }
            
        }
        
        background.toBack();
        
        pileList = new ArrayList();
        
        for(int i = 0; i < 4; i++){
            
            int pileX = 120 + (150 * i);
            int pileY = 450;
            Pile pile = new Pile(pileX, pileY, mainStage);
            pileList.add(pile);
            
        }

        for(BaseActor actor : BaseActor.getList(mainStage, Card.class)){

            Card card = (Card) actor;
            
            if(card.getRankValue() == 0){
            
                Pile pile = pileList.get(card.getSuitValue());
                card.toFront();
                card.moveToActor(pile);
                pile.addCard(card);
                card.setDraggable(false);
                
            }
            
        }
        
    }
    
    @Override
    public void initComponents() {
       
        messageLabel = new Label("You Win!!!", BaseGame.labelStyleFreeFont);
        messageLabel.setColor(Color.CYAN);
        messageLabel.setVisible(false);
        
        uiTable.add(messageLabel).expandX().expandY().bottom().pad(50);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}

    @Override
    public void update(float dt) {
       
        won = true;
        
        for(Pile pile : pileList){
   
            if(pile.getSize() < 12){
                won = false;
            }
            
        }
        
        messageLabel.setVisible(won);
        
    }

}
