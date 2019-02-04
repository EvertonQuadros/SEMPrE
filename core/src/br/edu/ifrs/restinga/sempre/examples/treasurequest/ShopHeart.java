/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class ShopHeart extends BaseActor{
    
    public ShopHeart(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init();
        
    }
    
    private void init(){
        loadTexture("heart-icon.png");
    }
    
}
