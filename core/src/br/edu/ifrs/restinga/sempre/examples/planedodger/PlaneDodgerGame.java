/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.planedodger;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

/**
 *
 * @author Not275
 */
public class PlaneDodgerGame extends BaseGame{
    
    public PlaneDodgerGame() {
        super("planedodger");
    }
    
    @Override
    public void create(){
        
        super.create();
        
        setActiveScreen(new LevelScreen());
    }
    
}
