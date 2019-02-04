/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.missinghomework;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Kelsoe extends BaseActor {
 
    public Animation normal;
    public Animation sad;
    public Animation lookLeft;
    public Animation lookRight;

    public Kelsoe(float x, float y, Stage stage) {
    
        super(x, y, stage);
        
        init();
        
    }
 
    private void init(){
        
        normal = loadTexture("kelsoe-normal.png");
        sad = loadTexture("kelsoe-sad.png");
        lookLeft = loadTexture("kelsoe-look-left.png");
        lookRight = loadTexture("kelsoe-look-right.png");
        
    }
    
}
