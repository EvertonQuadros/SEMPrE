/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.cardpickup;

import br.edu.ifrs.restinga.sempre.classes.base.DropTargetActor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class Pile extends DropTargetActor{

    private ArrayList<Card> cardList;
    
    public Pile(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        cardList = new ArrayList();
        loadTexture("pile.png");
        setSize(100,120);
        setBoundaryRectangle();
        
    }
    
    public void addCard(Card card){
        cardList.add(0, card);
    }
    
    public Card getTopCard(){
        return cardList.get(0);
    }
    
    public int getSize(){
        return cardList.size();
    }
    
    public void removeCard(){
        cardList.remove(0);
    }
    
}
