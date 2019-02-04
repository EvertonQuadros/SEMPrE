/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.effects;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Selection_Effect extends BaseActor{
    
    public Selection_Effect(Float x, Float y, Stage stage) {
        
        super(x, y, stage);

        init();
        
    }
    
    public Selection_Effect(Float x, Float y, Stage stage, Color color) {
        
        super(x, y, stage);

        init(color);
        
    }
  
    private void init(){
        
        loadTexture("elipse.png");
       
        addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 0.4f),
                                                    Actions.scaleTo(1.0f, 1.0f, 0.4f ))));
        
    }
    
    private void init(Color color){
        
        loadTexture("elipse_transparent.png");
       
        addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.4f),
                                                    Actions.scaleTo(1.0f, 1.0f, 0.4f ))));
       
        setColor(color);
        
    }
    
    public void removeActor(){
        this.remove();
    }

}
