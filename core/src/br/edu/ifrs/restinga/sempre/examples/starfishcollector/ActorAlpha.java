/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Not275
 */
public class ActorAlpha{
        
    private final Texture actorTexture;
    private final Rectangle actorRectangle;
    
    private float actorX, actorY;

    public ActorAlpha(String pathToFile, float actorX, float actorY){

        this.actorX = actorX;
        this.actorY = actorY;

        this.actorTexture = BaseGame.util.getInternalTexture(pathToFile);
        this.actorRectangle = new Rectangle(actorX, actorY, actorTexture.getWidth(), actorTexture.getHeight()); 

    }

    
    public Texture getActorTexture() {
        return actorTexture;
    }

    public Rectangle getActorRectangle() {
        return actorRectangle;
    }
    
    public float getActorX() {
        return actorX;
    }

    public void addActorX(int i) {
        this.actorX = actorX + i;
    }
    
    public void subsActorX(int i) {
        this.actorX = actorX - i;
    }

    public float getActorY() {
        return actorY;
    }

    public void addActorY(int i) {
        this.actorY = actorY + i;
    }
    
    public void subsActorY(int i) {
        this.actorY = actorY - i;
    }

}
