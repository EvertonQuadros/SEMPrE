/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jumpingjack;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

/**
 *
 * @author Not275
 */
public class JumpingJackGame extends BaseGame{

    public JumpingJackGame() {
        super("jumpingjack", 800, 640);
    }

    @Override
    public void create() {
        
        super.create(); //To change body of generated methods, choose Tools | Templates.
        
        setActiveScreen(new LevelScreen()); 
        
    }
    
}
