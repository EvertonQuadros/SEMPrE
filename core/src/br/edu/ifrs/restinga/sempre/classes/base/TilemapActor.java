/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.base;

import br.edu.ifrs.restinga.sempre.utils.Util;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Not275
 */
public class TilemapActor extends Actor{

    private final TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private Camera mainCamera;

    public TilemapActor(String fileName, Stage stage) {
        
        //set up tile map, renderer and camera;
        tiledMap = new TmxMapLoader().load(BaseGame.util.getAssetsPath(fileName));
        init(stage);
        
    }
    
    private void init(Stage stage){
        
        int tileWidth = (int) tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int) tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int) tiledMap.getProperties().get("width");
        int numTilesVertical = (int) tiledMap.getProperties().get("height");
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;
        
        BaseActor.setWorldBounds(mapWidth, mapHeight);
        
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap, 1 ,8191);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, BaseGame.SCREEN_WIDTH, BaseGame.SCREEN_HEIGHT);
        tiledCamera.update();
        
        stage.addActor(this);
        
    }

    @Override
    public void act(float delta) {
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        
        //adjust tilemap camera to stay in sync with main camera;
        mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);
        
        //need the following code to force batch order, otherwise this will be batched and renderered last.
        batch.end();
        tiledMapRenderer.render();
        batch.begin();
        
    }
    
    public Vector3 getMousePosInGameWorld() {
       return mainCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
    
    public ArrayList<MapObject> getRectangleList(String propertyName){
        
        ArrayList<MapObject> list = new ArrayList();
        
        for(MapLayer layer : tiledMap.getLayers()){
            
            for(MapObject object : layer.getObjects()){
                
                if(object instanceof RectangleMapObject){
                    
                    MapProperties properties = object.getProperties();
                    
                    if(properties.containsKey("name") && properties.get("name").equals(propertyName)){
                        list.add(object);
                    }
                    
                }
                
            }
            
        }
        
        return list;
        
    }
    
    public ArrayList<MapObject> getTileList(String propertyName){
        
        ArrayList<MapObject> list = new ArrayList();
        
        for(MapLayer layer : tiledMap.getLayers()){
            
            for(MapObject object : layer.getObjects()){
                
                if(object instanceof TiledMapTileMapObject){
                    
                    MapProperties properties = object.getProperties();
                    MapProperties defaultProperties;
                    
                    // Default mapproperties are stored within associated tile object
                    // instance-specific overrides are stored in map object;
                    
                    TiledMapTileMapObject tiledmapObject = (TiledMapTileMapObject) object;
                    TiledMapTile tile = tiledmapObject.getTile();
                    
                    defaultProperties = tile.getProperties();
                    
                    if(defaultProperties.containsKey("name") && defaultProperties.get("name").equals(propertyName)){
                        list.add(object);
                    }
                    
                    //get list of default property keys
                    Iterator<String> propertyKeys = defaultProperties.getKeys();
                    
                    //iterate over keys, copy default values into properties if needed
                    while(propertyKeys.hasNext()){
                        
                        String key = propertyKeys.next();
                        
                        if(!properties.containsKey(key)){
                        
                            Object value = defaultProperties.get(key);
                            properties.put(key, value);
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        return list;
        
    }
    
    public void mappingTileObjects(Class actor, Stage stage){
        
        Class[] complex = new Class[]{MapProperties.class, Stage.class};
        
        try {
            
            Class theClass = Class.forName(actor.getName());
            
            for(MapObject object : getTileList(theClass.getSimpleName())){
                
                MapProperties properties = object.getProperties();
                
                Constructor constructor;
                
                try{
                    
                    constructor = actor.getConstructor(complex);
                    theClass.cast(constructor.newInstance(properties, stage));
                    
                }
                catch(NoSuchMethodException ex){
                    System.out.printf("\n ---> ERROR: ".concat(ex.getMessage()));
                }
               
            }
            
        } 
        catch (ClassNotFoundException 
             | IllegalAccessException
             | IllegalArgumentException 
             | InstantiationException 
             | SecurityException 
             | InvocationTargetException ex) {
            BaseGame.util.showErrorMessage(ex);
            //ex.printStackTrace();
        }

    }
    
    public void mappingRectangleObjects(Class actor, Stage stage){
        
        Class[] complex = new Class[]{MapProperties.class, Stage.class};
        
        try {
            
            Class theClass = Class.forName(actor.getName());

            for(MapObject object : getRectangleList(theClass.getSimpleName())){
                
                MapProperties properties = object.getProperties();
                
                Constructor constructor;
                
                try{
                    
                    constructor = theClass.getConstructor(complex);
                    theClass.cast(constructor.newInstance(properties, stage));

                }
                catch(NoSuchMethodException ex){
                    System.out.printf("\n ---> ERROR: ".concat(ex.getMessage()));
                }
               
            }
            
        } 
        catch (ClassNotFoundException 
             | IllegalAccessException
             | IllegalArgumentException 
             | InstantiationException 
             | SecurityException 
             | InvocationTargetException ex) {
            BaseGame.util.showErrorMessage(ex);
            //ex.printStackTrace();
        }
        
    }
    
}
