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
public class Lot_Waypoint extends Solid{
    
    int id;
    
    public Lot_Waypoint(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        id = (int)properties.get("id");
        
    }
 
    public int getId(){
        return id;
    }
    
}
