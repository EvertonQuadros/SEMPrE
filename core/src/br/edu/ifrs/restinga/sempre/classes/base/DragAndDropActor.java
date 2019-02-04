/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import br.edu.ifrs.restinga.sempre.classes.base.DropTargetActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Not275
 */
public class DragAndDropActor extends BaseActor{
    
    private final DragAndDropActor self;
    private DropTargetActor dropTarget;
    
    private float grabOffsetX;
    private float grabOffsetY;
    
    private boolean draggable;
    
    private float startPositionX;
    private float startPositionY;
    
    public DragAndDropActor(float x, float y, Stage stage) {
        
        super(x, y, stage);
        
        draggable = true;
        
        self = this;
        
        addListener(new InputListener(){
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //float x = offsetX | float y = offsetY , on this concept;
               
                if(!self.isDraggable()){
                    return false;
                }
                
                self.grabOffsetX = x;
                self.grabOffsetY = y;
                
                self.startPositionX = self.getX();
                self.startPositionY = self.getY();
                
                self.toFront();
                
                self.addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f));
                
                self.onDragStart();
                
                return true;
                
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                
                self.setDropTarget(null);
                float closestDistance = Float.MAX_VALUE;
                
                for(BaseActor actor : BaseActor.getList(self.getStage(), DropTargetActor.class)){
                    
                    DropTargetActor target = (DropTargetActor) actor;
                    
                    if(target.isTargetable() && self.overlaps(target)){
                        
                        float currentDistance = Vector2.dst(self.getX(), self.getY(), target.getX(), target.getY());
                        
                        //check if this target is even closer
                        if(currentDistance < closestDistance){
                            
                            self.setDropTarget(target);
                            closestDistance = currentDistance;
                            
                        }
                        
                    }
                    
                }
                
                self.addAction(Actions.scaleTo(1.00f, 1.00f, 0.25f));
                
                self.onDrop();
                
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) { //float x = offsetX | float y = offsetY , on this concept;
                
                float deltaX = x - self.grabOffsetX;
                float deltaY = y - self.grabOffsetY;
                
                self.moveBy(deltaX, deltaY);
                
            }
            
        });
        
    }

    @Override
    public void act(float dt) {
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean hasDropTarget(){
        return (dropTarget != null);
    }
    
    public void setDropTarget(DropTargetActor dropTarget){
        this.dropTarget = dropTarget;
    }
    
    public DropTargetActor getDropTarget(){
        return dropTarget;
    }
 
    public void setDraggable(boolean d){
        draggable = d;
    }
    
    public boolean isDraggable(){
        return draggable;
    }
    
    public void moveToActor(BaseActor other){
        
        float x = other.getX() + (other.getWidth() - this.getWidth()) / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;
        addAction(Actions.moveTo(x, y, 0.50f, Interpolation.pow3));
        
    }
    
    public void moveToStart(){
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3));
    }
 
    public void onDragStart(){}
            
    public void onDrop(){}
    
}
