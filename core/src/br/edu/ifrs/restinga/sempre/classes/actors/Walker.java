/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseStage;

import br.edu.ifrs.restinga.sempre.classes.entities.Waypoint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class Walker extends Persona{
 
    private Waypoint waypoint = null;
    private int targetRoute = -1; //target route; //later this will be calculated by necessity type from lot.
    private int targetIntersection; //initial intersection //1 or 2;
    
    private int rebounce = 2; //number of retries, in case of test (if necessity more than 0);
                              //if fail walker will leave. 0.5 = 50% of fail/success, in case of sucess rebounce - 1; 
                              //if 0 will leave without test.
    
    private boolean changeSidewalk = false;
    private boolean reRouting = false;
    
    public Walker(MapProperties properties, BaseStage stage) {
    
        super(properties, stage);
    
        init(properties);
        
    }
    
    private void init(MapProperties properties){
  
        if((float)properties.get("width") > 40){
            setPosition((float)properties.get("x") + MathUtils.random(-40, 40), 
                        (float)properties.get("y") + MathUtils.random(-20, 20));
        }
        else{
            setPosition((float)properties.get("x") + MathUtils.random(-20, 20), 
                        (float)properties.get("y") + MathUtils.random(-40, 40));
        }

        accelerateAtAngle((float)properties.get("angle"));

       
        reRoute();
      
    }

    private boolean testRebounce(){
   
        if(MathUtils.randomBoolean(0.5f)){ //50% in this test //this value will be necessity.

           // System.out.printf("\nRebounce has passed!");
            
            rebounce--;
            return true;

        }
       
       // System.out.printf("\nRebounce has failed!");
        
        return false;
        
    }
    
    private void reRoute(){
        
       ArrayList<Integer> routes = new ArrayList();
       
       if(targetRoute != 0){
           routes.add(0);
       }
       
       if(targetRoute != 2){
           routes.add(2);
       }
       
       if(targetRoute != 4){
           routes.add(4);
       }
       
        targetRoute = routes.get(MathUtils.random(0, routes.size() - 1));
        targetIntersection = MathUtils.random(1, 2);
  
        if(waypoint != null){
            reRouting = true;
        }
        
        //System.out.printf("\ntargetRoute: " + targetRoute);
        //System.out.printf("\ntargetIntersection: " + targetIntersection);
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.

        for(BaseActor actor : BaseActor.getList(getStage(), Waypoint.class)){
            
            Waypoint waypoint2 = (Waypoint)actor;
            
            if(!changeSidewalk){
          
                if(overlaps(waypoint2) 
                        && waypoint2.getWaypointRoute() != 1 
                        && waypoint2.getWaypointRoute() != 3){
                    
                    if(waypoint == null && overlaps(waypoint2)){
                        waypoint = waypoint2;
                    }
                    else if(waypoint.getWaypointRoute() != targetRoute 
                            && waypoint.getWaypointRoute() != 1 
                            && waypoint.getWaypointRoute() != 3){
                        waypoint = waypoint2;
                    }
                    else{
                        
                        if(waypoint2.getWaypointRoute() == targetRoute 
                                && waypoint2.getWaypointIntersection() == targetIntersection){
                            waypoint = waypoint2;
                        }
                        else if(waypoint.getWaypointRoute() != targetRoute 
                                && waypoint2.getWaypointRoute() == targetRoute){
                            waypoint = waypoint2;
                        }
                        
                    }
                    
                }
                
            }
            else{
    
                if(overlaps(actor) 
                        && waypoint2.getWaypointRoute() == 1 
                        || overlaps(actor) 
                        && waypoint2.getWaypointRoute() == 3){
                    
                    reRouting = false;
                    waypoint = waypoint2;
                    if(targetRoute == 0 && waypoint.getWaypointRoute() == 1){
                        
                        changeSidewalk = false;
                        break;
                        
                    }
                    else if(targetRoute == 2 && waypoint.getWaypointRoute() == 1 
                            || targetRoute == 2 && waypoint.getWaypointRoute() == 3 ){

                        changeSidewalk = false;
                        break;
                        
                    }
                    else if(targetRoute == 4 && waypoint.getWaypointRoute() == 3){
                        
                        changeSidewalk = false;
                        break;
                        
                    }
                    
//                    for(BaseActor actor2 : BaseActor.getList(getStage(), Waypoint.class)){
//                        
//                        Waypoint next = (Waypoint)actor2;
//                        
//                        if(next.overlaps(waypoint2)
//                                && overlaps(next)
//                                && next != waypoint2 
//                                && next.getWaypointRoute() == 1 
//                                    || next.getWaypointRoute() == 3){
//                            
//                            if(targetRoute == 0){
//                                
//                                if(next.getWaypointRoute() < waypoint2.getWaypointRoute()){
//                                    waypoint = next;
//                                }
//                                
//                            }
//                            else{
//                                
//                                if(next.getWaypointRoute() > waypoint2.getWaypointRoute()){
//                                    waypoint = next;
//                                }
//                                
//                            }
//                            
//                        }
//                        
//                    } 
                    
                }
                
            }
            
        }
        
        if(waypoint == null){
            accelerateAtAngle(getMotionAngle());
        }
        else{
            
            if(waypoint.getWaypointRoute() == targetRoute){
                
                //System.out.printf("\nSAME ROUTE!");
                
                if(waypoint.getWaypointIntersection() == targetIntersection){
                    
                   // System.out.printf("\nSAME INTERSECTION!");
                    
                    if((getX() <= 2 || getY() <= 2 || getY() >= 622) && rebounce > 0){
                        
                       // System.out.printf("\nTIME TO REROUTE?!");
                        
                        if(testRebounce()){
                            reRoute();
                        }
             
                    }
                    else{
                        
                       // System.out.printf("\nGOING TO THE MAP'S END!");
                        
                        accelerateAtAngle(waypoint.getWaypointRotation3());
                        
//                        if(waypoint.getWaypointIntersection() == 1){
//                            accelerateAtAngle(waypoint.getWaypointRotation());
//                        }
//                        else{
//                            accelerateAtAngle(waypoint.getWaypointRotation2()); //in this place walker will search for lots to fufill its necessity within range until reaches the end of map;
//                        }
                        
                    }
                    
                }
                else{
                    
                  //  System.out.printf("\nCHANGING INTERSECTION!");
                    
                    if(targetIntersection == 1){
                        accelerateAtAngle(waypoint.getWaypointRotation());
                    }
                    else{
                        accelerateAtAngle(waypoint.getWaypointRotation2());
                    }
                    
                }
                
            }
            else if(waypoint.getWaypointRoute() == 1 || waypoint.getWaypointRoute() == 3){
                
              //  System.out.printf("\nCROSSING : " + waypoint.getWaypointRoute());
                
                if(waypoint.getWaypointRoute() > targetRoute){
                    accelerateAtAngle(waypoint.getWaypointRotation());
                }
                else{
                    accelerateAtAngle(waypoint.getWaypointRotation2());
                }
                
            }
            else if(reRouting){
                
                accelerateAtAngle(waypoint.getWaypointRotation3() + 180);
                changeSidewalk = true;
                
            }
            else{
 
                accelerateAtAngle(getMotionAngle());
                changeSidewalk = true;
                
            }
            
        }
    
    }
    
}
