/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.utils;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;

/**
 *
 * @author Not275
 */
public class FileUtil {
 
    private static boolean finished;
    private static FileHandle fileHandle;
    private static final int openDialog = 1;
    private static final int saveDialog = 2;
    
    public static FileHandle showOpenDialog(){
        return showDialog(openDialog);
    }
    
    public static FileHandle showSaveDialog(){
        return showDialog(saveDialog);
    }
    
    private static FileHandle showDialog(int dialogType){
        
        new JFXPanel();
        finished = false;
        
        Platform.runLater(
            
            () -> {
            
                FileChooser chooser = new FileChooser();
                File file;
                
                if(dialogType == openDialog){
                    file = chooser.showOpenDialog(null);
                }
                else{
                    file = chooser.showSaveDialog(null);
                }
                
                if(file != null){
                    fileHandle = new FileHandle(file);
                }
                else{
                    fileHandle = null;
                }
                
                finished = true;
     
            }
                
        );
        
        while(!finished){
        
            synchronized(Thread.currentThread()){  //block of code required to sync the thread or it will be desync and stop to respond (Hang).
                
                try {
                    Thread.currentThread().wait(100);
                } 
                catch (InterruptedException ex) {
                    BaseGame.util.showErrorMessage(ex);
                }
                
            }
            
        } 
        
        return fileHandle;
        
    }
    
}
