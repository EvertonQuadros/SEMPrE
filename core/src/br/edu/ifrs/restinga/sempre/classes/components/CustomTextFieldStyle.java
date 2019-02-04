/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class CustomTextFieldStyle extends TextFieldStyle{

    private static final FreeTypeFontGenerator.FreeTypeFontParameter fontParameters 
                   = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static FreeTypeFontGenerator fontGenerator;
    
    public CustomTextFieldStyle() {
        
        fontGenerator = new FreeTypeFontGenerator(BaseGame.util.getInternalFileHandle("OpenSans.ttf"));
        
        fontParameters.size = 24; //48
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1;
        fontParameters.borderColor = Color.WHITE;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;
  
        this.font = fontGenerator.generateFont(fontParameters);
        this.fontColor = Color.BLACK;
        
        Pixmap pixmap = new Pixmap(60, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        
        background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
        
        pixmap = new Pixmap(3, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        
        cursor = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
        
    }
    
}
