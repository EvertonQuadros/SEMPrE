/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.cardpickup;

import br.edu.ifrs.restinga.sempre.classes.base.DragAndDropActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Card extends DragAndDropActor{

    public static String[] rankNames = {"A","2","3","4","5","6","7","8","9","J","Q","K"};
    public static String[] suitNames = {"Clubs","Hearts","Spades","Diamonds"};
    
    private int rankValue;
    private int suitValue;
    
    private Pile pile;
    
    public Card(float x, float y, Stage stage) {
        super(x, y, stage);
    }

    public int getRankValue() {
        return rankValue;
    }

    public void setRankValue(int rankValue) {
        this.rankValue = rankValue;
    }
    
    public String getRankName(){
        return rankNames[getRankValue()];
    }

    public int getSuitValue() {
        return suitValue;
    }

    public void setSuitValue(int suitValue) {
        this.suitValue = suitValue;
    }

    public String getSuitName(){
        return suitNames[getSuitValue()];
    }

    public Pile getPile() {
        return pile;
    }

    public void setPile(Pile pile) {
        this.pile = pile;
    }
    
    public void clearPile(){
        pile = null;
    }
    
    public boolean hasPile(){
        return pile != null;
    }
    
    public void setRankValues(int rank, int suit){
        
        setRankValue(rank);
        setSuitValue(suit);
        String textureFileName = "card".concat(getSuitName())
                                       .concat(getRankName())
                                       .concat(".png");
        
        loadTexture(textureFileName);
        setSize(80,100);
        setBoundaryRectangle();
        
    }

    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        boundToWorld(); //to prevent card to be placed out of the screen;
        
    }

    @Override
    public void onDrop() {
        
        if(hasDropTarget()){
            
            Pile Tpile = (Pile)getDropTarget();
            Card topCard = Tpile.getTopCard();
            
            if(this.getRankValue() == topCard.getRankValue() + 1 
                    && this.getSuitValue() == topCard.getSuitValue()){
                
                moveToActor(Tpile);
                Tpile.addCard(this);
                //this.setDraggable(false);
                this.setPile(Tpile);
                
            }
            else{
                moveToStart(); //avoid blocking view of pile when incorrect;
            }
            
        }
        
    }

    @Override
    public void onDragStart() {
       
        if(hasPile()){
            
            Pile Tpile = this.getPile();
            Tpile.removeCard();
            clearPile();
  
        }
        
    }
    
}
