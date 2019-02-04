/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import br.edu.ifrs.restinga.sempre.classes.components.CustomLabelStyle;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputMultiplexer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import com.badlogic.gdx.utils.Logger;

/**
 *
 * @author Not275
 */
public abstract class BaseGame extends Game{
    
    private static CustomLabelStyle labelStyle;
    private static CustomLabelStyle labelStyleButton;
    private static CustomLabelStyle labelTooltip;
    
    private static BaseGame game;
    
    public static CustomLabelStyle labelStyleFreeFont;
    public static CustomLabelStyle labelStyleHiero;

    public static TextButtonStyle textbuttonStyle;
    
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;
    
    public static int cheapLot = MathUtils.random(0,  11);
    
    public static Util util = new Util();
    
    public BaseGame(String project){
        
        game = this;
        Util.projectName = project;
        Util.logger = new Logger(project, Logger.DEBUG);
        
    }
    
    public BaseGame(String project, int screenWidth, int screenHeight){
        
        game = this;
        Util.projectName = project;
        Util.logger = new Logger(project, Logger.DEBUG);
        
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        
    }
    
    @Override
    public void create(){
        
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
 
        //using external program (Hiero)
        
        labelStyleHiero = new CustomLabelStyle(new BitmapFont(util.getInternalFileHandle("cooper.fnt")));
        //labelStyleHiero = new CustomLabelStyle(new BitmapFont(Gdx.files.local(util.getAssetsPath("cooper.fnt"))));
        
        //using libgdx methods
        labelStyleFreeFont = new CustomLabelStyle();
     
        textbuttonStyle = new TextButtonStyle();
        Texture buttonTexture = util.getInternalTexture("button.png");
        NinePatch buttonPatch = new NinePatch(buttonTexture, 24, 24, 24, 24);
        textbuttonStyle.up = new NinePatchDrawable(buttonPatch);
        textbuttonStyle.font = new BitmapFont(util.getInternalFileHandle("cooper.fnt"));
        textbuttonStyle.fontColor = Color.GRAY;
        
        labelStyle = new CustomLabelStyle();
        labelStyleButton = new CustomLabelStyle();
        labelTooltip = new CustomLabelStyle();
        
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 18;
        parameters.color = Color.WHITE;
        parameters.borderWidth = 2;
        parameters.borderColor = Color.BLACK;
        parameters.borderStraight = true;
        
        labelStyle.setParameters(parameters);
        
        parameters.borderWidth = 1;
        parameters.size = 16;
        parameters.color = Color.BLACK;
    
        labelStyleButton.setParameters(parameters);
        
        parameters.size = 12;
        parameters.borderColor = Color.WHITE;
        parameters.borderWidth = 1;
        
        labelTooltip.setParameters(parameters);
        
    }
    
    public static void setActiveScreen(BaseScreen screen){
        game.setScreen(screen);
    }
    
    public static String getProjectName(){
        return Util.projectName;
    }
    
    public static CustomLabelStyle getLabelStyle(){
        return labelStyle;
    }
    
    public static CustomLabelStyle getLabelButton(){
        return labelStyleButton;
    }
    
    public static CustomLabelStyle getLabelTooltip(){
        return labelTooltip;
    }

}
