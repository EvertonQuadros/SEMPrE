/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actions;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;
import com.badlogic.gdx.scenes.scene2d.Action;

/**
 *
 * @author Not275
 */
public class SetTextAction extends Action{

    protected String textToDisplay;
    
    public SetTextAction(String text){
        textToDisplay = text;
    }
    
    @Override
    public boolean act(float delta) {
        
        DialogBox dialogBox = (DialogBox) target;
        dialogBox.setText(textToDisplay);
        return true;
        
    }
    
}
