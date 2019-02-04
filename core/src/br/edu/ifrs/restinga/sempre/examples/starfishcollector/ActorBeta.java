/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.starfishcollector;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Not275
 */
public class ActorBeta extends Actor{
    
    private final String ActorName;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    
    public ActorBeta(String name){
        
        super();

        textureRegion = new TextureRegion();
        rectangle = new Rectangle();
        this.ActorName = name;
        
    }
    
    public void setTexture(Texture t){
        
        textureRegion.setRegion(t);
        this.setSize(t.getWidth(), t.getHeight());
        rectangle.setSize(t.getWidth(), t.getHeight());
        
    }
    
    public Rectangle getRectangle(){
        
        rectangle.setPosition(getX(), getY());
        return rectangle;
        
    }
    
    public boolean overlaps(ActorBeta other){
        return this.getRectangle().overlaps(other.getRectangle());
    }
    
    @Override
    public void act(float dt){
        super.act(dt);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha){
        
        super.draw(batch, parentAlpha); 
        
        Color c = getColor();
        
        batch.setColor(c.r, c.g, c.b, c.a);
        
        if(isVisible()){
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        
    } 

    public String getActorName() {
        return ActorName;
    }
    
}
