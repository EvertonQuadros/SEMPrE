/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.rectangledestroyer10;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Wall extends BaseActor{

    public Wall(Float x, Float y, Stage stage){
        
        super(x, y, stage);
        init(10,10);
        
    }
    
    public Wall(float x, float y, float width, float height, Stage stage) {
        
        super(x, y, stage);
        init(width, height);
        
    }
    
    public Wall (MapProperties properties, Stage stage){
        
        super((float)properties.get("x"),
              (float)properties.get("y"), stage);
        
        init((float)properties.get("width"), (float)properties.get("height"));
        
    }
    
    private void init(float width, float height){
  
        loadTexture("wall-square.png");
        setSize(width, height);
        setColor(Color.GRAY);
        setBoundaryRectangle();
        
    }
    
}
