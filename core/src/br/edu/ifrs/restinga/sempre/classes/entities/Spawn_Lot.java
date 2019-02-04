/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.entities;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Spawn_Lot extends Solid{
    
    private int id;
    private String orientation;
    
    public Spawn_Lot(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        id = (int)properties.get("id");
        orientation = (String)properties.get("orientation");
        
    }

}
