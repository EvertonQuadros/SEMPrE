/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 *
 * @author Not275
 */
public class CustomTextButtonStyle extends TextButtonStyle{
    
    public CustomTextButtonStyle() {
    
        Texture buttonTexture = BaseGame.util.getInternalTexture("button.png");
        NinePatch buttonPatch = new NinePatch(buttonTexture, 24, 24, 24, 24);
        
        up = new NinePatchDrawable(buttonPatch);
        font = new BitmapFont(BaseGame.util.getInternalFileHandle("cooper.fnt"));
        fontColor = Color.GRAY;

    }
    
//    
//    public CustomTextButtonStyle(String textureName){
//    
//        Texture buttonTexture = BaseGame.util.getInternalTexture(textureName);
//        NinePatch buttonPatch = new NinePatch(buttonTexture, 24, 24, 24, 24);
//        
//        up = new NinePatchDrawable(buttonPatch);
//        
//        FreeTypeFontGenerator fontGenerator =
//        new FreeTypeFontGenerator(Util.FileHandlerFunctions.getBase("OpenSans.ttf"));
//        
//        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
//        fontParameters.size = 18;
//        fontParameters.color = Color.WHITE;
//        fontParameters.borderWidth = 1;
//        fontParameters.borderColor = Color.BLACK;
//        fontParameters.borderStraight = true;
//        
//        font = fontGenerator.generateFont(fontParameters);
//   
//    }
    
    public CustomTextButtonStyle(String fileName, String textureName) {
    
        Texture buttonTexture = BaseGame.util.getInternalTexture(textureName);
        
        NinePatch buttonPatch = new NinePatch(buttonTexture, 24, 24, 24, 24);
        
        up = new NinePatchDrawable(buttonPatch);
     
//        Texture buttonTexture2 = BaseGame.util.getInternalTexture(textureName);
//        
//        NinePatch buttonPatch2 = new NinePatch(buttonTexture2, 24, 24, 24, 24);
//        
//        down = new NinePatchDrawable(buttonPatch2);
        
//        Texture buttonTexture2 = BaseGame.util.getInternalTexture(textureNameDown);
//        NinePatch buttonPatch2 = new NinePatch(new TextureRegion(textureRegion[1][1]), 24, 24, 24, 24);
//        
//        down = new NinePatchDrawable(buttonPatch2);
        
        font = new BitmapFont(BaseGame.util.getInternalFileHandle(fileName));
        fontColor = Color.GRAY;
        
    }
    
    public CustomTextButtonStyle(String fontName, Color fontColor) {
    
        Texture buttonTexture = BaseGame.util.getInternalTexture("button.png");
        NinePatch buttonPatch = new NinePatch(buttonTexture, 24, 24, 24, 24);
        
        up = new NinePatchDrawable(buttonPatch);
        font = new BitmapFont(BaseGame.util.getInternalFileHandle(fontName));
        this.fontColor = fontColor;
      
    }

}
