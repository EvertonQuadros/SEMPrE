/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JOptionPane;

/**
 *
 * @author Not275
 */
public class Util {
    
    public static String projectName = null; //later it will be in configuration file;
    public static Logger logger = null;
    
        public void clearScreen(){
            
            Gdx.gl.glClearColor(0, 0, 0, 1); 
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
        }
        
        public void showErrorMessage(Exception text){
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            text.printStackTrace(pw);
            String stackTrace = sw.toString();
            
            JOptionPane.showMessageDialog(null, stackTrace, "ERROR:", JOptionPane.ERROR_MESSAGE);
            
        }
        
        public void addLog(String message, int logLevel){
          
            if(logLevel == Logger.INFO){
                logger.info(message);
            }
            else if(logLevel == Logger.ERROR){
                logger.error(message);
            }
            else{
                logger.debug(message);
            }
            
        }
        
        public String secondsToString(int pTime) {
            return String.format("%02d:%02d", pTime / 60, pTime % 60);
        }
        
        public Texture getInternalTexture(String pathToFile){
            
            Texture texture = new Texture(Gdx.files.local(getAssetsPath(pathToFile)));
            
            if(texture.getTextureData() == null){
                
                System.out.printf("\nWarning: Asset: " + pathToFile + " loading from Base path.");
                texture = new Texture(Gdx.files.local(getBasePath(pathToFile)));
                
                if(texture.getTextureData() == null){
                    
                    System.out.printf("\nERROR: Asset: " + pathToFile + " NOT FOUND.");
                    texture = new Texture(Gdx.files.local(getBasePath("missing.png")));
                    
                }
                
            }
 
            return texture;
            
        }
        
        public Texture getInternalTexture(String subDirectory, String pathToFile){
            
            Texture texture = new Texture(Gdx.files.local(getAssetsPath(subDirectory, pathToFile)));
            
            if(texture.getTextureData() == null){
                
                System.out.printf("\nWarning: Asset: " + pathToFile + " loading from Base path.");
                texture = new Texture(Gdx.files.local(getBasePath(pathToFile)));
                
                if(texture.getTextureData() == null){
                    
                    System.out.printf("\nERROR: Asset: " + pathToFile + " NOT FOUND.");
                    texture = new Texture(Gdx.files.local(getBasePath("missing.png")));
                    
                }
                
            }
 
            return texture;
            
        }
    
        public FileHandle getInternalFileHandle(String pathToFile){

            FileHandle fileHandle = Gdx.files.local(getAssetsPath(pathToFile));
            
            if(fileHandle == null){

                System.out.printf("\nWarning: Asset: " + pathToFile + " loading from Base path.");
                fileHandle = Gdx.files.local(getBasePath(pathToFile));

                if(fileHandle == null){
                
                    System.out.printf("\nERROR: Asset: " + pathToFile + " NOT FOUND.");
                    fileHandle = Gdx.files.local(getBasePath("missing.png"));
                
                }
                
            }
            
            return fileHandle;
            
        }
        
        public FileHandle getInternalFileHandle(String directory, String pathToFile){

            FileHandle fileHandle = Gdx.files.local(getAssetsPath(directory, pathToFile));
            
            if(fileHandle == null){

                System.out.printf("\nWarning: Asset: " + pathToFile + " loading from Base path.");
                fileHandle = Gdx.files.local(getBasePath(pathToFile));

                if(fileHandle == null){
                
                    System.out.printf("\nERROR: Asset: " + pathToFile + " NOT FOUND.");
                    fileHandle = Gdx.files.local(getBasePath("missing.png"));
                
                }
                
            }
            
            return fileHandle;
            
        }
        
        public String getToolsPath(){
            
            String path = System.getProperty("user.dir");
            
            return path.substring(0, path.length() - 6)
                    .concat("tools")
                    .concat(System.getProperty("file.separator"));
            
        }
        
        public String getAssetsPath(String subDirectory, String filename){
            return  //Gdx.files.getLocalStoragePath()
                    // System.getProperty("file.separator")
                     projectName
                    .concat(System.getProperty("file.separator"))
                    .concat(subDirectory)
                    .concat(System.getProperty("file.separator"))
                    .concat(filename);
        }
        
        public String getAssetsPath(String filename){
            return  //Gdx.files.getLocalStoragePath()
                    // System.getProperty("file.separator")
                     projectName
                    .concat(System.getProperty("file.separator"))
                    .concat(filename);
        }
        
        public String getBasePath(String filename){
            return  //Gdx.files.getLocalStoragePath()
                    //System.getProperty("file.separator")
                     "base"
                    .concat(System.getProperty("file.separator"))
                    .concat(filename);
                    
        }

        public <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
                
            SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(

                (Map.Entry<K,V> e1, Map.Entry<K,V> e2) -> {

                    int res = e1.getValue().compareTo(e2.getValue());
                    
                    return res != 0 ? res : 1;

                });

            sortedEntries.addAll(map.entrySet());
            
            return sortedEntries;

        }
    
}
