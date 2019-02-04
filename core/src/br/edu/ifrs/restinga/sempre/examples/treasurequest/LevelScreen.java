/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples.treasurequest;

import br.edu.ifrs.restinga.sempre.classes.entities.Solid;
import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.classes.base.BaseScreen;
import br.edu.ifrs.restinga.sempre.classes.base.TilemapActor;
import br.edu.ifrs.restinga.sempre.classes.components.DialogBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author Not275
 */
public class LevelScreen extends BaseScreen{

    private Hero hero;
    private Sword sword;
    private Treasure treasure;
    private ShopHeart shopHeart;
    private ShopArrow shopArrow;
    
    private int health;
    private int coins;
    private int arrows;
    
    private boolean gameOver;
    
    private BaseActor healthIcon;
    private BaseActor coinIcon;
    private BaseActor arrowIcon;
    
    private Label healthLabel;
    private Label coinLabel;
    private Label arrowLabel;
    private Label messageLabel;
    private DialogBox dialogBox;
    
    @Override
    public void initActors() {
        
        healthIcon = new BaseActor(0f, 0f, uiStage);
        healthIcon.loadTexture("heart-icon.png");
        
        coinIcon = new BaseActor(0f, 0f, uiStage);
        coinIcon.loadTexture("coin-icon.png");
        
        arrowIcon = new BaseActor(0f, 0f, uiStage);
        arrowIcon.loadTexture("arrow-icon.png");
        
        health = 3;
        coins = 50;
        arrows = 3;
        gameOver = false;
        
        TilemapActor tilemapActor = new TilemapActor("customap.tmx", mainStage);
     
        tilemapActor.mappingRectangleObjects(Solid.class, mainStage);
        
        tilemapActor.mappingTileObjects(Bush.class, mainStage);
        
        tilemapActor.mappingTileObjects(Rock.class, mainStage);
        
        tilemapActor.mappingTileObjects(Flyer.class, mainStage);
        
        tilemapActor.mappingTileObjects(NPC.class, mainStage);
        
        tilemapActor.mappingTileObjects(ShopHeart.class, mainStage);

        tilemapActor.mappingTileObjects(ShopArrow.class, mainStage);
        
        shopHeart = (ShopHeart)BaseActor.getList(mainStage, ShopHeart.class).get(0);
        
        shopArrow = (ShopArrow)BaseActor.getList(mainStage, ShopArrow.class).get(0);
        
        MapProperties properties = tilemapActor.getTileList("Treasure").get(0).getProperties();
        treasure = new Treasure(properties, mainStage);
        treasure.setScale(2);
        
        tilemapActor.mappingTileObjects(Coin.class, mainStage);
        
        properties 
                = tilemapActor.getRectangleList("Start").get(0).getProperties();
        
        hero = new Hero(properties, mainStage);
  
        sword = new Sword(0f, 0f, mainStage);
        sword.setVisible(false);
        
    }

    @Override
    public void initComponents() {
    
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.TAN;
    
        BaseGame.labelStyleFreeFont.setParameters(parameter);
        
        healthLabel = new Label(" x " + health, BaseGame.labelStyleFreeFont);
        healthLabel.setColor(Color.PINK);
        
        coinLabel = new Label(" x " + coins, BaseGame.labelStyleFreeFont);
        coinLabel.setColor(Color.GOLD);
        
        arrowLabel = new Label(" x " + arrows, BaseGame.labelStyleFreeFont);
        arrowLabel.setColor(Color.TAN);
        
        messageLabel = new Label("...", BaseGame.labelStyleFreeFont);
        messageLabel.setVisible(false);
        
        dialogBox = new DialogBox(0f, 0f, uiStage, "dialog.png");
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);
        
        uiTable.pad(20);
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(arrowIcon);
        uiTable.add(arrowLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(8).expandX().expandY();
        uiTable.row();
        uiTable.add(dialogBox).colspan(8);
        
    }

    @Override
    public void initSounds() {}

    @Override
    public void initScenes() {}

    public void swingSword(){
        
        if(!sword.isVisible()){
            
            hero.setSpeed(0);
            
            float facingAngle = hero.getFacingAngle();
            
            Vector2 offset = new Vector2();
            
            if(facingAngle == 0){
                offset.set(0.50f, 0.20f);
            }
            else if(facingAngle == 90){
                offset.set(0.65f, 0.50f);
            }
            else if(facingAngle == 180){
                offset.set(0.40f, 0.20f);
            }
            else{ //facingAngle == 270;
                offset.set(0.25f, 0.20f);
            }
            
            sword.setPosition(hero.getX(), hero.getY());
            sword.moveBy(offset.x * hero.getWidth(), offset.y * hero.getHeight());
            
            float swordArc = 90;
            sword.setRotation(facingAngle - swordArc / 2);
            sword.setOriginX(0);
            
            sword.setVisible(true);
            sword.addAction(Actions.rotateBy(swordArc, 0.25f));
            sword.addAction(Actions.after(Actions.visible(false)));
 
            //hero should appear in front of sword when facing north or west;
            if(facingAngle == 90 || facingAngle == 180){
                hero.toFront();
            }
            else{
                sword.toFront();
            }
            
        }
        
    }
    
    public void shootArrow(){
        
        if(arrows > 0){
            
            arrows--;
            
            Arrow arrow = new Arrow(0, 0, mainStage);
            arrow.centerAtActor(hero);
            arrow.setRotation(hero.getFacingAngle());
            arrow.setMotionAngle(hero.getFacingAngle());
            
        }
        
    }
    
    public void kill(Flyer flyer){
        
        flyer.remove();
        Coin coin = new Coin(0, 0, mainStage);
        coin.centerAtActor(flyer);
        Smoke smoke = new Smoke(0, 0, mainStage);
        smoke.centerAtActor(flyer);
        
    }
    
 
    @Override
    public boolean keyDown(int keycode) {
       
        if(!gameOver){
        
            if(keycode == Keys.S){
                swingSword();
            }
            
            if(keycode == Keys.A){
                shootArrow();
            }
            
            if(keycode == Keys.B){
                
                if(hero.overlaps(shopHeart) && coins >= 3){
                       
                        coins -= 3;
                        health++;
                        
                }
                
                if(hero.overlaps(shopArrow) && coins >= 4){
                    
                        coins -= 4;
                        arrows += 3;
                    
                }
                
            }
            
        }
        
        return false;
        
    }

    @Override
    public void update(float dt) {
      
        if(!gameOver){
            
            healthLabel.setText(" x " + health);
            coinLabel.setText(" x " + coins);
            arrowLabel.setText(" x " + arrows);
        
        //  if(!sword.isVisible()){

            if(Gdx.input.isKeyPressed(Keys.LEFT)){
            hero.accelerateAtAngle(180);
            }

            if(Gdx.input.isKeyPressed(Keys.RIGHT)){
                hero.accelerateAtAngle(0);
            }

            if(Gdx.input.isKeyPressed(Keys.UP)){
                hero.accelerateAtAngle(90);
            }

            if(Gdx.input.isKeyPressed(Keys.DOWN)){
                hero.accelerateAtAngle(270); 
            }

        //  }
        
            for(BaseActor actor : BaseActor.getList(mainStage, Solid.class)){
                
                hero.preventOverlap(actor);
                
                for(BaseActor flyer : BaseActor.getList(mainStage,  Flyer.class)){
                
                    if(flyer.overlaps(actor)){
                    
                        flyer.preventOverlap(actor);
                        flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                        
                    }
                
                }
                
                for(BaseActor arrow : BaseActor.getList(mainStage, Arrow.class)){
                    
                    if(arrow.overlaps(actor)){
                        
                        arrow.preventOverlap(actor);
                        arrow.setSpeed(0);
                        arrow.addAction(Actions.fadeOut(0.5f));
                        arrow.addAction(Actions.after(Actions.removeActor()));
                        
                    }
                    
                }
                
            }

            for(BaseActor flyer : BaseActor.getList(mainStage, Flyer.class)){
                
                if(sword.overlaps(flyer)){
                    kill((Flyer)flyer);                 
                }
                
                if(hero.overlaps(flyer)){
                    
                    hero.preventOverlap(flyer);
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                    
                    Vector2 heroPos = new Vector2(hero.getX(), hero.getY());
                    Vector2 flyerPos = new Vector2(flyer.getX(), flyer.getY());
                    Vector2 hitVector = new Vector2(heroPos.sub(flyerPos));
                    
                    hero.setMotionAngle(hitVector.angle());
                    hero.setSpeed(100);
                    health--;
                    hero.getHit();
                    
                }
                
            }
            
            if(sword.isVisible()){

                for(BaseActor actor : BaseActor.getList(mainStage, Bush.class)){

                    if(sword.overlaps(actor)){
                        actor.remove();
                    }

                }
                
            }
            
            for(BaseActor actor : BaseActor.getList(mainStage, Coin.class)){
                
                if(hero.overlaps(actor)){
                    
                    actor.remove();
                    coins++;
                    
                }
                
            }
            
            for(BaseActor actor : BaseActor.getList(mainStage, Arrow.class)){
                
                for(BaseActor flyer : BaseActor.getList(mainStage, Flyer.class)){
                    
                    if(actor.overlaps(flyer)){
                       
                        actor.remove();
                        kill((Flyer)flyer);
                        
                    }
                    
                }
                
            }
            
            for(BaseActor npcActor : BaseActor.getList(mainStage, NPC.class)){
                
                NPC npc = (NPC) npcActor;
                hero.preventOverlap(npc);
                
                boolean nearby = hero.isWithinDistance(4, npc);
                
                if(nearby && !npc.isViewing()){
                    
                    //check NPC ID for dynamic text;
                    if(npc.getID().equals("Gatekeeper")){
                        
                        int flyerCount = BaseActor.countActors(mainStage, Flyer.class);
                        String message = "Destroy the Flyers and you can have the treasure.\n";
                        
                        if(flyerCount > 1){
                            message =  message.concat("There are ")
                                    .concat(String.valueOf(flyerCount)).concat( " left!");
                        }
                        else if(flyerCount == 1){
                            message =  message.concat("There is ")
                                    .concat(String.valueOf(flyerCount)).concat( " left!");
                        }
                        else{
                            
                            message =  message.concat("It's yours!"); 
                            npc.addAction(Actions.fadeOut(5.0f));
                            npc.addAction(Actions.after(Actions.moveBy(-10000, -10000)));
                            
                        }
                        
                        dialogBox.setText(message);
                        
                    }
                    else{
                    dialogBox.setText(npc.getText());
                    }
                
                    dialogBox.setVisible(true);
                    npc.setViewing(true);
                    
                }
                
                if(npc.isViewing() && !nearby){
                    
                    dialogBox.setText(" ");
                    dialogBox.setVisible(false);
                    npc.setViewing(false);
                    
                }
                
            }
       
            if(hero.overlaps(treasure)){
                
                treasure.remove();
                messageLabel.setText("YOU WIN!!!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setFontScale(2);
                messageLabel.setVisible(true);
                
            }
            else if(health <= 0){
                
                hero.remove();
                messageLabel.setText("GAME OVER!!!");
                messageLabel.setColor(Color.RED);
                messageLabel.setFontScale(2);
                messageLabel.setVisible(true);
                
            }
            
        }
       
    }

}
