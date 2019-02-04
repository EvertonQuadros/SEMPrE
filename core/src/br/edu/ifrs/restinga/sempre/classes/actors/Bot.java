/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseStage;
import br.edu.ifrs.restinga.sempre.classes.effects.Icon;

import br.edu.ifrs.restinga.sempre.classes.effects.Selection_Effect;

import br.edu.ifrs.restinga.sempre.classes.screens.LevelScreen;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.MathUtils;
import java.util.Map.Entry;

/**
 *
 * @author Not275
 */
public class Bot extends Persona{
    
    private float timer = MathUtils.random();
    private final float reactionTimer = 3f + MathUtils.random();
    private final float testReactionTimer = 3f;
    private float testTimer = 0f;
    private final LevelScreen screen;
    
    private final String[] messages = {"O jogador %s comprou o lote %d !", //0
                                       "Compra não realizada no lote %d ! Sua oferta foi ultrapassada!"}; //1
    
    public Bot(MapProperties properties, BaseStage stage, LevelScreen screen) {
        
        super(properties, stage);

        init();
        
        this.screen = screen; //needs access to screen for update UI values and show messages to user.
        
        playerContrast = new Selection_Effect(0f, 0f, getStage(), Color.RED);
        playerContrast.setZIndex(1); 
        
    }

    private void init(){
      
        setNecessity(new Product(Product.Type_Product.values()[0], 0));
    
        setPerception(0.5f); //overall first impression, are generated when walker completes a trade;

        setNecessityLevel(0f); //more necessity are higher probability to accept lower quality and higher prices;

        setPatience(0f); //less patience are more likety to give up when in lot (if too long).

        setProductivity(0.5f); //productivity used to employee, define time to attend a consumer (walker). productivity < patience are likely to generate giveups.

        setMoney(MathUtils.random(500000, 1000000));
        
    }
    
    private void addOffer(int range){ //value range;
        
        boolean anyRange = false;
        
        if(range <= 0){
            anyRange = true;
        }
        
        for(BaseActor actor : BaseActor.getList(getStage(), Lot.class)){
                
            Lot lot = (Lot)actor;
                
            if(lot.getOwner() != null 
                && lot.getOwner() != this){
                    
                //System.out.printf("\n" + lot.getPrice());
                
                if(!lot.getPersonaOffer(this) && (anyRange || range >= lot.getPrice())){
                        
                    int value = lot.getPrice() + MathUtils.random(100, 500);
                    
                    //System.out.printf("\nVALUE: " + value);
                    
                    if(getMoney() >= value && lot.addOffer(this, value)){
                        
                        subMoney(value);
                        
                    }
                        
                }
                    
            }
                
        }

    }
    
    private void sellOffer(Lot lot){
        
        Entry entry = lot.getHighestOffer();
   
        if(entry != null){
        
            if(entry.getKey().equals(screen.getPlayer())){
                screen.getNavigationDialog().getModal().informationEvent(lot, 1,(int)entry.getValue());
                lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
            }
            else{

                if(lot.getPersonaOffer(screen.getPlayer())){

                    screen.getPlayer().getIcon(Icon.Type.SMILEY_SAD);
                    screen.getNavigationDialog().castMessage(String.format(messages[1], lot.getId()), Color.RED);
                    lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
                    
                }
                else{
                    
                    screen.getNavigationDialog().castMessage(String.format(messages[0], ((Persona)entry.getKey()).getPersonaName(), lot.getId()), Color.YELLOW);
                    lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
                    
                }

            }

            lot.sell();

            if(isInsideLot()){
                exitLot();
            }
        
        }
        
    }
    
    private void putToSell(Lot lot){
        
        boolean playerOffer = lot.getPersonaOffer(screen.getPlayer());
        
        if(lot.addSellStatus()){
            
            if(lot.getOwner().equals(screen.getPlayer())){
                
                screen.getPlayer().getIcon(Icon.Type.SMILEY_HAPPY);
                screen.getNavigationDialog().castMessage(String.format(messages[0], lot.getOwner().getPersonaName(), lot.getId()), Color.GREEN);
                
                for(BaseActor actor : BaseActor.getList(getStage(), Bot.class)){
                    
                    Bot bot = (Bot)actor;
                    bot.getIcon(Icon.Type.SMILEY_SAD);
                    
                }
                
            }
            else{
                
                if(playerOffer){
                    
                    screen.getPlayer().getIcon(Icon.Type.SMILEY_SAD);
                    screen.getNavigationDialog().castMessage(String.format(messages[1], lot.getId()), Color.RED);
                    lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
                    
                }
                else{
                    
                    screen.getNavigationDialog().castMessage(String.format(messages[0], lot.getOwner().getPersonaName(), lot.getId()), Color.YELLOW);
                    lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
                    
                }
                
            }
            
        }
        
    }
    
    private void buy(Lot lot){ //for lots without owner;
    
        boolean player = false;
        
        if(lot.getOwner() != null 
                && lot.getOwner().equals(screen.getPlayer())){
            player = true;
        }
        
        boolean result = lot.buy(this, 0);
        
        if(result && !player){
            
            screen.getNavigationDialog().castMessage(String.format(messages[0], getPersonaName(), lot.getId()), Color.YELLOW);
            lot.getOwner().getIcon(Icon.Type.SMILEY_HAPPY);
             
        }
        else if(result && player){
        
            screen.getPlayer().getIcon(Icon.Type.SMILEY_HAPPY);
            screen.getNavigationDialog().castMessage(String.format(messages[0], getPersonaName(), lot.getId()), Color.GREEN);
       
        }
        
    }
    
    private void buyUpgrades(Lot lot, int value){
        
        if(lot.getUpgrade().getLevelByCode(value) < 10){
                       
            if(lot.buyUpgrade(value)){
                getIcon(Icon.Type.SMILEY_HAPPY);
            }
            else{
                getIcon(Icon.Type.SMILEY_ANGRY);
            }

        }
        
    }
    
    private void buildLot(Lot lot, Type_Lot type){
        
        int cod;
        
        if(type.equals(lot.getType()) 
                || lot.getType().equals(Type_Lot.LOT_BUILDING)){
            throw new IllegalArgumentException("INVALID ACTION (TYPE EQUALS TO LOT TYPE OR TRYING TO BUILD A LOT BUILDING)");
        }
        
        if(!lot.getType().equals(Type_Lot.LOT_HOUSE) 
                && !lot.getType().equals(Type_Lot.LOT_STORE)){
            
            if(lot.getType().equals(Type_Lot.LOT_STORE)){
                cod = 0;
            }
            else{
                cod = 1;
            }
            
        }
        else{
            
            if(lot.getType().equals(Type_Lot.LOT_STORE)){
                cod = 3;
            }
            else{
                cod = 2;
            }
            
        }

        if(lot.buildUpgrade(cod)){
            getIcon(Icon.Type.SMILEY_VERY_HAPPY);
        }
        else{
            getIcon(Icon.Type.SMILEY_ANGRY);
        }
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        timer += dt;
        testTimer += dt;
        
//        if(timer > reactionTimer){
//            
//            timer = 0;
//            
//            System.out.printf("\nPERSONA: " + getPersonaName() + "\t Money: " + getMoneyValue());
//            
//        }
//////        
//        addOffer(10000);
//        
        if(testTimer > testReactionTimer){
//             
            testTimer = 0;
            
////            for (Lot lot : getLots()) {
////                
////                Type_Lot[] type = {Type_Lot.LOT_STORE, Type_Lot.LOT_HOUSE};
////                
////                if(lot.getType().equals(Type_Lot.LOT_EMPTY) 
////                        || lot.getType().equals(Type_Lot.LOT_EMPTY_ABANDONED)){
////                    buildLot(lot, type[(int)MathUtils.random(0, 1)]);
////                }
////                else{
////                    
////                    if(lot.getType().equals(Type_Lot.LOT_STORE)){
////                        buildLot(lot, type[1]);
////                    }
////                    else if(lot.getType().equals(Type_Lot.LOT_HOUSE)){
////                        buildLot(lot, type[0]);
////                    }
////                    
////                }
           
////                    putToSell(lot);
////                    if(lot.getOfferSize() > 0){
////                        sellOffer(lot);
////                    }
//////
////                        int value = MathUtils.random(0, 5);
////                        
////                        buyUpgrades(getLots().get(0), value);
////
////                    if(lot.getUpgrade().getLevelByCode(value) < 10){
////                       
////                        if(lot.buyUpgrade(value)){
////                            System.out.printf("\n " + lot.getUpgrade().getLevelByCode(value));
////                        }
////                        else{
////                            System.out.printf("\n NO BUFUNFA!");
////                        }
////                        
////                    }
////                    else{
////                        System.out.printf("\n MAXIMO!");
////                    }
//
                
                
////            }
            
        }
        
    }
   
}
