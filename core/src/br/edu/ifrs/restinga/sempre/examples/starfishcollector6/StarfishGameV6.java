/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector6;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

/**
 *
 * @author Not275
 */
public class StarfishGameV6 extends BaseGame{

    public StarfishGameV6() {
        super("starfishcollector6");
    }
    
    @Override
    public void create(){
 
        super.create();
        
        setActiveScreen(new MenuScreen());
        
    }
    
}
