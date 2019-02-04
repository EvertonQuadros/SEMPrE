/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jigsawpuzzle;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private final int numberRows = 3;
    private final int numberCols = 3;
    
    private Label messageLabel;
    
    @Override
    public void initActors() {
        
        BaseActor background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("background.jpg");
        
        Texture texture = new Texture(BaseGame.util.getInternalFileHandle("sun.jpg"), true);
        
        int imageWidth = texture.getWidth();
        int imageHeight = texture.getHeight();
        int pieceWidth = imageWidth / numberCols;
        int pieceHeight = imageHeight / numberRows;
        
        TextureRegion[][] temporaryTextureRegion = TextureRegion.split(texture, pieceWidth, pieceHeight);
        
        for(int rows = 0 ; rows < numberRows; rows++){
            
            for(int cols = 0; cols < numberCols; cols++){
                
                //create puzzle piece at random locations on left half of screen;
                int pieceX = MathUtils.random(0, 400 - pieceWidth);
                int pieceY = MathUtils.random(0, 600 - pieceHeight);
                PuzzlePiece puzzlePiece = new PuzzlePiece(pieceX, pieceY, mainStage);
                
                Animation<TextureRegion> animation = new Animation(1, temporaryTextureRegion[rows][cols]);
                puzzlePiece.setAnimation(animation);
                puzzlePiece.setRow(rows);
                puzzlePiece.setCol(cols);
                
                int marginX = (400 - imageWidth) / 2;
                int marginY = (600 - imageHeight) / 2;
                
                int areaX = (400 + marginX) + pieceWidth * cols;
                int areaY = (600 - marginY - pieceHeight) - pieceHeight * rows;
                
                PuzzleArea puzzleArea = new PuzzleArea(areaX, areaY, mainStage);
                puzzleArea.setSize(pieceWidth, pieceHeight);
                puzzleArea.setBoundaryRectangle();
                puzzleArea.setRow(rows);
                puzzleArea.setCol(cols);
                
            }
            
        }
        
    }
    
    @Override
    public void initComponents() {
        
        messageLabel = new Label("You Win!!!", BaseGame.labelStyleFreeFont);
        messageLabel.setColor(Color.CYAN);
        messageLabel.setVisible(false);

        uiTable.add(messageLabel).expandX().expandY().bottom().pad(50);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}

    @Override
    public void update(float dt) {
    
       boolean solved = true;
       
       for(BaseActor actor : BaseActor.getList(mainStage, PuzzlePiece.class)){
           
           PuzzlePiece puzzlePiece = (PuzzlePiece)actor;
           
           if(!puzzlePiece.isCorrectlyPlaced()){
               solved = false;
           }
           
       }
       
       messageLabel.setVisible(solved);
        
    }

}
