/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class CustomProgressBarStyle extends ProgressBarStyle{
    
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    
    private final int width;
    private final int height;
    
    
    public CustomProgressBarStyle(Color backgroundColor, Color barColor, int width, int height){
 
        this.width = width;
        this.height = height;
        
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888); //200x30
        pixmap.setColor(backgroundColor);
        pixmap.fill();
        
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        
        background = drawable;
        
        pixmap = new Pixmap(0, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(barColor);
        pixmap.fill();
        
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        
        knob = drawable;
        
        pixmap = new Pixmap(10, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(barColor);
        pixmap.fill();
        
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        
        knobBefore = drawable;
        
    }
 
    public void setBarColor(Color barColor){
        
        pixmap = new Pixmap(0, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(barColor);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        
        knob = drawable;
        
        pixmap = new Pixmap(10, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(barColor);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        
        knobBefore = drawable;
        
        
    }
    
    public void setBackgroundColor(Color backgroundColor){
        
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(backgroundColor);
        pixmap.fill();
        
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        background = drawable;
        pixmap.dispose();
        
    }
    
}
