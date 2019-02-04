/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 *
 * @author Not275
 */
public class CustomLabelStyle extends LabelStyle{

    private static FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static FreeTypeFontGenerator fontGenerator;
    
    public CustomLabelStyle() {
        
        super();
  
        fontGenerator = new FreeTypeFontGenerator(BaseGame.util.getInternalFileHandle("OpenSans.ttf"));
        
        fontParameters.size = 24; //48
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;
  
        this.font = fontGenerator.generateFont(fontParameters);
      
    }
    
    public CustomLabelStyle(FileHandle customFont) {
        
        super();
  
        fontGenerator = new FreeTypeFontGenerator(customFont);

        this.font = fontGenerator.generateFont(fontParameters);
        
    }
    
    public CustomLabelStyle(BitmapFont bitFont){
        
        super();
 
        this.font = bitFont;
        
    }
   
    public void setFont(BitmapFont bitFont){
        this.font = bitFont;
    }
    
    public void setParameters(FreeTypeFontParameter fontParameters){
        this.font = fontGenerator.generateFont(fontParameters);
    }
    
    
}
