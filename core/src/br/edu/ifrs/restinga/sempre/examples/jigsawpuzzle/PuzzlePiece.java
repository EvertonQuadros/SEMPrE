/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.jigsawpuzzle;

import br.edu.ifrs.restinga.sempre.classes.base.DragAndDropActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class PuzzlePiece extends DragAndDropActor{
    
    private int row;
    private int col;
    
    private PuzzleArea puzzleArea;
    
    public PuzzlePiece(float x, float y, Stage stage) {
        super(x, y, stage);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public PuzzleArea getPuzzleArea() {
        return puzzleArea;
    }

    public void setPuzzleArea(PuzzleArea puzzleArea) {
        this.puzzleArea = puzzleArea;
    }
 
    public void clearPuzzleArea(){
        puzzleArea = null;
    }
    
    public boolean hasPuzzleArea(){
        return puzzleArea != null;
    }
    
    public boolean isCorrectlyPlaced(){
        return (hasPuzzleArea() 
                && this.getRow() == puzzleArea.getRow()
                && this.getCol() == puzzleArea.getCol());
    }

    @Override
    public void onDragStart() {
    
        if(hasPuzzleArea()){
            
            PuzzleArea puzzleA = getPuzzleArea();
            puzzleA.setTargetable(true);
            clearPuzzleArea();
            
        }
    
    }

    @Override
    public void onDrop() {
    
        if(hasDropTarget()){
            
            PuzzleArea puzzleA = (PuzzleArea) getDropTarget();
            moveToActor(puzzleA);
            setPuzzleArea(puzzleA);
            puzzleA.setTargetable(false);
            
        }
    
    }
    
}
