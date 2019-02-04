/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.screens;

import br.edu.ifrs.restinga.sempre.classes.actors.Bot;
import br.edu.ifrs.restinga.sempre.classes.actors.Car;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Persona;
import br.edu.ifrs.restinga.sempre.classes.actors.Player;
import br.edu.ifrs.restinga.sempre.classes.actors.Walker;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.TilemapActor;

import br.edu.ifrs.restinga.sempre.classes.components.CustomTextureButtonStyle;
import br.edu.ifrs.restinga.sempre.classes.components.CustomNavigationDialog;

import br.edu.ifrs.restinga.sempre.classes.entities.Spawn_Lot;
import br.edu.ifrs.restinga.sempre.classes.entities.Car_Waitpoint;
import br.edu.ifrs.restinga.sempre.classes.entities.Crossing;
import br.edu.ifrs.restinga.sempre.classes.entities.Despawn_Car;
import br.edu.ifrs.restinga.sempre.classes.entities.Despawn_Walker;
import br.edu.ifrs.restinga.sempre.classes.entities.Light_Pole;
import br.edu.ifrs.restinga.sempre.classes.entities.Road;
import br.edu.ifrs.restinga.sempre.classes.entities.Sidewalk;
import br.edu.ifrs.restinga.sempre.classes.entities.Spawn_Walker;
import br.edu.ifrs.restinga.sempre.classes.entities.Traffic_Light;
import br.edu.ifrs.restinga.sempre.classes.entities.Walker_Waitpoint;
import br.edu.ifrs.restinga.sempre.classes.entities.Waypoint;

import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;

import br.edu.ifrs.restinga.sempre.classes.effects.Barrier;
import br.edu.ifrs.restinga.sempre.classes.entities.Lot_Waypoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.utils.Align;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

//    long lastTimeCounted = TimeUtils.millis();
//    private float sinceChange = 0;
//    private float frameRate = Gdx.graphics.getFramesPerSecond();
    
    private Player player;
    
    private Persona enemy;
    private Persona enemy2;
    private Persona enemy3;

    private float sync_dt = 0; //this variable synchronizes timed events (e.g. car spawns) when game is paused.
    
    private int visualMoney;
    
    private float carSpawnInterval = 5;
    private float carTimer = 0;
    
    private float walkerSpawnInterval = 3f;
    private float walkerTimer = 0;
    
    private float gameTime = 0;
    
    private Boolean decision = null;
    
    private TilemapActor tiledMapper;
    
    //ui elements
    private DialogBox topBar;
    
    private CustomNavigationDialog customNavigationDialog;
   
    private BaseActor moneyIcon;
    private BaseActor lotsIcon;
    private BaseActor employeesIcon;
    private BaseActor timerIcon;
    
    private Label moneyLabel;
    private Label lotsLabel;
    private Label employeesLabel;
    private Label timerLabel;
    
    @Override
    public void initActors() {
        
        tiledMapper = new TilemapActor("map.tmx", mainStage);
        
        MapProperties properties = tiledMapper.getRectangleList("Start").get(0).getProperties();
        player = new Player(properties, mainStage, "male_2.png", "Éverton Quadros do Couto", 30, Color.WHITE);

        enemy = new Bot(properties, mainStage, this);
        enemy.setX(enemy.getX() + 50);
//        
//        enemy2 = new Bot(properties, mainStage, this);
//        enemy2.setX(enemy2.getX() + 60);
//        
//        enemy3 = new Bot(properties, mainStage, this);
//        enemy3.setX(enemy2.getX() + 80);
       
        visualMoney = player.getMoney();
        
        tiledMapper.mappingRectangleObjects(Barrier.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Light_Pole.class, mainStage);

        tiledMapper.mappingRectangleObjects(Road.class, mainStage);

        tiledMapper.mappingRectangleObjects(Sidewalk.class, mainStage);

        tiledMapper.mappingRectangleObjects(Crossing.class, mainStage);

        tiledMapper.mappingRectangleObjects(Car_Waitpoint.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Walker_Waitpoint.class, mainStage);

        tiledMapper.mappingRectangleObjects(Despawn_Car.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Spawn_Lot.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Spawn_Walker.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Despawn_Walker.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Waypoint.class, mainStage);
        
        tiledMapper.mappingRectangleObjects(Lot_Waypoint.class, mainStage);
        
        for(MapObject map : tiledMapper.getRectangleList("Spawn_Lot")){
            
            Lot lot = new Lot(map.getProperties(), mainStage);
            
            if(lot.getId() == BaseGame.cheapLot){
                lot.buy(enemy, 0);
            }
            
        }
       
        Color color[] = {Color.RED, Color.GREEN};
       
        int i = 0;

        for(MapObject map : tiledMapper.getRectangleList("Traffic_Light")){

            MapProperties property = map.getProperties();
            String texture = "";
            String face = (String)property.get("face");

            if(face.equals("South")){
                texture = "traffic_light_south.png";
            }
            else if(face.equals("West")){
                texture = "traffic_light_west.png";
            }

            new Traffic_Light(property, mainStage, 
                    (String)property.get("face"),
                    texture,
                    color[i++]);
           
        }
        
//          player.getIcon(Icon.Type.SMILEY_VERY_HAPPY);
//        Lot lot2 = (Lot)BaseActor.getList(mainStage, Lot.class).get(5);
//        lot2.addStatusIcon(Icon_Lot.Type.ICON_SELL, Icon_Lot.Duration.FOREVER);
//        lot2.addEventIcon(Icon_Lot.Type.ICON_PARTNERSHIP, Icon_Lot.Duration.FOREVER);
       
    }

    @Override
    public void initComponents() { 

        customNavigationDialog = new CustomNavigationDialog(0f, 0f, uiStage, this);
        topBar = new DialogBox(0, 0, uiStage, "dialog-translucent.png");
        topBar.setDialogSize(800, 48);
        topBar.setBackgroundColor(Color.BLACK);
        topBar.setVisible(true);
        
        Table dialogTable = new Table();
        dialogTable.setFillParent(true);
   
        Label name = new Label(player.getPersonaNameLimit(17), BaseGame.labelStyleFreeFont);
       
        moneyIcon = new BaseActor(0f, 0f, uiStage);
        moneyIcon.loadTexture("icons".concat(System.getProperty("file.separator"))
                                     .concat("icon_money.png"));
      
        lotsIcon = new BaseActor(0f, 0f, uiStage);
        lotsIcon.loadTexture("icons".concat(System.getProperty("file.separator"))
                                    .concat("icon_lots.png"));
        
        employeesIcon = new BaseActor(0f, 0f, uiStage);
        employeesIcon.loadTexture("icons".concat(System.getProperty("file.separator"))
                                    .concat("icon_employees.png"));
        
        moneyLabel = new Label("$".concat(String.valueOf(visualMoney)), BaseGame.labelStyleFreeFont);
        moneyLabel.setColor(Color.WHITE);
        
        lotsLabel = new Label("0", BaseGame.labelStyleFreeFont);
        lotsLabel.setColor(Color.WHITE);
       
        employeesLabel = new Label("0", BaseGame.labelStyleFreeFont);
        employeesLabel.setColor(Color.WHITE); 
        
        timerIcon = new BaseActor(0f, 0f, uiStage);
        timerIcon.loadTexture("icons".concat(System.getProperty("file.separator"))
                                    .concat("icon_timer.png"));
        
        timerLabel = new Label(BaseGame.util.secondsToString(0), BaseGame.labelStyleFreeFont);
        timerLabel.setColor(Color.WHITE);
        
        Button playerIcon = new Button(new CustomTextureButtonStyle(player.getProfileTexture()));
        
        dialogTable.add(playerIcon).width(30).height(30);
        dialogTable.add(name).padRight(30);
        dialogTable.add(lotsIcon); 
        dialogTable.add(lotsLabel).padRight(30);
        dialogTable.add(employeesIcon);
        dialogTable.add(employeesLabel).padRight(30);
        dialogTable.add(timerIcon);
        dialogTable.add(timerLabel).padRight(30);
        dialogTable.add(moneyIcon);
        dialogTable.add(moneyLabel);
        dialogTable.align(Align.left);
 
        topBar.addActor(dialogTable);
        
        uiTable.add(topBar);
        uiTable.row().expandX().expandY();
        uiTable.add();
    
//        customNavigationDialog.getModal().setText("teste 12345");
//        customNavigationDialog.getModal().setImageTexture("Sidewalk_dark.png", Color.WHITE);
//        customNavigationDialog.getModal().show();
    
        //modal.setText("oi oi oi oi ");
        //modal.setImageTexture("StreetCurve.png", Color.WHITE);
        
        
    }

    @Override
    public void initSounds() { }

    @Override
    public void initScenes() { }

    public Player getPlayer() {
        return player;
    }
    
    public CustomNavigationDialog getNavigationDialog(){
        return customNavigationDialog;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(customNavigationDialog.isShowing()
                || customNavigationDialog.getModal().isShowing()){
            return false;
        }
        
        if(Gdx.input.isTouched(Input.Buttons.LEFT)){
        
            Vector3 mousePosition = tiledMapper.getMousePosInGameWorld();
            
            BaseActor placement = new BaseActor((float)mousePosition.x, 
                                                (float)mousePosition.y, mainStage);
            placement.loadTexture("white.png");
            placement.setVisible(false);
   
            for(BaseActor actor : BaseActor.getList(mainStage, BaseActor.class)){

                if(actor instanceof Lot || actor instanceof Persona){

                    if(placement.overlaps(actor)){

                        if(actor instanceof Lot){
                        
                            if(((Lot) actor).getType().equals(Type_Lot.LOT_BUILDING)){
                                break;
                            }
                        
                        }
     
                        customNavigationDialog.setActor(actor);

                        break;

                    }

                }
     
            }
           
            placement.remove();
            
        }
       
        return false;

    }
    
    @Override
    public void update(float dt) { 
 
        if(mainStage.isPaused()){
            
            dt = sync_dt;
            return;
            
        }
        
        sync_dt = dt;
 
        if(getPlayer().getValue() != 0){
         
            if(getPlayer().getValue() % 2 != 0){

                if(getPlayer().getValue() > 0){

                    visualMoney++;
                    getPlayer().subValue(1);

                }
                else{

                    visualMoney--;
                    getPlayer().addValue(1);

                }

            }

            visualMoney += getPlayer().getValue() / 2;

            getPlayer().setValue(getPlayer().getValue() / 2);

        }
        
        if(player.isInsideLot()){

            if(player.getCurrentLot() != null && customNavigationDialog.getActor() != player.getCurrentLot()){
                
                customNavigationDialog.disableDialog();
                customNavigationDialog.setActor(player.getCurrentLot());
                
            }
            else if(player.getCurrentLot() != null && customNavigationDialog.getActor() == player.getCurrentLot() && customNavigationDialog.isLimited()){
                
                customNavigationDialog.disableDialog();
                customNavigationDialog.setActor(player.getCurrentLot());
                
            }
            
            if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.getCurrentLot().getOrientation().equals("South") ||
                Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.getCurrentLot().getOrientation().equals("North") ||
                    Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getCurrentLot().getOrientation().equals("West") ||
                        Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getCurrentLot().getOrientation().equals("East")){
                
                player.exitLot();
                
                if(customNavigationDialog.isShowing()){
                    customNavigationDialog.disableDialog();
                }
                
            }
            
        }
        else{
            
            if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                player.accelerateAtAngle(45);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                player.accelerateAtAngle(135);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                player.accelerateAtAngle(90);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                player.accelerateAtAngle(225);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                player.accelerateAtAngle(315);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                player.accelerateAtAngle(270); 
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                player.accelerateAtAngle(180);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                player.accelerateAtAngle(0);
            }
        
        }
        
        gameTime += dt;
        carTimer += dt;
   
        if(carTimer > carSpawnInterval){
           
            carTimer = 0;
            carSpawnInterval = MathUtils.random(2, 10);

            MapProperties properties = tiledMapper.getRectangleList("Spawn_Car").get(MathUtils.random(0, 3)).getProperties();
            new Car(properties, mainStage);
           
        }
        
        walkerTimer += dt;
        
        if(walkerTimer > walkerSpawnInterval){
         
            walkerTimer = 0;
            
            MapProperties properties = tiledMapper.getRectangleList("Spawn_Walker").get(MathUtils.random(0, 5)).getProperties();
            new Walker(properties, mainStage);
            
        }

        moneyLabel.setText("$".concat(String.valueOf(visualMoney))); 
        timerLabel.setText(BaseGame.util.secondsToString((int)gameTime));
        employeesLabel.setText(String.valueOf(player.getEmployees()));
        lotsLabel.setText(String.valueOf(player.getLots().size()));
        
//        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
//        lastTimeCounted = TimeUtils.millis();
//
//        sinceChange += delta;
//        
//        if(sinceChange >= 1000) {
//            sinceChange = 0;
//            frameRate = Gdx.graphics.getFramesPerSecond();
//        }
//        
//        System.out.printf("\n--->" + frameRate);
        
    }

}
