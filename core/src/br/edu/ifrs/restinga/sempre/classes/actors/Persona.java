/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product.Type_Product;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseStage;

import br.edu.ifrs.restinga.sempre.classes.effects.Barrier;
import br.edu.ifrs.restinga.sempre.classes.entities.Crossing;
import br.edu.ifrs.restinga.sempre.classes.entities.Entity;
import br.edu.ifrs.restinga.sempre.classes.effects.Icon;
import br.edu.ifrs.restinga.sempre.classes.effects.Icon.Type;
import br.edu.ifrs.restinga.sempre.classes.entities.Light_Pole;
import br.edu.ifrs.restinga.sempre.classes.entities.Road;
import br.edu.ifrs.restinga.sempre.classes.effects.Selection_Effect;
import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import br.edu.ifrs.restinga.sempre.classes.entities.Traffic_Light;

import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.utils.Array;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Not275
 */
public class Persona extends BaseActor {

    private String id; //unique identification of the persona;
    private TextureRegion profileTexture;
    private Icon icon = null;
    
    private Animation south;
    private Animation north;
    private Animation west;
    private Animation east;
    
    private Button button;
    
    private float facingAngle;
    private boolean running = false;
    //private boolean player = false;
    //private boolean bot = false;
    private boolean employee = true;
    private boolean inside = false; //inside lot;
    
    private boolean selected = false;
    private Selection_Effect selectionEffect = null;
    protected Selection_Effect playerContrast = null;
    
    //Characteristics
    private String personaName = null; //max 17 chars //testing name;
    private int money = 10000;
    private int salary = 0; //planned salary (minimium);
    
    private int age = 0;
    private Product necessity = null;
    private Lot currentLot = null; //current Lot of the persona;
    
    private float perception; //overall perpection of the walker from the service; Change of the time during the service;
    private float necessityLevel = 0;
    private float patience = 0;
    private float productivity = 0;
    private float motivation = 0; //used to determine employee autofire or strike chance; Decreases over time with determined events;
    
    private final CopyOnWriteArrayList<Lot> lots = new CopyOnWriteArrayList();
    
    protected String[] names = {"Alice","Miguel","Sophia",
                                "Arthur","Helena","Bernardo",
                                "Valentina","Heitor","Laura",
                                "Davi","Isabella","Lorenzo",
                                "Manuela","Théo","Júlia",
                                "Pedro","Heloísa","Gabriel",
                                "Luiza","Enzo","Maria",
                                "Matheus","Lorena","Lucas"};
                              
    protected String[] surnames = {" Fernandes"," Santana"," Carvalho",
                                   " Martins"," Santos"," Boaventura",
                                   " Oliveira"," Moraes"," Leão",
                                   " Garcia"," Sampaio"," Menezes",
                                   " Campos"," Ribeiro"," Xavier",
                                   " Pinheiro"," Moreira"," Medeiros"};
    
    protected String[] charsF = {"female_1.png", "female_10.png", "female_2.png", 
                                 "female_3.png", "female_4.png", "female_5.png", 
                                 "female_6.png", "female_7.png", "female_8.png", 
                                 "female_9.png"};
            
    protected String[] charsM = {"male_3.png", "male_4.png", "male_5.png",
                                 "male_6.png", "male_8.png", "male_7.png"};
    
    public Persona(Float x, Float y, BaseStage stage) {
        
        super(x, y, stage);

        initPersona(null, null);
        
    }
    
    public Persona(MapProperties properties, BaseStage stage) {
        
        super(properties, stage);
 
        initPersona(null, null);
        
    }
 
     public Persona(MapProperties properties, BaseStage stage, String texture, Color color) {
        
        super(properties, stage);
 
        initPersona(texture, color);
        
    }
    
    private void initPersona(String textureName, Color color){
     
        id = UUID.randomUUID().toString();
        
        motivation = 1;

        int index = 0;
    
        if(this instanceof Walker){
            employee = MathUtils.randomBoolean(0.4f);
        }

        index = MathUtils.random(0, names.length - 1);
        setPersonaName(names[index].concat(surnames[MathUtils.random(0, surnames.length - 1)]));

        age = MathUtils.random(18, 65);

        perception = MathUtils.random(0.2f, 1.0f); //overall first impression, are generated when walker completes a trade;

        necessityLevel = MathUtils.random(0.2f, 1.0f); //more necessity are higher probability to accept lower quality and higher prices;

        patience = MathUtils.random(0.2f + (age / 100), 1.0f); //less patience are more likety to give up when in lot (if too long).

        productivity = MathUtils.random(0.2f + (100 / (age * 10)), 1.0f); //productivity used to employee, define time to attend a consumer (walker). productivity < patience are likely to generate giveups.

        salary = ((int)MathUtils.random(100, 500)) + ((int)(productivity * 1000)) + ((int)motivation * 200);

        necessity = new Product(Type_Product.values()[MathUtils.random(1, Type_Product.values().length - 1)], MathUtils.random(1, 35));
        necessity.setQuantity(MathUtils.random(5, 40));

        setMoney(necessity.getPrice() * necessity.getQuantity());
        
        int rows = 4;
        int cols = 4;

        if(textureName == null){
 
            if(index % 2 == 0){ //isFemale;
                textureName = charsF[MathUtils.random(0, charsF.length - 1)];
            }
            else{
                textureName = charsM[MathUtils.random(0, charsM.length - 1)];
            }
             
        }
        
        Texture texture = new Texture(BaseGame.util.getInternalFileHandle("characters", textureName), true);
        
        initActor(rows, cols, texture);               
       
        if(color == null){
            
            Color[] colors = {Color.WHITE, Color.BROWN, Color.TAN, Color.YELLOW};
            setColor(colors[MathUtils.random(0, colors.length - 1)]);
            
        }
        else{
            setColor(color);
        }
        
        setAnimation(south);
        facingAngle = 270;

        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(MathUtils.random(90, 100)); //default 100
        setDeceleration(400);
        
    }
   
    @Override
    public BaseStage getStage(){
        return (BaseStage)super.getStage();
    }
    
    public float getSatisfactionLevel(){
        return perception - necessityLevel;
    }
    
    public String getId(){
        return id;
    }
    
    public void setPersonaName(String name){
        
        if(name.trim().length() - 1 > 25){
            name = name.trim().substring(0, 25);
        }
        
        personaName = name.trim().toUpperCase();
        
    }
    
    public boolean isEmployee(){
        return employee;
    }

    public void setInside(boolean inside){
        this.inside = inside;
    }
    
    public boolean isInsideLot() {
        return inside;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    public Product getNecessity() {
        return necessity;
    }
    
    public void setNecessity(Product product){
        necessity = product;
    }
    
    public float getPerception() {
        return perception;
    }
    
    public void setPerception(float perception) {
        this.perception = perception;
    }

    public float getNecessityLevel() {
        return necessityLevel;
    }
    
    public void setNecessityLevel(float necessityLevel) {
        this.necessityLevel = necessityLevel;
    }

    public float getPatience() {
        return patience;
    }
    
    public void setPatience(float patience){
        this.patience = patience;
    }
    
    public float getProductivity() {
        return productivity;
    }
    
    public void setProductivity(float productivity){
        this.productivity = productivity;
    }
    
    public int getMoney(){
        return money;
    }
    
    public void setMoney(int value){
        money = value;
    }
    
    public void subMoney(int value){
        money -= value;
    }
    
    public void addMoney(int value){
        money += value;
    }
    
    public String getMoneyValue(){
        return String.valueOf(money);
    }
    
    public String getPersonaName(){
        return personaName;
    }
    
    public String getPersonaNameLimit(int limit){
        
        if(personaName.length() - 1 > limit){
            return personaName.substring(0, limit);
        }
        
        return getPersonaName();
        
    }
    
    public boolean isMoneyEnough(int value){
        return money >= value;
    }
    
    public boolean isSelected(){
        return selected;
    }

    public Lot getCurrentLot() {
        return currentLot;
    }

    public void setCurrentLot(Lot currentLot) {
        this.currentLot = currentLot;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public float getMotivation() {
        return motivation;
    }

    public void setMoral(float motivation) {
        this.motivation = motivation;
    }
    
    public TextureRegion getProfileTexture(){
        return profileTexture;
    }
    
    public CopyOnWriteArrayList<Lot> getLots() {
        return lots;
    }
  
    public int getEmployees(){
        
        int value = 0;
        
        for(Lot lot : lots){
            value += lot.getEmployees().size();
        }
        
        return value;
        
    }
    
    public void select(Button button){
       
        this.button = button;
        
        selected = true;
        
        if(selectionEffect == null){
            
            selectionEffect = new Selection_Effect(0f, 0f, getStage());
            selectionEffect.setZIndex(1);        
            
        }
        
    }
    
    public void deselect(){
        
        if(button != null){
            
            Button btn = button;
            button = null;
            
            InputEvent evt = new InputEvent();
            evt.setType(InputEvent.Type.touchDown);
            
            btn.fire(evt);
            
        }
        
        if(isSelected()){
            
            if(selectionEffect != null){
                
                selectionEffect.remove();
                selectionEffect = null;
                
            }
            
        }
        
        selected = false;
        
    }
    
    private void initActor(int rows, int cols, Texture texture){
    
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        float frameDuration  = 0.2f;
        
        TextureRegion[][] textureRegion
                = TextureRegion.split(texture, frameWidth, frameHeight);
        
        Array<TextureRegion> textureArray = new Array();
 
        for(int row = 0; row < rows; row++){
        
            for(int col = 0; col < cols; col++){
                textureArray.add(textureRegion[row][col]);
            }
 
            if(row == 0){
                south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(row == 1){
                west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(row == 2){
                east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }
            else{
                north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            }

            textureArray.clear();
            
        }
        
        profileTexture = textureRegion[0][0];
        
    }
    
    public float getFacingAngle(){
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }

    public Animation getSouth() {
        return south;
    }

    public void setSouth(Animation south) {
        this.south = south;
    }

    public Animation getNorth() {
        return north;
    }

    public void setNorth(Animation north) {
        this.north = north;
    }

    public Animation getWest() {
        return west;
    }

    public void setWest(Animation west) {
        this.west = west;
    }

    public Animation getEast() {
        return east;
    }
    
    public void setEast(Animation east) {
        this.east = east;
    }
    
    private void moveTo(Crossing crossing){
        
        if(crossing.getOrientation().equals("South")){
           
            if(facingAngle == 180){
                accelerateAtAngle(180);
            }
            else if(facingAngle == 0){
                accelerateAtAngle(0);
            }
            else{
              
                if(crossing.getX() + crossing.getWidth() / 2 >= getX()){
                    accelerateAtAngle(180);
                }
                else{
                    accelerateAtAngle(0);
                }
                
            }
            
        }
        else{
       
            if(facingAngle == 90){
                accelerateAtAngle(90);
            }
            else if(facingAngle == 270){
                accelerateAtAngle(270);
            }
          
            else{
                
                if(crossing.getY() + crossing.getHeight() / 2 <= getY()){
                    accelerateAtAngle(90);
                }
                else{
                    accelerateAtAngle(270);
                }
                
            }
            
        }
        
    }
    
    public void getIcon(Type iconValue){
        
        if(icon != null){
            removeActor(icon);
        }
        
        icon = new Icon(0f, 0f, getStage(), iconValue);
       
        addActor(icon);
        icon.setPosition(icon.getX() + 5, icon.getY() + getHeight());
        icon.toFront();
       
    }
    
    public void exitLot(){
   
        String orientation = getCurrentLot().getOrientation();
        int rotation;
        
        if(orientation.equals("North")){
            rotation = 270;
        }
        else if(orientation.equals("South")){
            rotation = 90;
        }
        else if(orientation.equals("West")){
            rotation = 0;
        }
        else{
            rotation = 180;
        }
        
        this.setMotionAngle(rotation);
        
        addAction(Actions.sequence(Actions.fadeIn(0.2f)));
        
        getCurrentLot().getPopulation().remove(this);
        
        setCurrentLot(null);

        setInside(false);
        
    }
    
    public void enterLot(Lot lot){
        
        if(lot.getType().equals(Type_Lot.LOT_BUILDING)){
            return;
        }
        
        this.deselect();
        setInside(true);
        lot.getPopulation().add(this);
        setCurrentLot(lot);

        this.getAccelerationVec().setZero();
        addAction(Actions.sequence(Actions.fadeOut(0.2f)));
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt);
        boolean overlaping = false;
        
        if(!isInsideLot()){
        
            if(selectionEffect != null){
                selectionEffect.setPosition(getX() - 8, getY() - 15);
            }
            
            if(playerContrast != null){
                playerContrast.setPosition(getX() - 8, getY() - 15);
            }

            if(getSpeed() == 0){
                setAnimationPaused(true);
            }
            else{

                setAnimationPaused(false);

                //set direction animation
                float angle = getMotionAngle();
                if(angle >= 45 && angle <= 135){

                    facingAngle = 90;
                    setAnimation(north);

                }
                else if(angle >= 135 && angle < 225){

                    facingAngle = 180;
                    setAnimation(west);

                }
                else if(angle >= 225 && angle <= 315){

                    facingAngle = 270;
                    setAnimation(south);

                }
                else{

                    facingAngle = 0;
                    setAnimation(east);

                }

            }

            for(BaseActor actor : BaseActor.getList(getStage(), Solid.class)){

                if(actor instanceof Crossing){

                    Crossing crossing = (Crossing) actor;

                    if(!crossing.isWalkerEnabled() && overlaps(crossing)){

                        if(!overlaps(crossing.getSolid()) && !running){

                            if(!(this instanceof Player)){
                                getAccelerationVec().setZero(); 
                            }
                            else{
                                preventOverlap(actor);
                            }

                        }
                        else{

                            setSpeed(getMaxSpeed());
                            moveTo(crossing);
                            running = true;

                        }

                    }

                    if(overlaps(crossing)){
                        overlaping = true;
                    }

                }
                else if(actor instanceof Barrier
                        || actor instanceof Lot && !(this instanceof Player)
                        || actor instanceof Road){
                    preventOverlap(actor);
                }

            }

            if(!overlaping){
                running = false;
            }

            for(BaseActor actor : BaseActor.getList(getStage(), Entity.class)){

                if(actor instanceof  Traffic_Light 
                        || actor instanceof Light_Pole){

                    if(isWithinDistance(0.5f, ((Entity)actor).getSolid())){
                        toFront();
                    }
                    else{
                        actor.toFront();
                    }

                }

            }

            for(BaseActor actor : BaseActor.getList(getStage(), Car.class)){

                if(isWithinDistance(1.0f, actor)){
                    toFront();
                }

            }

        }
        
        if(!(this instanceof Walker)){
            boundToWorld();
        }

        applyPhysics(dt);
        
    }
    
}
