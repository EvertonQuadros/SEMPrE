/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actions;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;

/**
 *
 * @author Not275
 */
public class TypewriterAction extends SetTextAction{
    
    private float elapsedTime;
    private float charactersPerSecond;
    
    public TypewriterAction(String text) {
        
        super(text);
        elapsedTime = 0;
        charactersPerSecond = 30;
        
    }
    
    @Override
    public boolean act(float deltaTime){
        
        elapsedTime += deltaTime;
        int numberOfCharacters = (int)(elapsedTime * charactersPerSecond);
        
        if(numberOfCharacters > textToDisplay.length()){
            numberOfCharacters = textToDisplay.length();
        }
        
        String partialText = textToDisplay.substring(0, numberOfCharacters);
        DialogBox dialogBox = (DialogBox) target;
        dialogBox.setText(partialText);
        
        //Return: action is complete when all characters have been displayed;
        return (numberOfCharacters >= textToDisplay.length());
        
    }
    
}
