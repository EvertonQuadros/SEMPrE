/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product.Type_Product;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseStage;

import br.edu.ifrs.restinga.sempre.classes.effects.Selection_Effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author Not275
 */
public class Player extends Persona{

    private int value = 0;
    
    public Player(MapProperties properties, BaseStage stage, String texture, String name, int idade, Color color) {
        
        super(properties, stage, texture, color);
        
        initPlayer(name, idade);

        playerContrast = new Selection_Effect(0f, 0f, getStage(), Color.GREEN);
        playerContrast.setZIndex(1);       
        
    }

    @Override
    public void addMoney(int value) {
        
        this.value += value;
        
        super.addMoney(value); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void subMoney(int value) {
        
        this.value -= value;
        
        super.subMoney(value); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    public int getValue(){
        return value;   
    }
    
    public void addValue(int value){
        this.value += value;
    }
    
    public void subValue(int value){
        this.value -= value;
    }
    
    public void setValue(int value){
        this.value = value;
    }

    private void initPlayer(String name, int age){
   
        if(name != null){
            setPersonaName(name);
        }
        
        if(age < 0 || age > 65){
            setAge(MathUtils.random(16, 65));
        }
        
        setNecessity(new Product(Type_Product.values()[0], 0));
    
        setPerception(0.5f); //overall first impression, are generated when walker completes a trade;

        setNecessityLevel(0f); //more necessity are higher probability to accept lower quality and higher prices;

        setPatience(0f); //less patience are more likety to give up when in lot (if too long).

        setProductivity(0.5f); //productivity used to employee, define time to attend a consumer (walker). productivity < patience are likely to generate giveups.

        setMoney(1000000);
        
    }
    
    @Override
    public void enterLot(Lot lot){
        
        lot.select();
        
        super.enterLot(lot);
  
    }
    
    @Override
    public void exitLot(){
       
        getCurrentLot().deselect();
        
        super.exitLot();
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
    
            for(BaseActor actor : BaseActor.getList(getStage(), Lot.class)){

                Lot lot = (Lot)actor;

                if(overlaps(lot) && overlaps(lot.getEntrance()) && !isInsideLot() && !lot.getType().equals(Type_Lot.LOT_BUILDING) &&
                           (lot.getOrientation().equals("South") && Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                            lot.getOrientation().equals("North") && Gdx.input.isKeyPressed(Input.Keys.UP) ||
                            lot.getOrientation().equals("West") && Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                            lot.getOrientation().equals("East") && Gdx.input.isKeyPressed(Input.Keys.RIGHT))){
                    enterLot(lot);
                }
                else{
                    preventOverlap(lot);
                }

            }

        alignCamera();
        
    }
    
}
