/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.utils.viewport.Viewport;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 *
 * @author Not275
 */
public class BaseActor extends Group {
    
    private Animation<TextureRegion> animation;
    
    private float elapsedTime;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    
    private boolean animationPaused;
    
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    
    private Polygon boundaryPolygon;
    
    private static Rectangle worldBounds;
    
    public BaseActor(Float x, Float y, Stage s){
        
        super();
        setPosition(x,y);
        addActor(s);
        init();
     
    }

    public BaseActor(MapProperties properties, Stage stage){
        
        super();
        setPosition((float)properties.get("x"), (float)properties.get("y"));
        addActor(stage);
        init();
        
    }
    
    private void init(){
        
        animation = null;
        
        elapsedTime = 0;
        acceleration = 0;
        deceleration = 0;
        maxSpeed = 1000;
        
        animationPaused = false;
   
        velocityVec = new Vector2(0,0);
        accelerationVec = new Vector2(0,0);
        
    }
    
    public void setAnimation(Animation<TextureRegion> animation){
        
        this.animation = animation;
        TextureRegion tr = animation.getKeyFrame(0);
        
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        
        setSize(w,h);
        setOrigin(w/2, h/2);
        
        if (boundaryPolygon == null){
            setBoundaryRectangle();
        }
        
    }
    
    public void clearAnimation(){
        animation = null;
    }
    
    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }
    
    //dt = delta time
    @Override
    public void act(float dt){
        
        super.act(dt);
        
        if(!animationPaused){
            elapsedTime += dt;
        }
        
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha){
   
        //super.draw(batch, parentAlpha); //Um dos maiores erros que tive até agora: na hora de renderizar (desenhar) os elementos eram desenhados fora de ordem e ficavam um em cima do outro dificultando a visibilidade).
        
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a); 
        
        if (animation != null && isVisible()){
            batch.draw(animation.getKeyFrame(elapsedTime), 
                    getX(), getY(), 
                    getOriginX(), getOriginY(), 
                    getWidth(), getHeight(), 
                    getScaleX(), getScaleY(), getRotation());
        }
        
        super.draw(batch, parentAlpha);
        
    }
    
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop){
        
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array();
        
        for(int i = 0 ; i < fileCount; i++){

            String fileName = fileNames[i];

            Texture texture;
            texture = BaseGame.util.getInternalTexture(fileName);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            
            textureArray.add(new TextureRegion(texture));

        }
     
        Animation<TextureRegion> frame = new Animation(frameDuration, textureArray);
        
        if(loop){
            frame.setPlayMode(Animation.PlayMode.LOOP);
        }
        else{
            frame.setPlayMode(Animation.PlayMode.NORMAL);
        }
        
        if(animation == null){
            setAnimation(frame);
        }
        
        return frame;
        
    }
    
    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop){
 
        Texture texture = BaseGame.util.getInternalTexture(fileName);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        
        Array<TextureRegion> textureArray = new Array();
        
        for(int r = 0; r < rows; r++){ //r = rows
        
            for(int c = 0; c < cols; c++){ //c = columns
        
                textureArray.add(temp[r][c]);
                
            }
         
        }
        
        Animation<TextureRegion> frame = new Animation(frameDuration, textureArray);
        
        if(loop){
            frame.setPlayMode(Animation.PlayMode.LOOP);
        }
        else{
            frame.setPlayMode(Animation.PlayMode.NORMAL);
        }
        
        if(animation == null){
            setAnimation(frame);
        }
        
        return frame;
        
    }
    
    //one frame image convenience method

    /**
     Method used to load single frame image.
     * @param fileName name of the file to load
     * @return frame of the texture;
     */
    public Animation<TextureRegion> loadTexture(String fileName){
        
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        
        return loadAnimationFromFiles(fileNames, 1 ,true);
        
    }
   
    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }
    
    private void addActor(Stage s){
        s.addActor(this);
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public Vector2 getVelocityVector(){
        return velocityVec;
    }
    
    public void setSpeed(float speed) {
    
    //if length is 0, then assume motion angle is zero degrees
        if(velocityVec.len() == 0){
            velocityVec.set(speed, 0);
        }
        else{
            velocityVec.setLength(speed);
        }
    
    }
    
    public void setMotionAngle(float angle){
        velocityVec.setAngle(angle);
    }
    
    public float getMotionAngle(){
        return velocityVec.angle();
    }
    
    public boolean isMoving(){
        return (getSpeed() > 0);
    }
    
    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }
    
    public void accelerateAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration, 0)).setAngle(angle);
    }
    
    public void turnAtAngle(float angle){
        velocityVec.add(new Vector2(10, 0)).setAngle(angle);
    }
    
    public void accelerateForward(){
        accelerateAtAngle(getRotation());
    }

    public void accelerateBackwards(float rotation){
        accelerationVec.add(new Vector2(acceleration, 0).rotate(getRotation()));
    }
    
    public void setMaxSpeed(float ms){
        maxSpeed = ms;
    }
    
    public void setDeceleration(float deceleration){
        this.deceleration = deceleration;
    }
    
    //dt = delta time;
    public void applyPhysics(float dt){

        //Issue 1: getspeed() before velocity.add (causing speed to be always 0, and turtle stay idle)
        float speed;
        
        // System.out.printf("\n Speed =" + speed);
        //apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);
        
        speed = getSpeed();
        
        //decrease speed (decelerate) when not accelerating
        if(accelerationVec.len() == 0){
            speed -= deceleration * dt;
        }
        
        //keep speed within set bounds //keep values between the max and min value settled.
        speed = MathUtils.clamp(speed, 0, maxSpeed); //(actual value, min value, max value);
        
        //update velocity
        setSpeed(speed);
        
        //apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);
        
        //reset acceleration
        accelerationVec.set(0,0);
        
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getDeceleration() {
        return deceleration;
    }
   
    public void setBoundaryRectangle(){
        
        float width = getWidth();
        float height = getHeight();
        float[] vertices = {0, 0, width, 0, width,height, 0, height};
        boundaryPolygon = new Polygon (vertices);
        
    }
    
    public void setBoundaryPolygon(int numSides){
        
        float width = getWidth();
        float height = getHeight();
        float[] vertices = new float[2*numSides];
        
        for(int i = 0; i < numSides; i++){
            
            float angle = i * 6.28f / numSides;
            
            //x-coordinate
            vertices[2*i] = ((width / 2) * MathUtils.cos(angle) + (width / 2));
            
            //y-coordinate
            vertices[2*i+1] = ((height / 2) * MathUtils.sin(angle) + (height / 2));
            
        }
        
        boundaryPolygon = new Polygon(vertices);
        
    }
    
    public Polygon getBoundaryPolygon(){
        
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        
        return boundaryPolygon;
        
    }
    
    public boolean overlaps(BaseActor other){
        
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        
        //initial test to improve performance
        if( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }
        
        return Intersector.overlapConvexPolygons(poly1, poly2);
        
    }
    
    public void centerAtPosition(float x, float y){
        setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
    }
    
    public void centerAtActor(BaseActor other){
        centerAtPosition(other.getX() + (other.getWidth() / 2), other.getY() + (other.getHeight() / 2));
    }
    
    public void setOpacity(float opacity){
        this.getColor().a = opacity; //a = alpha;
    }
    
    public Vector2 preventOverlap(BaseActor other){
        
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        
        //initial test to improve performance
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return null;
        }
        
        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
        
        if(!polygonOverlap){
            return null;
        }
        
        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        
        return mtv.normal;
 
    }
    
    public static ArrayList<BaseActor> getList(Stage stage, Class className){
        
        ArrayList<BaseActor> list = new ArrayList();
        Class theClass;
        
        try{
            
            theClass = Class.forName(className.getName());
            
            if(stage != null){
                
                for(Actor actor : stage.getActors()){
            
                    if(theClass.isInstance(actor)){
                        list.add((BaseActor) actor);
                    }
            
                }
                
            }
            
        }
        catch(ClassNotFoundException error){
            //class local {}; Actor.class.getSimpleName().concat("/" + local.class.getEnclosingMethod().getName())
            BaseGame.util.showErrorMessage(error);
        }
        
        return list;
        
    }
    
    public static void removeAllActors(Stage stage, Class className){
        
        ArrayList<BaseActor> list;
        list = getList(stage, className);
        
        for(BaseActor actor : list){
            actor.remove();
        }
        
    }
    
    public static int countActors(Stage stage, Class className){
        return getList(stage,className).size();
    }
    
    public static void setWorldBounds(float width, float height ){
        worldBounds = new Rectangle(0, 0, width, height);
    }
    
    public static void setWorldBounds(BaseActor actor){
        setWorldBounds(actor.getWidth(), actor.getHeight());
    }
    
    public static Rectangle getWorldBounds(){
        return worldBounds;
    }
    
    public void boundToWorld(){
        
        //check left edge
        if(getX() < 0){
            setX(0);
        }
        
        //check right edge
        if(getX() + getWidth() > worldBounds.width){
            setX(worldBounds.width - getWidth());
        }
        
        //check bottom edge
        if(getY() < 0){
            setY(0);
        }
        
        //check top edge
        if(getY() + getHeight() > worldBounds.height){
            setY(worldBounds.height - getHeight());
        }
        
    }
    
    public void alignCamera(){
        
        Camera camera = this.getStage().getCamera();
        Viewport viewport = this.getStage().getViewport();
        
        // center camera on actor
        camera.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0);
        
        // bound camera to layout
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, worldBounds.width - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, worldBounds.height - camera.viewportHeight / 2);
        camera.update();
        
    }
    
    public void wrapAroundWorld(){ //this method changes behavior of boundToWorld (actor teleports from one side of the screen to another instead stop at the border of the screen
        
        if(getX() + getWidth() < 0){
            setX(worldBounds.width);
        }
        
        if(getX() > worldBounds.width){
            setX(-getWidth());
        }
        
        if(getY() + getHeight() < 0){
            setY(worldBounds.height);
        }
        
        if(getY() > worldBounds.height){
            setY(-getHeight());
        }
        
    }
    
    public boolean isWithinDistance(float distance, BaseActor other){
        
        Polygon polygon = this.getBoundaryPolygon();
        float scaleX = ((this.getWidth() + 2) * distance) / this.getWidth();
        float scaleY = ((this.getHeight() + 2) * distance) / this.getHeight();
        polygon.setScale(scaleX, scaleY);
        
        Polygon polygon2 = other.getBoundaryPolygon();
        
        //initial test to improve performance
        if(!polygon.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())){
            return false;
        }
        
        return Intersector.overlapConvexPolygons(polygon, polygon2);
        
    }
    
    public void setColor(String color){
        
        if(color.equalsIgnoreCase("Red")){
            setColor(Color.RED);
        }
        else if(color.equalsIgnoreCase("Blue")){
            setColor(Color.BLUE);
        }
        else if(color.equalsIgnoreCase("Orange")){
            setColor(Color.ORANGE);
        }
        else if(color.equalsIgnoreCase("Purple")){
            setColor(Color.PURPLE);
        }
        else if(color.equalsIgnoreCase("Green")){
            setColor(Color.GREEN);
        }
        else if(color.equalsIgnoreCase("Yellow")){
            setColor(Color.YELLOW);
        }
        else if(color.equalsIgnoreCase("Gray")){
            setColor(Color.GRAY);
        }
        else if(color.equalsIgnoreCase("White")){
            setColor(Color.WHITE);
        }
        else{
            setColor(Color.CLEAR);
        }
        
    }

    public Vector2 getCarVelocity(float speed, float rotation){
        
        Vector2 velocity = new Vector2();
        float vx = (float) Math.cos(Math.toRadians(rotation)) * speed;
        float vy = (float) Math.sin(Math.toRadians(rotation)) * speed;
        
        velocity.x = vx;
        velocity.y = vy;
        
        return velocity;
        
    }
    
    public Vector2 getVelocityVec() {
        return velocityVec;
    }

    public void setVelocityVec(Vector2 velocityVec) {
        this.velocityVec = velocityVec;
    }

    public Vector2 getAccelerationVec() {
        return accelerationVec;
    }

    public void setAccelerationVec(Vector2 accelerationVec) {
        this.accelerationVec = accelerationVec;
    }

    public float getAcceleration() {
        return acceleration;
    }
    
}
