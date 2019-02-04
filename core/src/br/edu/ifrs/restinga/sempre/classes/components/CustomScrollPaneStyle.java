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
import com.badlogic.gdx.graphics.Pixmap;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 *
 * @author Not275
 */
public class CustomScrollPaneStyle extends ScrollPaneStyle{

    String path = "hud".concat(System.getProperty("file.separator"));
    
    public CustomScrollPaneStyle() {
        
        init();
    
    }
    
    private void init(){
        
//        vScroll = new TextureRegionDrawable(
//                    new TextureRegion(
//                        new Texture(getFileHandler("Bar_2.png"))));
        
        vScrollKnob = new TextureRegionDrawable(
                        new TextureRegion(
                            new Texture(getFileHandler("Switch_1.png"))));

        Pixmap pixmap = new Pixmap(30, 30, Pixmap.Format.RGBA8888); //200x30
        pixmap.setColor(160f / 255f, 160f / 255f, 160f / 255f, 0.5f);
        pixmap.fill();

        vScroll = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        
        pixmap.dispose();
        
    }
    
    public FileHandle getFileHandler(String file){
        return BaseGame.util.getInternalFileHandle(path.concat(file));
    }
    
}
