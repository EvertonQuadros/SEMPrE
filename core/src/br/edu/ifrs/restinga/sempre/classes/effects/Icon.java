/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.effects;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class Icon extends BaseActor{
    
    public enum Type {SMILEY_HAPPY, SMILEY_VERY_HAPPY,
                      SMILEY_ANGRY, SMILEY_VERY_ANGRY,
                      SMILEY_SAD};
    
    public Icon(Float x, Float y, Stage stage, Type type) {
        
        super(x, y, stage);
        
        init(type);
        
    }

    private void init(Type type){
        
        String path = "smileys".concat(System.getProperty("file.separator"));
        
        if(type.equals(Type.SMILEY_HAPPY)){
            loadTexture(path.concat("icon_happy.gif"));
        }
        else if(type.equals(Type.SMILEY_VERY_HAPPY)){
            loadTexture(path.concat("icon_vhappy.gif"));
        }
        else if(type.equals(Type.SMILEY_ANGRY)){
            loadTexture(path.concat("icon_angry.gif"));
        }
        else if(type.equals(Type.SMILEY_VERY_ANGRY)){
            loadTexture(path.concat("icon_vangry.gif"));
        }
        else{
            loadTexture(path.concat("icon_sad.gif"));
        }
   
        addAction(Actions.repeat(25, Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 0.15f),
                                                      Actions.scaleTo(1.0f, 1.0f, 0.15f))));
        
    }
    
    public void removeAction(){
        getActions().removeIndex(0);
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        
        if(getActions().size == 0){
            this.remove();
        }
        
    }
    
}
