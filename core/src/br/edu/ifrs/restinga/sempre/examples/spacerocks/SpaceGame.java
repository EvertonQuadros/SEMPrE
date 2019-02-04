/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.spacerocks;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;

/**
 *
 * @author Not275
 */
public class SpaceGame extends BaseGame{

    public SpaceGame() {
        super("spacerocks");
    }
    
    @Override
    public void create(){
        
        super.create();
        
        setActiveScreen(new LevelScreen());
        
    }
    
}
