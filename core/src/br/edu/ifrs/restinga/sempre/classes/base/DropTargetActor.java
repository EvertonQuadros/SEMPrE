/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class DropTargetActor extends BaseActor{
    
    private boolean targetable;
    
    public DropTargetActor(float x, float y, Stage stage) {
        
        super(x, y, stage);
        targetable = true;
        
    }
    
    public void setTargetable(boolean targetable){
        this.targetable = targetable;
    }
    
    public boolean isTargetable(){
        return targetable;
    }
    
}
