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
public class Icon_Lot extends BaseActor{
    
    public enum Type {ICON_BATTLE, ICON_DEFEAT, ICON_CANCEL, ICON_SELL, ICON_PARTNERSHIP, ICON_HIRE, ICON_BUYING, ICON_UPGRADE};
    public enum Duration {REPEAT_20, REPEAT_100, FOREVER, EXPAND};
    
    private Type type;
    
    public Icon_Lot(Float x, Float y, Stage s, Type type, Duration duration) {
        
        super(x, y, s);
        init(type, duration);
        
    }
    
    private void init(Type type, Duration duration){
        
        String path = "icons".concat(System.getProperty("file.separator"));
        
        Color color;
        
        this.type = type;

        if(type.equals(Type.ICON_BATTLE) || type.equals(Type.ICON_CANCEL) || type.equals(Type.ICON_DEFEAT)){
            
            color = Color.RED;
            
            if(type.equals(Type.ICON_BATTLE)){
                path = path.concat("icon_battle.png");
            }
            else if(type.equals(Type.ICON_DEFEAT)){
                path = path.concat("icon_defeat.png");
            }
            else{
                path = path.concat("icon_cancel.png");
            }
            
        }
        else if(type.equals(Type.ICON_BUYING) || type.equals(Type.ICON_UPGRADE)){

            if(type.equals(Type.ICON_BUYING)){
                path = path.concat("icon_buying.png");
            }
            else if(type.equals(Type.ICON_UPGRADE)){
                path = path.concat("icon_upgrade.png");
            }
            
            color = Color.YELLOW;
            
        }
        else{
            
            color = Color.BLUE;
            
            if(type.equals(Type.ICON_HIRE)){
                path = path.concat("icon_hire.png");
            }
            else if(type.equals(Type.ICON_PARTNERSHIP)){
                path = path.concat("icon_partnership.png");
            }
            else{
                path = path.concat("icon_sell.png");
            }
            
        }
         
        loadTexture(path);
        
        if(duration.equals(Duration.REPEAT_20)){
            addAction(Actions.repeat(20, Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.3f), 
                                                          Actions.color(color, 0.2f), 
                                                          Actions.scaleTo(1.0f, 1.0f, 0.3f), 
                                                          Actions.color(Color.WHITE, 0.2f))));
        }
        else if(duration.equals(Duration.REPEAT_100)){
            addAction(Actions.repeat(100, Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.3f), 
                                                          Actions.color(color, 0.2f), 
                                                          Actions.scaleTo(1.0f, 1.0f, 0.3f), 
                                                          Actions.color(Color.WHITE, 0.2f))));
        }
        else if(duration.equals(Duration.EXPAND)){
            
            addAction(Actions.sequence(
                      Actions.color(color),
                      Actions.scaleTo(0.5f, 0.5f, 0.2f),  
                      Actions.scaleTo(1.0f, 1.0f, 0.2f),
                      Actions.scaleTo(1.5f, 1.5f, 0.2f)));
            
        }
        else{
            addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.3f), 
                                                          Actions.color(color, 0.2f), 
                                                          Actions.scaleTo(1.0f, 1.0f, 0.3f), 
                                                          Actions.color(Color.WHITE, 0.2f))));
        }
        
    }

    public Type getType() {
        return type;
    }
    
    public void removeIcon(){
        getActions().removeIndex(0);
    }

    @Override
    public void act(float dt) {
    
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        if(getActions().size == 0){
            remove();
        }
        
    }
    
}
