/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.rectangledestroyer;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Wall extends BaseActor{

    public Wall(float x, float y, float width, float height, Stage stage) {
        
        super(x, y, stage);
        
        init(width, height);
        
    }
    
    private void init(float width, float height){
        
        loadTexture("white-square.png");
        setSize(width, height);
        setColor(Color.GRAY);
        setBoundaryRectangle();
        
    }
    
}
