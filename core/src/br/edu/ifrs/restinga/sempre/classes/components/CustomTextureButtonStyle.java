/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import static br.edu.ifrs.restinga.sempre.classes.base.BaseGame.util;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class CustomTextureButtonStyle extends ButtonStyle{

    TextureRegion buttonRegion;
    Texture buttonTexture;
    
    public CustomTextureButtonStyle(String fileName) {
        
        buttonTexture = util.getInternalTexture(fileName);
        
        buttonRegion = new TextureRegion(buttonTexture);
        
        up = new TextureRegionDrawable(buttonRegion);
        
    }
    
    public CustomTextureButtonStyle(TextureRegion texture){

        buttonRegion = new TextureRegion(texture);
        
        up = new TextureRegionDrawable(buttonRegion);
        
    }

    public TextureRegion getButtonRegion() {
        return buttonRegion;
    }

    public Texture getButtonTexture() {
        return buttonTexture;
    }
    
}
