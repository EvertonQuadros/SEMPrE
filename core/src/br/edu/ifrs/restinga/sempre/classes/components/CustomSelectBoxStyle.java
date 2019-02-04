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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class CustomSelectBoxStyle extends SelectBoxStyle{

    public CustomSelectBoxStyle() {
        
        super();
        
        init();
        
    }
    
    private void init(){
        
        listStyle = new ListStyle();
        
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888); //200x30
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        
        listStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        listStyle.font = new BitmapFont(BaseGame.util.getInternalFileHandle("customhierofont.fnt"));
        
        listStyle.fontColorSelected = Color.GRAY;
        listStyle.fontColorUnselected = Color.WHITE;
       
        background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
      
        font = new BitmapFont(BaseGame.util.getInternalFileHandle("customhierofont.fnt"));
        
        fontColor = Color.WHITE;
        disabledFontColor = Color.GRAY;
        
        pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
     
        backgroundOpen = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
        
        pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
               
        backgroundOver = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        listStyle.selection = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
        
        scrollStyle = new CustomScrollPaneStyle();
        
    }
    
}
