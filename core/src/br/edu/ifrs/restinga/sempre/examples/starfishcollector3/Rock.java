/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector3;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Rock extends BaseActor{
    
    private final String fileName = "rock.png";
    
    public Rock(float x, float y, Stage s) {
     
        super(x, y, s);
  
        init();
        
    }
    
    private void init(){
        
        loadTexture(fileName);
        setBoundaryPolygon(8);
        
    }
    
}
