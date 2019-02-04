/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.desktop;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;
import br.edu.ifrs.restinga.sempre.utils.Util;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Not275
 */
public class ToolsLauncher {
    
    public static void Hiero(){
        call("runnable-hiero.jar");
    }
    
    private static void call(String argument){
        
        try {
            Desktop.getDesktop().open(new File(BaseGame.util.getToolsPath().concat(argument))); 
        } 
        catch (IOException | IllegalArgumentException ex) {
            BaseGame.util.showErrorMessage(ex);
        }
    
    }
    
}
