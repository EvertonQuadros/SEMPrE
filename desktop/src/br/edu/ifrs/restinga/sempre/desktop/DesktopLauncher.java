package br.edu.ifrs.restinga.sempre.desktop;

import br.edu.ifrs.restinga.sempre.SempreMain;

import br.edu.ifrs.restinga.sempre.examples.Main;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.examples.RhythmTapper.RecorderGame;
import br.edu.ifrs.restinga.sempre.examples.RhythmTapper.RhythmTapperGame;
import br.edu.ifrs.restinga.sempre.examples.cardpickup.CardPickupGame;
import br.edu.ifrs.restinga.sempre.examples.jigsawpuzzle.JigsawPuzzleGame;
import br.edu.ifrs.restinga.sempre.examples.jumpingjack.JumpingJackGame;
import br.edu.ifrs.restinga.sempre.examples.missinghomework.TheMissingHomework;
import br.edu.ifrs.restinga.sempre.examples.planedodger.PlaneDodgerGame;
import br.edu.ifrs.restinga.sempre.examples.rectangledestroyer.RectangleDestroyerGame;
import br.edu.ifrs.restinga.sempre.examples.rectangledestroyer10.RectangleDestroyerGameV10;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector.StarFishCollector;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector.StarFishCollectorBeta;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector3.StarfishGame;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector5.StarfishGameV5;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector6.StarfishGameV6;
import br.edu.ifrs.restinga.sempre.examples.spacerocks.SpaceGame;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector10.Starfish;
import br.edu.ifrs.restinga.sempre.examples.starfishcollector10.StarfishGameV10;
import br.edu.ifrs.restinga.sempre.examples.treasurequest.TreasureQuestGame;

import br.edu.ifrs.restinga.sempre.utils.Util;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.badlogic.gdx.utils.Logger;

public class DesktopLauncher {
    
    public static void main (String[] args) {
    
        Logger logger = new Logger("Sempre Logger", Logger.DEBUG);
        
        //examples:
        //Main();
        //StarFishCollector()
        //StarFishCollectorBeta()
        //StarfishGame()
        //SpaceGame()
        //StarfishGameV5()
        //StarfishGameV6()
        //TheMissingHomework()
        //RhythmTapperGame()
        //RecorderGame()
        //PlaneDodgerGame()
        //RectangleDestroyerGame()
        //JigsawPuzzleGame()
        //CardPickupGame()
        //StarfishGameV10()
        //JumpingJackGame()
        //TreasureQuestGame()
        //SempreMain();
      
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        
        SempreMain app = new SempreMain();
        //BaseGame app = new StarfishGameV10();
        
        //System.out.printf("\n" + (0.1f - ((0.602f * 2f) * 0.1)));
        
        config.width = BaseGame.SCREEN_WIDTH;
        config.height = BaseGame.SCREEN_HEIGHT;
        config.title = SempreMain.getProjectName();
        config.resizable = false;
        //config.fullscreen = true;
        
        //ToolsLauncher.Hiero();
        
        LwjglApplication application = 
                new LwjglApplication(app, config);

        logger.info("Game Version: ".concat(String.valueOf(application.getVersion())));
        
//        double x2 = 0.25f;
//        double x1 = 0.5f;
//        double y2 = 0.25f;
//        double y1 = 0.5f;
//
//        double value = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
//        System.out.printf("\nvalor: " + value);
//        
        }
     
        //System.out.printf ("Game Version: " + application.getVersion());
        //config.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        //config.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        //config.fullscreen = true;
                
//       adicionar exibição das informações/características dos pedestres;
//       adicionar exibição das características do lote;
//       adicionar as características do lote;
//       adicionar interação do usuário com o lote;
//       adicionar interação do pedestre com o lote; (efeito de entrar no lote);
    
    }
        

