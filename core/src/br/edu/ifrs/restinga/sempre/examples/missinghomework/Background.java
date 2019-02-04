/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.missinghomework;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Background extends BaseActor{
    
    public Animation hallway;
    public Animation classroom;
    public Animation scienceLab;
    public Animation library;
    
    public Background(float x, float y, Stage stage){
        
        super(x, y, stage);
        
        init();
        
    }
    
    private void init(){
        
        hallway = loadTexture("bg-hallway.jpg");
        classroom = loadTexture("bg-classroom.jpg");
        scienceLab = loadTexture("bg-science-lab.jpg");
        library = loadTexture("bg-library.jpg");
        setSize(800,600);
        
    }
    
}
