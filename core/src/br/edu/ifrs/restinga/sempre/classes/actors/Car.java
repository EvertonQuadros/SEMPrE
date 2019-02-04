/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;

import br.edu.ifrs.restinga.sempre.classes.effects.Barrier;

import br.edu.ifrs.restinga.sempre.classes.entities.Car_Waitpoint;
import br.edu.ifrs.restinga.sempre.classes.entities.Crossing;
import br.edu.ifrs.restinga.sempre.classes.entities.Entity;
import br.edu.ifrs.restinga.sempre.classes.entities.Spawn_Lot;
import br.edu.ifrs.restinga.sempre.classes.entities.Sidewalk;
import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import br.edu.ifrs.restinga.sempre.classes.entities.Waypoint;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Not275
 */
public class Car extends BaseActor{

    private Waypoint waypoint = null;
    private Solid solid;
    private final float lifeTime = 15;
    private float lifeTimer = 0;
    
    public Car(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        init(properties);
        
    }
    
    private void init(MapProperties properties){
        
        String[] cars = {"car_1.png","car_2.png"};
        Color[] colors = {Color.CLEAR, Color.TEAL, Color.GREEN, Color.WHITE, Color.YELLOW, Color.MAGENTA, Color.VIOLET};
        
        int i = MathUtils.random(0, 1);
        
        loadTexture("actors".concat(System.getProperty("file.separator"))
                            .concat(cars[i]));
        
        setBoundaryPolygon(8);
        
        if(i == 0){
            
            setAcceleration(250);
            setMaxSpeed(400);
            
        }
        else{
            
            setAcceleration(350);
            setMaxSpeed(500);
            
        }
        
        setDeceleration(500);
        
        setColor(colors[MathUtils.random(0, colors.length - 1)]);
        
        setRotation((float)properties.get("angle"));
      
        solid = new Solid(0, 0, getStage());
        solid.loadTexture("white.png");
        solid.setColor(Color.RED);
        solid.setWidth(10);
        solid.setHeight(10);
        solid.setVisible(false);
        
        
    }

    private void stop(){
        
        getAccelerationVec().setZero();

        if(getSpeed() - 30 < 0){
            setSpeed(0);
        }
        else{
            setSpeed(getSpeed() - 30);
        }
        
    }
    
    public void removeSolid(){
        solid.remove();
    }
    
    @Override
    public void act(float dt) {

        super.act(dt);
        
        lifeTimer += dt;
        
        if(lifeTimer > lifeTime){
            
            solid.remove();
            remove();
            
        }
       
        if(getRotation() == 0){
            
            solid.setWidth(60);
            solid.setHeight(10);
            solid.centerAtActor(this);
            solid.centerAtPosition(getX() + 210, getY() + 50);
            
        }
        else{
            
            solid.setWidth(10);
            solid.setHeight(60);
            //solid.centerAtActor(this);
            
            solid.centerAtPosition(getX() + 100, getY() + 170);
        }
        
        solid.setBoundaryRectangle();
        
//        if(Gdx.input.isKeyPressed(Keys.UP)){
//            accelerateForward();
//        }
//        
//        if(Gdx.input.isKeyPressed(Keys.DOWN)){
//           getAccelerationVec().setZero();
//        }        
//
//        
//        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
//           
//            if(getSpeed() > 0){
//                turnAtAngle(getRotation() - 5);
//            }
//           
//        }
//        
//        if(Gdx.input.isKeyPressed(Keys.LEFT)){
//            
//            if(getSpeed() > 0){
//                turnAtAngle(getRotation() + 5);
//            }
//         
//        }

        for(BaseActor actor : BaseActor.getList(getStage(), Waypoint.class)){
           
            Waypoint waypointActor = (Waypoint) actor;
            
            if(overlaps(waypointActor) && waypointActor.getWaypointRoute() == -1){
                
                if(waypoint != null){
                    
                    if(waypoint.getWaypointIntersection() < waypointActor.getWaypointIntersection() 
                            && waypoint.getWaypointRoute() == waypointActor.getWaypointRoute()){
                     
                        waypoint = waypointActor;
                        
                    }
                    
                }
                else{
                    waypoint = waypointActor;
                }
                
            }
            
        }
        
        if(waypoint == null){
            accelerateAtAngle(getRotation());
        }
        else{
      
            if(!(waypoint.getWaypointRotation() == getRotation())){
                turnAtAngle(waypoint.getWaypointRotation());
            }
            else{
                accelerateAtAngle(waypoint.getWaypointRotation());
            }
    
        }

        if(getSpeed() > 0){
            setRotation(getMotionAngle());
        }
        
        for(BaseActor actor : BaseActor.getList(getStage(), Solid.class)){
            
            if(actor instanceof Sidewalk){
                preventOverlap(actor);
            }
            else if(actor instanceof Barrier 
                    || actor instanceof Spawn_Lot){
                preventOverlap(actor);
            }
            
        }
        
        for(BaseActor actor : BaseActor.getList(getStage(), Car_Waitpoint.class)){
                
            Car_Waitpoint waitpoint = (Car_Waitpoint) actor;
            
            if(overlaps(waitpoint) && waitpoint.isActive()){
                
                for(BaseActor crossings : BaseActor.getList(getStage(), Crossing.class)){
                
                    Crossing crossing = (Crossing) crossings;

                    if(waitpoint.getOrientation().equals(crossing.getOrientation()) 
                            && !crossing.isCarEnabled() 
                            && !overlaps(crossing.getSolid())){
                        
                        stop();
                        
                    }
                   
                }
                
            }    
                
        }       

        for(BaseActor cars : BaseActor.getList(getStage(), Car.class)){
            
            if(cars != this){
            
                if(solid.overlaps(cars)){
                    stop();
                }
                
            }
            
        }
        
        for(BaseActor actor : BaseActor.getList(getStage(), Entity.class)){
 
            if(isWithinDistance(0.5f, actor) && actor.getY() > getY()){
                actor.toFront();
            }
            
        }
        
        applyPhysics(dt);

    }
    
}
