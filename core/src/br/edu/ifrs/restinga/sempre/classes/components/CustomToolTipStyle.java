/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Not275
 */
public class CustomToolTipStyle extends TextTooltipStyle{

    public CustomToolTipStyle() {
        
        Pixmap pixmap = new Pixmap(30, 30, Pixmap.Format.RGBA8888); //200x30
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
                
        pixmap.dispose();
                
        label = BaseGame.getLabelTooltip();
        //wrapWidth = 120f;
        
    }
    
}
