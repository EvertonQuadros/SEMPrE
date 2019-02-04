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
public class Waypoint extends Solid{
    
    private int route;
    private int intersection;
    
    private float rotation;
    private float rotation2;
    private float rotation3;
    
    public Waypoint(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        route = (int)properties.get("route");
        intersection = (int)properties.get("intersection");
        rotation = (float)properties.get("rotation");
        rotation2 = (float)properties.get("rotation2");
        rotation3 = (float)properties.get("rotation3");
        
    }

    public int getWaypointRoute() {
        return route;
    }

    public int getWaypointIntersection() {
        return intersection;
    }
    
    public float getWaypointRotation() {
        return rotation;
    }
    
    public float getWaypointRotation2() {
        return rotation2;
    }

    public float getWaypointRotation3(){
        return rotation3;
    }
    
}
