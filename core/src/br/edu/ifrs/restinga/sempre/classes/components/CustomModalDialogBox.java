/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.actors.Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Status;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;

import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.classes.effects.Icon;

import br.edu.ifrs.restinga.sempre.classes.screens.LevelScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;

/**
 *
 * @author Not275
 */
public class CustomModalDialogBox extends DialogBox{
    
    private final String path = "events".concat(System.getProperty("file.separator"));
    
    private final String[] messages = {"Compra realizada! Você agora pode gerenciar o lote!", //0
                                       "Não foi possível comprar! Você não possui dinheiro suficiente!", //1
                                       "Oferta não aplicada! O valor é menor que o valor mínimo!", //2
                                       "Oferta não aplicada! Dinheiro insuficiente para esta oferta!", //3
                                       "Oferta aplicada! Aguarde a aceitação da oferta.", //4
                                       "Oferta não aplicada! Existem ofertas maiores no lote!.", //5
                                       "Ação cancelada pelo usuário", //6
                                       "VALOR DA OFERTA:",  //7
                                       "Digite aqui um valor da oferta, se o valor for abaixo do valor do lote\na oferta será desconsiderada.\nOBS: Você deve ter o dinheiro suficiente para fazer a oferta.",
                                       "Mensagem não encontrada.", //9
                                       "ERRO: CÓDIGO NÃO RECONHECIDO/IMPLEMENTADO!", //10
                                       "Espaço Interno", //11
                                       "Depósito", //12
                                       "Fachada", //13
                                       "Treino de Funcionários", //14
                                       "Atendimento ao Cliente", //15
                                       "Fornecedor", //16
                                       "Melhoria não adquirida! Você não tem dinheiro suficiente!", //17
                                       "\n\nLotes comerciais vendem produtos para os consumidores e rendem mais lucro ao longo do tempo do que os lotes residenciais, porém são mais caros e tem uma manutenção maior.", //18
                                       "\n\nLotes residenciais hospedam inquilinos e são mais baratos pois tem uma manutenção menor, porém geram menos lucro do que um lote comercial.", //19
                                       "\nCUIDADO: Esta ação resultará na perda de todos os produtos/inquilinos que você possui no lote!", //20
                                       "COMERCIAL", //21
                                       "RESIDENCIAL", //22
                                       "Construção iniciada! Aguarde a conclusão das obras.", //23
                                       "Não foi possível construir! Você não tem dinheiro suficiente.", //24
                                       
                                       
                                        };  
    
    private final String[] formattedMessages = {"Você realmente deseja comprar o lote por $%d ?", //0- buyEvent (do you really want to buy this lot for $...) 
                                                "O lote atualmente não está a venda, você gostaria de oferecer um valor de compra? \n Mínimo: $%d\n", //1 - (do you want to put an offer to buy this lot? minimum price: $%d
                                                "Venda realizada! O jogador %s comprou o seu lote por $%d!", //2
                                                "Compra realizada! Você comprou o lote do jogador %s por $%d!", //3
                                                "Desejar realmente melhorar a característica %s do lote por $%d ?", //4
                                                "Melhoria %s adquirida! Agora está no nível %d .", //5
                                                "Você realmente deseja expandir este lote %s para %s por $%d ?", //6
                                                "Você realmente deseja transformar este lote %s para %s por $%d ?", //7
                                                
                                                }; 
    
    private TextField offerField;
    
    private BaseActor image;
    private DialogBox backgroundDialog;
    
    CustomTextureButtonStyle buttonAcceptStyle;
    CustomTextureButtonStyle buttonRefuseStyle;
    
    private boolean showing = false;
    private boolean endedModalEvent = false;
    private boolean instantBuy = false;
    private boolean offerBuy = false;
    private boolean informationOnly = false;
    private boolean generalEvent = false;
    
    private boolean focusTextField = false;
    
    private String outcome = null;
    private Color outcomeColor = null;

    private Table table;
    private Table contentTable;
    private ScrollPane scrollPane;
    
    //default buttons (for buy/sell/offer lots);
    private Button buttonAccept;
    private Button buttonRefuse;
    
    //no action button (just closes this dialog);
    private Button buttonOk;
    
    //general event button (runtime changeable button)
    private Button buttonConfirm;
    
    private LevelScreen screen;
    
    private Lot lot;

    public CustomModalDialogBox(float x, float y, Stage stage, String fileName, LevelScreen screen) {
        
        super(x, y, stage, fileName);
        
        init(screen);
        
    }
    
    private void init(LevelScreen screen){
        
        this.screen = screen;
        
        offerField = new TextField("0", new CustomTextFieldStyle());
        offerField.setMaxLength(6);
        
        TextFieldFilter text = (TextField arg0, char arg1) -> Character.toString(arg1).matches("^[0-9]");
        
        offerField.setTextFieldFilter(text);
        
        buttonAcceptStyle = new CustomTextureButtonStyle("hud"
                            .concat(System.getProperty("file.separator"))
                            .concat("OK_1.png"));
        
        buttonRefuseStyle = new CustomTextureButtonStyle("hud"
                            .concat(System.getProperty("file.separator"))
                            .concat("Cancel_1.png"));
        
        buttonAccept = new Button(buttonAcceptStyle);
        buttonAccept.addListener((Event e) -> {

            if(this.screen.isTouchDownEvent(e)){
          
                if(instantBuy){
                    
                    boolean result = lot.buy(screen.getPlayer(), 0);
     
                    if(result){

                        outcome = messages[0];
                        outcomeColor = Color.GREEN;

                        screen.getPlayer().getIcon(Icon.Type.SMILEY_VERY_HAPPY);

                    }
                    else{

                        outcome = messages[1];
                        outcomeColor = Color.RED;

                        screen.getPlayer().getIcon(Icon.Type.SMILEY_SAD);
                     
                    }
        
                }
                else{
          
                    int value = 0;
        
                    if(!offerField.getText().equals("")){
                        value = Integer.valueOf(offerField.getText());
                    }
      
                    if(value < lot.getPrice()){
                        
                        outcome = messages[2];
                        outcomeColor = Color.RED;

                        screen.getPlayer().getIcon(Icon.Type.SMILEY_SAD);
                        
                    }
                    else if(value > screen.getPlayer().getMoney()){
                        
                        outcome = messages[3];
                        outcomeColor = Color.RED;

                        screen.getPlayer().getIcon(Icon.Type.SMILEY_VERY_ANGRY);
                        
                    }
                    else{
                        
                        if(lot.addOffer(screen.getPlayer(), value)){ //testing

                            screen.getPlayer().subMoney(value);

                            outcome = messages[4];
                            outcomeColor = Color.GREEN;

                            screen.getPlayer().getIcon(Icon.Type.SMILEY_HAPPY);

                        }
//                        else{
//
//                            outcome = messages[5];
//                            outcomeColor = Color.YELLOW;
//
//                            screen.getPlayer().getIcon(Icon.Type.SMILEY_SAD);
//
//                        }
                        
                    }
                   
                }
                
                endedModalEvent = true;
                
                hide();
   
            }

            return false;

        });
        
        buttonRefuse = new Button(buttonRefuseStyle);
        buttonRefuse.addListener((Event e) -> {

            if(this.screen.isTouchDownEvent(e)){
      
                outcome = messages[6];
                
                outcomeColor = Color.YELLOW;
                
                endedModalEvent = true;
                
                hide();
   
            }
            
            return false;

        });
        
        buttonConfirm = new Button(buttonAcceptStyle);
        
        buttonOk = new Button(buttonAcceptStyle);
        buttonOk.addListener((Event e) -> {
        
        if(this.screen.isTouchDownEvent(e)){
                
                endedModalEvent = true;
                
                hide();
                
            }
            
            return false;
            
        });

        backgroundDialog = new DialogBox(-25f, -25f, getStage(), "dialog-translucent.png");
        backgroundDialog.setBackgroundColor(Color.GRAY);
        backgroundDialog.setDialogSize(BaseGame.SCREEN_WIDTH + 50, BaseGame.SCREEN_HEIGHT + 50);
        backgroundDialog.setOpacity(0f);

        setDialogSize(BaseGame.SCREEN_WIDTH / 2 + 130, BaseGame.SCREEN_HEIGHT / 2 + 130);
        setBackgroundColor(Color.GRAY);
        setOpacity(0f);
        //setPosition(backgroundDialog.getWidth() / 6, backgroundDialog.getHeight() / 6);
        centerAtActor(backgroundDialog);
        
        table = new Table();
        table.setFillParent(true);
        
        contentTable = new Table();
        
        scrollPane = new ScrollPane(contentTable, new CustomScrollPaneStyle());
        scrollPane.setScrollBarPositions(false, true);
        scrollPane.setFlickScroll(false);
      
        addActor(table);
        
        backgroundDialog.addActor(this);
        backgroundDialog.toBack();
       
    }

    public boolean isShowing() {
        return showing;
    }
    
    public boolean isFinishedModalEvent(){
        return endedModalEvent;
    }
    
    public void resetModal(){
        
        outcome = null;
        outcomeColor = null;
        endedModalEvent = false;
        
    }

    public Color getOutcomeColor() {
        return outcomeColor;
    }

    public String getOutcome() {
        return outcome;
    }
    
    private void initTable(){
        
        table.clearChildren();
        
        contentTable.clearChildren();
       
        table.add(new CustomLabel("")).expand();
        table.row().expand();
        table.add(image).colspan(2).center().padLeft(20).expand();
        table.row().expand();
        table.add(new CustomLabel("")).expand();
        table.row().expand();
        table.add(scrollPane).width(table.getWidth()).height(125).padLeft(50).expand();
        table.row().expand();
        table.add(new CustomLabel("")).expand();
        table.row().expand();
        contentTable.add(getDialogLabel()).width(table.getWidth() - 60);
        table.row().expand();
        table.add(new CustomLabel(""));
        table.row().expand();

        //table.add(getDialogLabel()).width(getWidth()).colspan(2).center().padLeft(20).expand();;
        
        if(offerBuy){
        
            table.add(new CustomLabel(messages[7])).center().padLeft(20).expand();
            table.row().expand();
            table.add(new CustomLabel(""));
            table.row().expand();
            table.add(offerField).center().padLeft(20).padTop(10).expand();
            table.row().expand();
            
        }
        
        if(!informationOnly){
        
            if(!generalEvent){
                table.add(buttonAccept).colspan(3).left().padLeft(100).expand();
            }
            else{
                table.add(buttonConfirm).colspan(3).left().padLeft(100).expand();
            }
            
            table.add(buttonRefuse).colspan(3).right().padRight(100).expand();
            table.row().expand();
        
        }
        else{
            
            table.add(buttonOk).left().padLeft(100).expand();
            table.row().expand();
            
        }
        
        if(focusTextField){
            
            InputEvent input = new InputEvent();
            input.setType(InputEvent.Type.touchDown);
            input.setTarget(offerField);
            offerField.fire(input);
            
        }
        
        scrollPane.setScrollPercentY(0);
        
        if(showing){
        
            InputEvent evt = new InputEvent();
            evt.setType(InputEvent.Type.touchDown);

            scrollPane.fire(evt);
            
        }
        
    }
    
    public void setLabelText(String text){
        getDialogLabel().setText(text);
    }
    
    public void setImageTexture(String fileName, Color color){
        
        image = new BaseActor(0f, 0f, getStage());
        image.loadTexture(fileName);
        image.setColor(color);
        image.setSize(getWidth() / 2, getHeight() / 2);
        
        initTable();
        
    }
    
    private void show(){
        
        backgroundDialog.toFront();
        
        initTable();
        
        backgroundDialog.addAction(Actions.fadeIn(0.3f));
        
        addAction(Actions.fadeIn(0.5f));
        
        showing = true;
        
        screen.getMainStage().pauseStage();
       
    }
    
    private void hide(){
        
        setLabelText("");
        
        backgroundDialog.toBack();
        
        table.clearChildren();
        
        backgroundDialog.addAction(Actions.fadeOut(0.3f));
        
        addAction(Actions.fadeOut(0.5f));
         
        showing = false;
        offerBuy = false;
        instantBuy = false;
        focusTextField = false;
        informationOnly = false;
        generalEvent = false;
        
        screen.getMainStage().unPauseStage();
        
    }
    
    public void buyEvent(Lot lot){ //buying from city (no owner lots) or with selling status;
  
        this.lot = lot;
        
        show();
        
        if(lot.getOwner() == null 
                || lot.getStatus().equals(Type_Status.STATUS_SELLING)){
            
            setLabelText(String.format(formattedMessages[0], lot.getPrice()));
            instantBuy = true;
            
        }
        else{
            
            offerBuy = true;
            setLabelText(String.format(formattedMessages[1], lot.getPrice()));
            offerField.setText(String.valueOf(lot.getPrice()));
            focusTextField = true;
        
        }
       
        if(image != null){
            image.remove();
        }
        
        image = new BaseActor(0f, 0f, getStage());
        image.loadTexture(lot.getTexture());
        image.setSize(getWidth() / 2, getHeight() / 2);
  
        initTable();
        
    }
    
    public void upgradeEvent(Lot lot, int code){ //position of the upgrade: 0- Interior, 1-deposit, 2-exterior, 3-employee training, 4-customer service, 5-provider
        
        boolean invalidCode = false;
        
        if(lot.getOwner() != screen.getPlayer()){
            return;
        }
      
        if(code >= 0 && code < 6){ //upgrade lot events;
            
            int value = lot.getUpgrade().getValueByCode(code); 
            int level = lot.getUpgrade().getLevelByCode(code);

            generalEvent = true;

            this.lot = lot;

            buttonConfirm = new Button(buttonAcceptStyle);

            show();

            setLabelText(String.format(formattedMessages[4], messages[11 + code], value));

            buttonConfirm.addListener((Event e) -> {

                if(this.screen.isTouchDownEvent(e)){

                    if(lot.buyUpgrade(code)){

                        outcome = String.format(formattedMessages[5],  messages[11 + code], level + 1);
                        outcomeColor = Color.GREEN;

                    }
                    else{

                        outcome = messages[17];
                        outcomeColor = Color.RED;

                    }

                    endedModalEvent = true;

                    hide();

                }

                return false;

            });
            
        }
        else if(code >= 6 && code < 10){ //build lot events; //6-7 expand lot //8-9 change lot;
            
            generalEvent = true;

            this.lot = lot;

            buttonConfirm = new Button(buttonAcceptStyle);

            show();

            int value = Math.abs(6 - code);
            int price = 0; //change costs 2x
            
            if(value == 0){
                price = (lot.getPrice() / 10) + 1000;
            }
            else if(value == 1){
                price = (lot.getPrice() / 8) + 3000;
            }
            else if (value == 2){
                price = (lot.getPrice() / 8) + 3000;
            }
            else{
                price = (lot.getPrice() / 5) + 5000;
            }
            
            if(code == 6 || code == 7){
                setLabelText(String.format(formattedMessages[6], lot.getTypeName(), messages[21 + value], price).concat(messages[18 + value]));
            }
            else{
                setLabelText(String.format(formattedMessages[7], lot.getTypeName(), messages[21 + (value - 2)], price).concat(messages[20]));
            }
    
            buttonConfirm.setName(String.valueOf(value));
            
            buttonConfirm.addListener((Event e) -> {

                if(this.screen.isTouchDownEvent(e)){
                    
                    int cod = Integer.valueOf(e.getListenerActor().getName());
                   
                    if(lot.buildUpgrade(cod)){
     
                        outcome = messages[23];
                        outcomeColor = Color.GREEN;
                        
                    }
                    else{
                        
                        outcome = messages[24];
                        outcomeColor = Color.RED;
                        
                    }
                    
                    endedModalEvent = true;

                    hide();

                }

                return false;

            });
            
        }
        else{
            invalidCode = true;
        }

        image = new BaseActor(0f, 0f, getStage());
        image.loadTexture(lot.getTexture());
        image.setSize(getWidth() / 2, getHeight() / 2);
  
        initTable();
        
        if(invalidCode){
            
            table.add(buttonOk); //you've to add this to the dialog table to call event from this button or it will get null;
            
            outcome = messages[10];
            outcomeColor = Color.RED;
 
            InputEvent input = new InputEvent();
            input.setType(InputEvent.Type.touchDown);
            buttonOk.fire(input);
            
        }
        
    }

    public void informationEvent(Lot lot, int code, int value){
        
        this.lot = lot;
        
        informationOnly = true;
        
        show();
        
        if(code == 0){
            setLabelText(String.format(formattedMessages[2], lot.getOwner().getPersonaName(), value));
        }
        else if(code == 1){
            setLabelText(String.format(formattedMessages[3], lot.getOwner().getPersonaName(), value));
        }
        else{
            setLabelText(messages[8]); //message not found;
        }
        
        if(image != null){
            image.remove();
        }
        
        image = new BaseActor(0f, 0f, getStage());
        image.loadTexture(lot.getTexture());
        image.setSize(getWidth() / 2, getHeight() / 2);
  
        initTable();
  
    }
    
    @Override
    public void act(float dt) {
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
    
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && showing){
            
            InputEvent event = new InputEvent();
            event.setType(InputEvent.Type.touchDown);
            
            if(!informationOnly && !generalEvent){
 
                event.setTarget(buttonAccept);
                buttonAccept.fire(event);
            
            }
            else if(generalEvent){
                
                event.setTarget(buttonConfirm);
                buttonConfirm.fire(event);
                
            }
            else{
                
                event.setTarget(buttonOk);
                buttonOk.fire(event);
                
            }
            
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && showing){
            
            InputEvent event = new InputEvent();
            event.setType(InputEvent.Type.touchDown);
            event.setTarget(buttonRefuse);
            buttonRefuse.fire(event);
            
        }
        
    }
    
}
