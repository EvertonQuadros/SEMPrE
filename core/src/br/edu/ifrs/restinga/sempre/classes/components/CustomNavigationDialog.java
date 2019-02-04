/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.components;

import br.edu.ifrs.restinga.sempre.classes.actors.Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Lot;
import br.edu.ifrs.restinga.sempre.classes.actors.Lot.Type_Status;
import br.edu.ifrs.restinga.sempre.classes.actors.Persona;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.classes.screens.LevelScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;

import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 *
 * @author Not275
 */
public class CustomNavigationDialog extends BaseActor {
    
    private BaseActor actor = null;
    private LevelScreen screen;
    private Button cancelButton;
    private Button backButton;
    private Button textureLot;
    
    private CustomLabel messageLabel;
    
    private DialogBox messageBox;
    private DialogBox navigationBox;
    
    private Table messageTable;
    private Table navigationTable;
    private Table containerTable;
    private ScrollPane scrollPane;

    private CustomModalDialogBox decisionModal;
    
    private int state = 0; //0 info level , 1 management level, 2 list level, 3....
    
    private final float updateTimerSpan = 1.0f;
    private float updateTimer = 0f;
    private boolean showing = false; //false disabled (isn't showing)...
    private boolean limited = false; //false its not limited interaction (when player is away of it - only for lots);
    private boolean remove = false;
    private boolean update = false;
    private Product selectProduct = null;
    
    private final String[] messages = {"MENU DO PERSONAGEM:", //0
                                       "MENU DO LOTE: ", //1
                                       "INTERESSE: ----------", //2
                                       "PRODUTIVIDADE: ", //3
                                       "MOTIVAÇÃO: ", //4
                                       "DINHEIRO: ", //5
                                       "PROCURANDO EMPREGO?   ", //6
                                       "PACIÊNCIA: ", //7
                                       "Tipo de produtos necessitados por este personagem.\nApenas lotes que oferecem estes produtos serão visitados por ele.\nA barra abaixo representa o nível de necessidade em relação a este produto.\nQuanto maior o valor, mais provável será o sucesso da negociação\ne a quantidade comprada.",
                                       "Atributo representa a produtividade do personagem quando empregado.\nValores altos representam maior velocidade e qualidade no atendimento.",
                                       "Atributo representa a motivação do personagem como empregado.\nEste valor reduz com o tempo, e valores abaixo da metade indicam uma\nprobabilidade maior para greves e pedidos de demissão.\nOferecer melhores condições de trabalho e salários aumentam este valor.",
                                       "Atributo representa a quantidade de dinheiro do personagem.\nQuanto maior este valor, mais disposto para gastar está o personagem.",
                                       "Atributo que informa se este personagem pode ser um empregado.\nObs: empregados com melhores atributos exigem salários maiores.",
                                       "Atributo relacionado a capacidade de aceitar um atendimento ruim.\nValores menores significam menos tolerância e maior chance de fracasso no atendimento.",
                                       "\nSIM", //14
                                       "\nNÃO", //15
                                       "CARACTERÍSTICAS: ", //16
                                       "Características do personagem.", //17
                                       "DONO: Prefeitura", //18
                                       "TIPO: ", //19
                                       "REPUTAÇÃO: ", //20
                                       "TAMANHO INTERNO: ", //21
                                       "TAMANHO DEPÓSITO: ", //22
                                       "QUALIDADE DOS PRODUTOS:", //23
                                       "VARIEDADE DOS PRODUTOS:", //24
                                       "VENDENDO", //25
                                       "CONTRATANDO", //26
                                       "NEGOCIANDO LOTE", //27
                                       "PADRÃO", //28
                                       "O proprietário do lote.\nLotes da prefeitura podem ser comprados diretamente.", //29
                                       "O tipo do lote [VAZIO, BALDIO, CONSTRUÇÃO, COMERCIAL ou RESIDENCIAL].", //30
                                       "O preço do lote. As melhorias e produtos também impactam neste valor.", //31
                                       "Atributo que define a probabilidade receber uma visita de um pedestre.\nEste valor muda conforme resultados das negociações ou eventos.",
                                       "Atributo que define a quantidade de espaço para novos visitantes e funcionários.",
                                       "Atributo que define a quantidade de espaço para novos produtos.\nEste valor impacta na variedade de produtos oferecidos.",
                                       "Atributo que define a qualidade dos produtos oferecidos pelo lote.\nQuanto melhor a qualidade, maior será o valor pago pelos produtos e o custo para mantê-los.",
                                       "Atributo que define a variedade de um mesmo produto, valor alto significa melhores lucros.\nObs: Manter uma quantidade grande de diferentes produtos diminui este valor.",
                                       "COMPRAR LOTE", //37
                                       "Comprar este lote.", //38
                                       "OFERECER COMPRA", //39
                                       "Colocar uma oferta de compra neste lote.", //40
                                       "CANCELAR OFERTA", //41
                                       "Remover oferta de compra neste lote.", //42
                                       "GERENCIAR LOTE", //43
                                       "Opções avançadas do lote.", //44
                                       "Oferta removida com sucesso!", //45
                                       "Não foram encontradas ofertas para este lote!", //46
                                       "Situação atual do lote.", //47
                                       "Características do lote", //48
                                       "VOCÊ DEVE ESTAR DENTRO DO LOTE PARA INTERAGIR!", //49
                                       "VOLTAR", //50
                                       "Voltar ao menu de informações.", //51
                                       "VENDER", //52
                                       "Colocar este lote à venda.", //53
                                       "CANCELAR ORDEM DE VENDA DO LOTE", //54
                                       "MELHORIAS: ", //55
                                       "Visão geral das melhorias do lote em valores absolutos.", //56
                                       "Ação concluída! O lote agora está a venda!", //57
                                       "Oferta de venda do lote cancelada pelo usuário!", //58
                                       "NÃO HÁ OFERTAS NO MOMENTO!", //59
                                       "LISTA DE PROPOSTAS DE COMPRA:", //60
                                        //  "JOGADOR:", //61 
                                       "JOGADOR:                         PROPOSTA:",
                                       "PROPOSTA:", //62
                                       "VER PROPOSTAS", //63
                                       "Lista de propostas para compra deste lote.",  //64
                                       "Espaço Interno", //65
                                       "Depósito", //66
                                       "Fachada", //67
                                       "Treino de Funcionários", //68
                                       "Atendimento ao Cliente", //69
                                       "Fornecedor", //70
                                       "VER MELHORIAS", //71
                                       "Investir em melhoria das características do lote, dividas em 6 categorias:\nEspaço interno: responsável pela espaço interno do lote.\nDepósito: responsável para o espaço de alocação de produtos.\nFachada: responsável pela atratividade do lote.\nTreino de empregados: responsável pela melhoria de características dos funcionários.\nAtendimento: responsável pelas melhores condições do atendimento.\nFornecedor: responsável pela melhor qualidade dos produtos comprados pelo lote.",
                                       "EXPANDIR COMERCIAL", //73
                                       "Construir um lote do tipo comercial a partir de um lote vazio ou baldio.", //74
                                       "EXPANDIR RESIDENCIAL", //75
                                       "Construir um lote do tipo residencial a partir de um lote vazio ou baldio.", //76
                                       "TORNAR RESIDENCIAL", //77
                                       "Altera um lote do tipo comercial para residencial.\nATENÇÃO: Todos os produtos no depósito serão perdidos após esta ação.", //78
                                       "TORNAR COMERCIAL", //79
                                       "Altera um lote do tipo residencial para comercial.\nATENÇÃO: Todos os inquilinos serão expulsos com esta ação.", //80
                                       "GERENCIAR PRODUTOS: ", //81
                                       "Gerenciar a compra e venda de produtos do lote.", //82
                                       "GERENCIAR INQUILOS:", //83
                                       "Gerenciar os inquilinos, alugúeis e informações sobre o serviço", //84
                                       "INVENTÁRIO DE PRODUTOS: ", //85,
                                       "QTDADE:", //86 //quantity
                                       "ESPAÇO:", //87
                                       "CUSTO:", //88
                                       "Tipo do produto. Obs: Coloque o ponteiro sobre a caixa para ver o tipo.", //89
                                       "Quantidade total do produto.", //90
                                       "Espaço total ocupado.", //91
                                       "Custo total diário.", //92
                                       "Espaço Disponível :", //93
                                       "Custo Total/Diário:" //94
                                       
                                       };
    
    private final String[] formattedMessages = {"NOME: %s", //0
                                                "IDADE: %d", //1
                                                "INTERESSE: %s", //2
                                                "TIPO: %s", //3
                                                "PREÇO: $%d", //4
                                                "DONO: %s", //5
                                                "SITUAÇÃO: %s", //6
                                                "REPUTAÇÃO: %.2f", //7
                                                "INTERNO:    %d / %d  (Máx Pessoas)", //8
                                                "DEPÓSITO:  %d / %d  (Máx Produtos)", //9
                                                "QUALIDADE: %.2f", //10
                                                "VARIEDADE: %.2f", //11
                                                "Melhorar o nível do %s no lote.\nNível Atual: %d .", //12
                                                "A característica %s está no nível máximo.\nNível Atual: 10 .", //13
                                                
                                                };
    
    public CustomNavigationDialog(Float x, Float y, Stage stage, LevelScreen screen) {
        
        super(x, y, stage);
        
        init(screen);
        
    }
    
    private void init(LevelScreen screen){

        this.screen = screen;

        messageLabel = new CustomLabel("");
        messageLabel.setAlignment(Align.center);
        
        messageBox = new DialogBox(0, 0, getStage(), "dialog-translucent.png");
        messageBox.setDialogSize(800, 48);
        messageBox.setBackgroundColor(Color.BLACK);
        messageBox.setOpacity(0);
        
        messageTable = new Table();
        messageTable.setFillParent(true);
        messageTable.add(messageLabel).center().expandX().expandY();
        
        messageBox.addActor(messageTable);
        messageBox.setVisible(false);
        
        navigationBox = new DialogBox(3, 5, getStage(), "dialog-translucent.png");
        navigationBox.setDialogSize(432, BaseGame.SCREEN_HEIGHT - 92);
        navigationBox.setBackgroundColor(Color.GRAY);
        navigationBox.setOpacity(0);
        
        navigationTable = new Table();
        navigationTable.setFillParent(true);
        
        containerTable = new Table();
        
        scrollPane = new ScrollPane(containerTable, new CustomScrollPaneStyle());
        scrollPane.setScrollBarPositions(false, true);
        scrollPane.setFlickScroll(false);
   
        navigationBox.addActor(navigationTable);

        String path = "hud".concat(System.getProperty("file.separator"))
                           .concat("dialog.png");
        
        decisionModal = new CustomModalDialogBox(0f, 0f, getStage(), path, screen);
         
        cancelButton = new Button(new CustomTextureButtonStyle("hud"
                                .concat(System.getProperty("file.separator"))
                                .concat("Cancel_1.png")));

        cancelButton.addAction(Actions.forever(Actions.sequence(Actions.color(Color.RED, 0.5f), Actions.color(Color.WHITE, 0.5f))));
        
        cancelButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                disableDialog();
            }

            return false;

        });
        
        backButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png")); //go back to general information.
        backButton.add(new CustomLabel(messages[50], messages[51] ,BaseGame.getLabelButton()));
        backButton.setName("backButton");
        backButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                if(state != 1){
                    state = 1;
                }
                else{
                    state = 0;
                }

                resetNavigationDialog(); 
                
            }

            return false;

        });
        
    }

    public boolean isShowing() {
        return showing;
    }
    
    public boolean isLimited() {
        return limited;
    }

    public BaseActor getActor() {
        return actor;
    }

    public void setActor(BaseActor actor) {
        this.actor = actor;
    }

    public CustomModalDialogBox getModal() {
        return decisionModal;
    }

    public void resetNavigationDialog(){
        
        updateTimer = updateTimerSpan + 1f;
        update = false;
        
    }
    
    public void disableDialog(){
        
        if(actor != null){
            
            if(actor instanceof Persona){
                ((Persona)actor).deselect();
            }
            else{
                ((Lot)actor).deselect();
            }
            
            actor = null;
            
        }
     
        showing = false;
        
        limited = false;
        
        remove = false;
        
        navigationTable.clearChildren();       
        navigationBox.addAction(Actions.fadeOut(0.5f));
    
        state = 0;
        
        update = false;
        
    }
  
    public void castMessage(String text, Color color){
        
        messageBox.setVisible(true); 
        
        if(color == null){
            messageLabel.setColor(Color.WHITE);
        }
        else{
            messageLabel.setColor(color);
        }
        
        messageLabel.setText(text);
        
        navigationBox.setY(45);

        if(messageBox.getActions().size > 0){
            messageBox.clearActions();
        }
        
        messageBox.addAction(Actions.after(Actions.sequence(Actions.fadeIn(0.5f), 
                                              Actions.delay(3.0f), 
                                              Actions.fadeOut(0.5f))));
        
        messageBox.addAction(Actions.after(Actions.visible(false)));
        
    }
    
    public ProgressBar getProgressBarInfo(String name, float value, float max, boolean horizontal){
        
        CustomProgressBar progressBar;
        
        if(max == 0){
            throw new IllegalArgumentException("getProgressBarInfo: max value cannot be equals to zero.");
        }
        //0.0f, 1.0f, 0.1f, false
        if(value < (max * 0.4)){
            progressBar = new CustomProgressBar(0, max, max / 10, horizontal);
        }
        else{
            progressBar = new CustomProgressBar(0, max, max / 10, horizontal);
        }
        
        progressBar.setValue(value);
        progressBar.setName(name);
        
        return progressBar;
        
    }
    
    private void navigation(){

        CustomLabel info = new CustomLabel(""); //Persona menus:
        navigationTable.row();
        navigationTable.add(info).padLeft(10);
        navigationTable.add(cancelButton).right().expand();
        navigationTable.row();
        navigationTable.add(scrollPane).width(navigationTable.getWidth()).height(navigationTable.getHeight() - 67);

        scrollPane.setScrollPercentY(0);
        
        if(actor instanceof Persona){
            
            Persona persona = (Persona)actor;

            info.setText(messages[0]);
            
            persona.select(cancelButton);

            update(persona);
            
        }
        else{
            
            Lot lot = (Lot)actor;

            info.setText(messages[1]);

            lot.select();
            
            cancelButton.addListener((Event e) -> {

                if(screen.isTouchDownEvent(e)){

                    disableDialog();
                    
                    if(screen.getPlayer().isInsideLot()){
                        screen.getPlayer().exitLot();
                    }
      
                }

                return false;

            });
            
            textureLot = new Button(new CustomTextureButtonStyle(lot.getTexture()));
      
            update(lot);
            
        }
 
        if(showing){
        
            InputEvent evt = new InputEvent();
            evt.setType(InputEvent.Type.touchDown);

            scrollPane.fire(evt);
            
        }
        
    }

    private void update(Persona persona){
        
        if(update){
            
            for(Actor actr : containerTable.getChildren()){
                
                if(actr.getName() != null){
                    
                    if(actr instanceof ProgressBar){
                        
                        if(actr.getName().equals("necessity")){
                            ((ProgressBar)actr).setValue(persona.getNecessityLevel());
                        }
                        else if(actr.getName().equals("productivity")){
                            ((ProgressBar)actr).setValue(persona.getProductivity());
                        }
                        else if(actr.getName().equals("motivation")){
                            ((ProgressBar)actr).setValue(persona.getMotivation());
                        }
                        else if(actr.getName().equals("money")){
                            ((ProgressBar)actr).setValue(persona.getMoney());
                        }
                        else if(actr.getName().equals("patience")){
                            ((ProgressBar)actr).setValue(persona.getPatience());
                        }
                        
                    }
                    
                }
                
            }
            
            return;
            
        }
    
        CustomLabel name = new CustomLabel(String.format(formattedMessages[0], persona.getPersonaName())); //Name:
        CustomLabel age = new CustomLabel(String.format(formattedMessages[1], persona.getAge())); //age:
        CustomLabel necessity = new CustomLabel(messages[2]); //product concern/interest
        CustomLabel productivity = new CustomLabel(messages[3]); //productivity: 
        CustomLabel motivation = new CustomLabel(messages[4]); //motivation: 
        CustomLabel money = new CustomLabel(messages[5]); //Money:
        CustomLabel employee = new CustomLabel(messages[6]); //looking for job?;
        CustomLabel patience = new CustomLabel(messages[7]);
        
        necessity.setToolTip(messages[8]);
        
        productivity.setToolTip(messages[9]);
        
        motivation.setToolTip(messages[10]);
        
        money.setToolTip(messages[11]);
        
        employee.setToolTip(messages[12]);
        
        patience.setToolTip(messages[13]);
        
        Button texturePersona = new Button(new CustomTextureButtonStyle(persona.getProfileTexture()));
        texturePersona.setColor(persona.getColor());
        
        Button textureInterest;
   
        if(persona.isEmployee()){
            employee.setText(employee.getText().append(messages[14])); //isEmployee (yes)
        }
        else{
            employee.setText(employee.getText().append(messages[15])); //!isEmployee (no) 
        }

        necessity.setText(String.format(formattedMessages[2], persona.getNecessity().getTypeByName())); //Necessity:
        textureInterest = new Button(new CustomTextureButtonStyle(persona.getNecessity().getTextureByName())); 
        
        containerTable.add(texturePersona).width(100).height(120).center().colspan(2).expand();
        containerTable.row().expand();
        containerTable.add(name).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(age).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(new CustomLabel(messages[16], messages[17])).center().padLeft(10); //Persona characteristics
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(textureInterest);
        containerTable.row().expand();
        containerTable.add(necessity).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("necessity", persona.getNecessityLevel(), 1.0f, false)).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(employee).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(patience).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("patience", persona.getPatience(), 1.0f, false)).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(productivity).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("productivity", persona.getProductivity(), 1.0f, false)).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(motivation).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("motivation", persona.getMotivation(), 1.0f, false)).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(money).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("money", persona.getMoney(), 1400, false)).align(Align.left).padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        
        update = true;
        
    }
  
    private void update(Lot lot){
   
        if(update){
            
            for(Actor actr : containerTable.getChildren()){
                
                if(actr.getName() != null){
                    
                    if(actr instanceof CustomLabel){
   
                        if(actr.getName().equals("owner")){
                            
                            if(lot.getOwner() == null){
                                ((CustomLabel)actr).setText(String.format(messages[18]));
                            }
                            else{
                                ((CustomLabel)actr).setText(String.format(formattedMessages[5], lot.getOwner().getPersonaNameLimit(15)));
                            }
                           
                        }
                        else if(actr.getName().equals("type")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[3], lot.getTypeName()));
                        }
                        else if(actr.getName().equals("price")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[4], lot.getPrice()));
                        }
                        else if(actr.getName().equals("status")){

                            if(lot.getStatus().equals(Type_Status.STATUS_SELLING)){
                                ((CustomLabel)actr).setText(String.format(formattedMessages[6], messages[25]));
                            }
                            else if(lot.getStatus().equals(Type_Status.STATUS_HIRING)){
                                ((CustomLabel)actr).setText(String.format(formattedMessages[6], messages[26]));
                            }
                            else if(lot.getStatus().equals(Type_Status.STATUS_BUYING)){
                                ((CustomLabel)actr).setText(String.format(formattedMessages[6], messages[27]));
                            }
                            else{
                                ((CustomLabel)actr).setText(String.format(formattedMessages[6], messages[28]));
                            }

                        }

                    }
                    else if(actr instanceof ProgressBar){

                        if(actr.getName().equals("reputation")){
                            ((ProgressBar)actr).setValue(lot.getReputation());
                        }
                        else if(actr.getName().equals("interior")){
                            ((ProgressBar)actr).setValue(lot.getInteriorSize());
                        }
                        else if(actr.getName().equals("depot")){
                            ((ProgressBar)actr).setValue(lot.getDepotSize());
                        }
                        else if(actr.getName().equals("quality")){
                            ((ProgressBar)actr).setValue(lot.getQuality());
                        }
                        else if(actr.getName().equals("variety")){
                            ((ProgressBar)actr).setValue(lot.getVariety());
                        }

                    }
                    
                }
                
            }
        
            return;
            
        }
        
        limited = !(screen.getPlayer().isInsideLot()
                    && screen.getPlayer().getCurrentLot().equals(lot));
  
        CustomLabel owner = new CustomLabel(messages[18]); //property city/mayor
        CustomLabel type = new CustomLabel(String.format(formattedMessages[3], lot.getTypeName())); //property major type. //receives bonus in this type of by walker visit default 10%, main type 33%.
        CustomLabel price = new CustomLabel(String.format(formattedMessages[4], lot.getPrice())); //property price (Price: );
        CustomLabel reputation = new CustomLabel(messages[20]); //property reputation (Reputation: );
        CustomLabel interior = new CustomLabel(messages[21]); //property interior size (Interior Size: );
        CustomLabel depot = new CustomLabel(messages[22]); //property deposit size (Deposit Size: );
        CustomLabel quality = new CustomLabel(messages[23]); //property product quality (Products Quality: );
        CustomLabel variety = new CustomLabel(messages[24]); //property product variety (Products Variety: );
        CustomLabel status;

        if(lot.getStatus().equals(Type_Status.STATUS_SELLING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[25]), messages[47]);
        }
        else if(lot.getStatus().equals(Type_Status.STATUS_HIRING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[26]), messages[47]);
        }
        else if(lot.getStatus().equals(Type_Status.STATUS_BUYING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[27]), messages[47]);
        }
        else{
            status = new CustomLabel(String.format(formattedMessages[6], messages[28]), messages[47]);
        }
        
        status.setName("status");
        
        owner.setToolTip(messages[29]); 
        owner.setName("owner");
        
        type.setToolTip(messages[30]);
        type.setName("type");
        
        price.setToolTip(messages[31]);

        reputation.setToolTip(messages[32]); //explaination about reputation attribute.
       
        interior.setToolTip(messages[33]);
       
        depot.setToolTip(messages[34]);
        
        quality.setToolTip(messages[35]);
       
        variety.setToolTip(messages[36]);
        
        //event buttons
        
        //CustomTextButton buyButton = new CustomTextButton(new CustomTextButtonStyle("customhierofont.fnt", "button.png"), "$ comprar");
        //CustomTextButton manageButton = new CustomTextButton(new CustomTextButtonStyle("customhierofont.fnt", "button.png"), "gerenciar");
        
        Button buyButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button manageButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        
        if(lot.getStatus().equals(Type_Status.STATUS_SELLING) || lot.getOwner() == null){
            buyButton.add(new CustomLabel(messages[37], messages[38], BaseGame.getLabelButton()));
        }
        else{
            
            if(lot.getOfferSize() < 1 || !lot.getPersonaOffer(screen.getPlayer())){
                buyButton.add(new CustomLabel(messages[39], messages[40], BaseGame.getLabelButton()));
            }
            else{
                
                remove = true;
                buyButton.add(new CustomLabel(messages[41], messages[42], BaseGame.getLabelButton()));
                
            }
            
        }
        
        manageButton.add(new CustomLabel(messages[43], messages[44], BaseGame.getLabelButton()));
        
        if(lot.getOwner() != null){
            owner.setText(String.format(formattedMessages[5], lot.getOwner().getPersonaNameLimit(15))); //property owner         
        }

        buyButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){

                if(!remove){
                    decisionModal.buyEvent(lot);
                }
                else{
                    
                    Integer value = lot.removeOffer(screen.getPlayer());
                    
                    if(value != null){
                        castMessage(messages[45], Color.GREEN);
                    }
                    else{
                        castMessage(messages[46], Color.YELLOW);
                    }
                    
                }
                
                disableDialog();
                screen.getPlayer().exitLot();
                
            }

            return false;

        });
        
        manageButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                state = 1;
                resetNavigationDialog();
                update = false;
                
            }

            return false;

        });

        containerTable.add(textureLot).width(navigationTable.getWidth() / 2).height(navigationTable.getWidth() / 2).center().colspan(2);
        containerTable.row().expand();
        containerTable.add(type).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(owner).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(status).left().padLeft(10); //status (Selling, buying[only for rivals], hiring[employees] and default;
        containerTable.row().expand();
        containerTable.add(price).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("price", lot.getPrice(), 100000f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(new CustomLabel(messages[16], messages[48])).center().padLeft(10); //Characteristics 
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(reputation).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("reputation", lot.getReputation(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(interior).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("interior", lot.getInteriorSize(), 25.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(depot).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("depot", lot.getDepotSize(), 150.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(quality).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("quality", lot.getQuality(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(variety).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("variety", lot.getVariety(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        
        if(!isLimited()){
        
            if(lot.getOwner() != screen.getPlayer()){
                containerTable.add(buyButton).center().colspan(2).expand();
            }
            else{
                containerTable.add(manageButton).center().colspan(2).expand();
            }
       
        }
        else{
            
            containerTable.add(new CustomLabel(""));
            containerTable.row().expand();
            
            castMessage(messages[49], Color.YELLOW);
            
        }
        
        update = true;
        
    }
    
    private void updateNavigation(Lot lot){

        if(update){
            
            for(Actor actr : containerTable.getChildren()){
                
                if(actr.getName() != null){
                    
                    if(actr instanceof CustomLabel){
                        
                        if(actr.getName().equals("type")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[3], lot.getTypeName()));
                        }
                        else if(actr.getName().equals("price")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[4], lot.getPrice()));
                        }
                        else if(actr.getName().equals("interior")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[8], lot.getPopulation().size(), lot.getInteriorSize()));
                        }
                        else if(actr.getName().equals("depot")){
                            ((CustomLabel)actr).setText(String.format(formattedMessages[9], lot.getProductList().size(), lot.getDepotSize()));
                        }
                        
                    }
                    else if(actr instanceof ProgressBar){
                        
                        if(actr.getName().equals("reputation")){
                            ((ProgressBar)actr).setValue(lot.getReputation());
                        }
                        else if(actr.getName().equals("quality")){
                            ((ProgressBar)actr).setValue(lot.getQuality());
                        }
                        else if(actr.getName().equals("variety")){
                            ((ProgressBar)actr).setValue(lot.getVariety());
                        }
                        
                    }
                    
                }
                
            }
            
            return;
            
        }
        
        CustomLabel type = new CustomLabel(String.format(formattedMessages[3], lot.getTypeName())); //property major type. //receives bonus in this type of by walker visit default 10%, main type 33%.
        CustomLabel price = new CustomLabel(String.format(formattedMessages[4], lot.getPrice())); //property price (Price: );
        //CustomLabel reputation = new CustomLabel(String.format(formattedMessages[7], lot.getReputation())); //property reputation (Reputation: );
        CustomLabel interior = new CustomLabel(String.format(formattedMessages[8], lot.getPopulation().size(), lot.getInteriorSize())); //property interior size (Interior Size: );
        CustomLabel depot = new CustomLabel(String.format(formattedMessages[9], lot.getProductList().size(), lot.getDepotSize())); //property deposit size (Deposit Size: );
        //CustomLabel quality = new CustomLabel(String.format(formattedMessages[10], lot.getQuality())); //property product quality (Products Quality: );
        //CustomLabel variety = new CustomLabel(String.format(formattedMessages[11], lot.getVariety())); //property product variety (Products Variety: );
        CustomLabel reputation = new CustomLabel(messages[20]);
        CustomLabel quality = new CustomLabel(messages[23]);
        CustomLabel variety = new CustomLabel(messages[24]);
        CustomLabel status;
        
        
        type.setToolTip(messages[30]);
        type.setName("type");
        
        price.setToolTip(messages[31]);
        price.setName("price");
        
        reputation.setToolTip(messages[32]); //explaination about reputation attribute.
        
        interior.setToolTip(messages[33]);
        interior.setName("interior");
        
        depot.setToolTip(messages[34]);
        depot.setName("depot");
        
        quality.setToolTip(messages[35]);
        
        variety.setToolTip(messages[36]);
        
        if(lot.getStatus().equals(Type_Status.STATUS_SELLING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[25]), messages[47]);
        }
        else if(lot.getStatus().equals(Type_Status.STATUS_HIRING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[26]), messages[47]);
        }
        else if(lot.getStatus().equals(Type_Status.STATUS_BUYING)){
            status = new CustomLabel(String.format(formattedMessages[6], messages[27]), messages[47]);
        }
        else{
            status = new CustomLabel(String.format(formattedMessages[6], messages[28]), messages[47]);
        }
        
        status.setName("status");
        
        //test pourposes
        //SelectBox<String> selectBox3 = new SelectBox(new CustomSelectBoxStyle());
        //SelectBox<String> selectBox = new SelectBox(new SelectBoxStyle()); 
        //selectBox3.setItems("XYZ","ABC","PQR","LMN");

        Button sellButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png")); //Sell lot (put to sell);
        Button listOffersButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button upgradeButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button buildComercialButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button buildResidentialButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button productButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button tenantButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        
        sellButton.add(new CustomLabel(messages[52], messages[53], BaseGame.getLabelButton()));
        listOffersButton.add(new CustomLabel(messages[63], messages[64], BaseGame.getLabelButton()));
        upgradeButton.add(new CustomLabel(messages[71], messages[72], BaseGame.getLabelButton()));
        productButton.add(new CustomLabel(messages[81], messages[82], BaseGame.getLabelButton()));
        tenantButton.add(new CustomLabel(messages[83], messages[84], BaseGame.getLabelButton()));
       
        listOffersButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                state = 2;
                resetNavigationDialog();
                
            }

            return false;

        });
        
        upgradeButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                state = 3;
                resetNavigationDialog();
              
            }

            return false;

        });
     
        productButton.addListener((Event e) -> {
        
            if(screen.isTouchDownEvent(e)){
                
                state = 4;
                resetNavigationDialog();
                
            }
        
            return false;
        
        });
        
        tenantButton.addListener((EventListener)productButton.getListeners().get(0));
        
        sellButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                if(!lot.getStatus().equals(Type_Status.STATUS_SELLING)){
                    
                    int value = 0;
                    
                    if(!lot.getOffers().isEmpty()){
                        value = (int)lot.getHighestOffer().getValue();
                    }
                    
                    if(lot.addSellStatus()){
                        
                        decisionModal.informationEvent(lot, 0, value);
                        
                        screen.getPlayer().exitLot();
                        disableDialog();
                        
                    }
                    else{
                        castMessage(messages[57], Color.WHITE);
                    }
                    
                }
                else{
                    
                    lot.removeStatus();
                    castMessage(messages[58], Color.YELLOW);
                    
                }
                
                resetNavigationDialog();
                
            }

            return false;

        });
        
        containerTable.add(textureLot).width(navigationTable.getWidth() / 2).height(navigationTable.getWidth() / 2).center().colspan(2);
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(type).left().padLeft(10);
        containerTable.row().expand();
        
        if(!lot.getType().equals(Type_Lot.LOT_BUILDING)){
            
            if(!lot.getType().equals(Type_Lot.LOT_HOUSE) && !lot.getType().equals(Type_Lot.LOT_STORE)){
            
                buildComercialButton.add(new CustomLabel(messages[73], messages[74], BaseGame.getLabelButton()));
                buildResidentialButton.add(new CustomLabel(messages[75], messages[76], BaseGame.getLabelButton()));
                
                buildComercialButton.setName("6");
                buildResidentialButton.setName("7");
                
                containerTable.add(buildComercialButton).left();
                containerTable.row().expand();
                containerTable.add(buildResidentialButton).left();
                
            }
            else{
                
                if(lot.getType().equals(Type_Lot.LOT_HOUSE)){
                    
                    buildComercialButton.add(new CustomLabel(messages[79], messages[80], BaseGame.getLabelButton()));
                    buildComercialButton.setName("8");
                    
                    containerTable.add(buildComercialButton).center().expand();
                    containerTable.row().expand();
                    containerTable.add(tenantButton).center();
                    
                }
                else{
                    
                    buildResidentialButton.add(new CustomLabel(messages[77], messages[78], BaseGame.getLabelButton()));
                    buildResidentialButton.setName("9");
                    
                    containerTable.add(buildResidentialButton).center().expand();
                    containerTable.row().expand();
                    containerTable.add(productButton).center();
                    
                }
                
            }
           
        }
        
        buildComercialButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                getModal().upgradeEvent(lot, Integer.valueOf(e.getListenerActor().getName()));
                
                resetNavigationDialog();
                
                disableDialog();
                
            }

            return false;

        });
        
        buildResidentialButton.addListener((Event e) -> {

            if(screen.isTouchDownEvent(e)){
                
                getModal().upgradeEvent(lot, Integer.valueOf(e.getListenerActor().getName()));
                
                resetNavigationDialog();
                
                disableDialog();
                
            }

            return false;

        });
        
        containerTable.row().expand();
        containerTable.add(status).left().padLeft(10); //status (Selling, buying[only for rivals], hiring[employees] and default;
        containerTable.row().expand();
        containerTable.add(price).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(new CustomLabel(messages[55], messages[56])).center().colspan(2).expand(); //Characteristics 
        containerTable.row().expand();
        containerTable.add(new CustomLabel(""));
        containerTable.row().expand();
        containerTable.add(reputation).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("reputation", lot.getReputation(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(interior).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(depot).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(quality).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("quality", lot.getQuality(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(variety).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(getProgressBarInfo("variety", lot.getVariety(), 1.0f, false)).left().padLeft(10);
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        
//        else if(lot.getStatus().equals(Type_Status.STATUS_HIRING)){
//            sellButton.getButtonLabel().setText("CANCELAR CONTRATAÇÕES"); //cancel status hiring;
//        }
        
        if(!lot.getStatus().equals(Type_Status.STATUS_SELLING)){
            
            containerTable.row().expand();
            containerTable.add(sellButton).left().expand();
            
        }
        else{
    
            sellButton.clearChildren();
            sellButton.add(new CustomLabel("CANCELAR ORDEM DE VENDA", messages[54], BaseGame.getLabelButton())); //cancel status selling;
            
            containerTable.row().expand();
            containerTable.add(sellButton).left().expand();

        }
        
        containerTable.row().expand();
        containerTable.add(listOffersButton).left().colspan(2).expand();
        containerTable.row().expand();
        containerTable.add(upgradeButton).left().colspan(2).expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(backButton).center().colspan(2).expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
   
        update = true;
        
    }
    
    public void listNavigation(Lot lot){
        
        containerTable.clearChildren();
//        if(update){
//
//  
//            
//            for(Actor actr : containerTable.getChildren()){
//  
//                if(actr.getName() != null){
//                    
//                    if(actr.getName().equals("name")){
//                        actr.remove();
//                    }
//                    else if(actr.getName().equals("value")){
//                        actr.remove();
//                    }
//                    else if(actr.getName().equals("backButton")){
//                        endList = actr;
//                    }
//                    
//                }
//                
//            }
//            
//            ArrayList<Entry> entries = lot.getOffers();
//        
//            if(!entries.isEmpty()){
//
//                for(Entry entry : entries){
//
//                    Persona persona = (Persona)entry.getKey();
//                    int value = (int)entry.getValue();
//
//                    CustomLabel personaName = new CustomLabel(persona.getPersonaNameLimit(10), persona.getPersonaName());
//                    personaName.setName("name");
//                    CustomLabel personaValue = new CustomLabel("$".concat(String.valueOf(value)));
//                    personaName.setName("value");
//
//                    containerTable.add(personaName).center().expand();
//                    containerTable.add(personaValue).center().expand();
//                    containerTable.row().expand();
//
//                }
//
//            }
//            else{
//            
//                CustomLabel emptyOffer = new CustomLabel(messages[59]);
//                emptyOffer.setName("name");
//                
//                containerTable.addActorBefore(emptyOffer, endList);
//                
//            }
//            
//            return;
//            
//        }
//        
        CustomLabel description = new CustomLabel(messages[60]);
        CustomLabel personaHeader = new CustomLabel(messages[61]);
        //CustomLabel valueHeader = new CustomLabel(messages[62]);
      
        containerTable.add(textureLot).width(navigationTable.getWidth() / 2).height(navigationTable.getWidth() / 2).center().colspan(4);
        containerTable.row().expand();
        containerTable.add(description).center().expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(personaHeader).center();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        
        ArrayList<Entry> entries = lot.getOffers();
        
        if(!entries.isEmpty()){
            
            for(Entry entry : entries){
                
                Persona persona = (Persona)entry.getKey();
                int value = (int)entry.getValue();
                
                CustomLabel personaName = new CustomLabel(persona.getPersonaNameLimit(10), persona.getPersonaName());
                personaName.setName("name");
                CustomLabel personaValue = new CustomLabel("$".concat(String.valueOf(value)));
                personaName.setName("value");
                
                containerTable.add(personaName);
                containerTable.add(personaValue);
                containerTable.row().expand();
                
            }
            
        }
        else{
            
            CustomLabel emptyOffer = new CustomLabel(messages[59]);
            emptyOffer.setName("name");
            
            containerTable.add(emptyOffer);
            
        }
        
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(backButton).colspan(2).center().expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        
        update = true;
        
    }
    
    public void upgradeNavigation(Lot lot){
        
        CustomLabel informationLabel = new CustomLabel(messages[71]);
        CustomLabel interiorLabel = null;
        CustomLabel depotLabel = null;
        CustomLabel exteriorLabel = null;
        CustomLabel employeeTrainingLabel = null;
        CustomLabel customerServiceLabel = null;
        CustomLabel providerLabel = null;
        
        Button interiorButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button depotButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button exteriorButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button employeeTrainingButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button customerServiceButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));
        Button providerButton = new Button(new CustomTextButtonStyle("customhierofont.fnt", "button.png"));

        Button[] buttons = {interiorButton, depotButton, exteriorButton, employeeTrainingButton, customerServiceButton, providerButton};
        CustomLabel[] labels = {interiorLabel, depotLabel, exteriorLabel, employeeTrainingLabel, customerServiceLabel, providerLabel};
        
        containerTable.add(textureLot).width(navigationTable.getWidth() / 2).height(navigationTable.getWidth() / 2).center().colspan(2);
        containerTable.row().expand();
        containerTable.add(informationLabel).colspan(2).center().expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        
        for(int i = 0; i < 6; i++){
            
            buttons[i].setName(String.valueOf(i));
            
            if(lot.getUpgrade().getLevelByCode(i) < 10){
            
                buttons[i].add(new CustomLabel("+", BaseGame.getLabelButton()));
                labels[i] = new CustomLabel(messages[65 + i], String.format(formattedMessages[12], messages[65 + i], lot.getUpgrade().getLevelByCode(i)));
                
                buttons[i].addListener((Event e) -> {

                    if(screen.isTouchDownEvent(e)){
                            
                        if( lot.getUpgrade().getLevelByCode(Integer.valueOf(e.getListenerActor().getName())) < 10){
                            getModal().upgradeEvent(lot, Integer.valueOf(e.getListenerActor().getName()));
                        }
                        
                        containerTable.clearChildren();
                        upgradeNavigation(lot);

                    }
                   
                    return false;

                });
                
            }
            else{
                
                buttons[i].add(new CustomLabel(" ", BaseGame.getLabelButton()));
                labels[i] = new CustomLabel(messages[65 + i], String.format(formattedMessages[13], messages[65 + i]));
                
                buttons[i].addListener((Event e) -> {

                    if(screen.isTouchDownEvent(e)){
                        castMessage(String.format(formattedMessages[13], 
                                messages[65 + Integer.valueOf(e.getListenerActor().getName())]), Color.YELLOW);
                    }

                    return false;

                });
                
            }
            
        if(lot.getType().equals(Type_Lot.LOT_HOUSE) || lot.getType().equals(Type_Lot.LOT_STORE)){    
            containerTable.add(buttons[i]).width(35).height(35).center().expand();
        }
        else{
            containerTable.add(new CustomLabel(""));
        }
        
        containerTable.add(labels[i]).left().padRight(45).expand();
        containerTable.row().expand();
            
        }
        
        containerTable.add(new CustomLabel("")).center().expand();
        containerTable.row().expand();
        containerTable.add(backButton).colspan(2).center().expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        
    }
    
    public void productNavigation(Lot lot){
    
        CustomLabel informationLabel = new CustomLabel(messages[85]);
        CustomLabel remainingSpace = new CustomLabel(messages[93]);
        CustomLabel totalCost = new CustomLabel(messages[94]);
        CustomTextButton buyProd = new CustomTextButton("Compra Produto");
        CustomTextButton changePrice = new CustomTextButton("Trocar Preço");
        
        ArrayList<Button> buttons = new ArrayList();
        
        containerTable.row().expand();
        containerTable.add(informationLabel).colspan(4).center().expand(); //one colspan counts for whole table.
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(remainingSpace).center().colspan(2);
        containerTable.add(new CustomLabel(String.valueOf(lot.getDepotSize()))).center().colspan(2);
        containerTable.row().expand();
        containerTable.add(totalCost).center().colspan(2);
        containerTable.add(new CustomLabel(String.valueOf(lot.getCost()))).center().colspan(2);
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row().expand();
        containerTable.add(new CustomLabel(messages[19], messages[89])).left().expand();
        containerTable.add(new CustomLabel(messages[86], messages[90])).left().expand();
        containerTable.add(new CustomLabel(messages[87], messages[91])).left().expand();
        containerTable.add(new CustomLabel(messages[88], messages[92])).left().expand();
        containerTable.row().expand();
        
        for(Product product : lot.getProductList()){
            
            Button button = new Button(new CustomTextureButtonStyle(product.getTextureByName()));
            buttons.add(button);
            button.add(new CustomLabel("          \n          ", product.getTypeByName()));
 
            button.addListener((Event e) -> {

                if(screen.isTouchDownEvent(e)){

                    if(selectProduct != null && selectProduct == product){
                        return false;
                    }
                    
                    for(Button btn : buttons){
                        btn.clearActions();
                    }
                    
                    selectProduct = product;
                    button.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.color(Color.GOLD, 0.5f))));
                    updateTimer = updateTimerSpan+1;
                    
                }

                return false;

            });
            
            containerTable.add(button).left().expand();
            containerTable.add(new CustomLabel(String.valueOf(product.getQuantity()))).left().expand();
            containerTable.add(new CustomLabel(String.valueOf(product.getSize() * product.getQuantity()))).left().expand();
            containerTable.add(new CustomLabel(String.valueOf(product.getCost() * product.getQuantity()))).left().expand();
            containerTable.row().expand();
            
        }

        TextField valueField = new TextField(String.valueOf(0), new CustomTextFieldStyle());
        valueField.setMaxLength(4);

        TextFieldFilter text = (TextField arg0, char arg1) -> Character.toString(arg1).matches("^[0-9]");

        valueField.setTextFieldFilter(text);

        containerTable.add(new CustomLabel("Preço:")).left();
        containerTable.add(valueField).width(60).left();

        if(selectProduct == null){
            
            valueField.setText("");
            valueField.setDisabled(true);
            
        }
        else{
            
            valueField.setDisabled(false);
            valueField.setText(String.valueOf(selectProduct.getPrice()));
            
        }

        containerTable.row().expand();
        containerTable.add(changePrice).colspan(4).expand().center();
        containerTable.row().expand();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row();
        containerTable.add(backButton).colspan(4).center().expand();
        containerTable.row();
        containerTable.add(new CustomLabel("")).expand();
        containerTable.row();
    
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        updateTimer += dt;
        
        if(actor != null && (updateTimer > updateTimerSpan || !showing)){
            
            updateTimer = 0;
            
            if(!showing){
                
                containerTable.clearChildren();
                
                navigationBox.addAction(Actions.fadeIn(0.5f));
                
                showing = true;
                
                navigation();
                
            }
            else{
                
                if(!update){
                    containerTable.clearChildren();
                }
                
                if(actor instanceof Persona){
                    update((Persona)actor);
                }
                else if(actor instanceof Lot){
                    
                    if(state == 0){
                        update((Lot)actor);
                    }
                    else if(state == 1){
                        updateNavigation((Lot)actor);
                    }
                    else if(state == 2){
                        listNavigation((Lot)actor);
                    }
                    else if(state == 3){
                        upgradeNavigation((Lot)actor);
                    }
                    else if(state == 4 ){

                        if(((Lot)actor).getType().equals(Type_Lot.LOT_STORE)){
                            productNavigation((Lot)actor);
                        }
                        else{
                            //residentNavigation((Lot)actor);
                        }

                    }
                    
                }
                else{
                    throw new IllegalArgumentException("Actor not found in navigation context!");
                }
                
            }
            
        }
        
//        if(actor != null && (updateTimer > updateTimerSpan || !showing)){
//
//            updateTimer = 0;
//            
//            if(actor instanceof Persona || actor instanceof Lot){
//            
//                if(!showing || screen.getPlayer().isInsideLot()){
//                   
//                    containerTable.clearChildren();
//        
//                    if(!showing){
//
//                        navigationBox.addAction(Actions.fadeIn(0.5f));
//
//                        showing = true;
//                        navigation();
//
//                    }
//                    else{
//
//                        if(actor instanceof Persona){
//                            update((Persona)actor);
//                        }
//                        else{
//                            update((Lot)actor);
//                        }
//
//                    }
//                    
//                }
//                
//            }
//  
//        }
        
        if(showing && navigationBox.getY() > 3 
            && !messageBox.isVisible()){
            navigationBox.setY(3);
        }
        else if (showing && navigationBox.getY() > 3 && messageBox.isVisible()){
            navigationBox.setY(45);
        }

        if(decisionModal.isFinishedModalEvent()){
         
            castMessage(decisionModal.getOutcome(), decisionModal.getOutcomeColor());
            decisionModal.resetModal();
           
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) 
                && showing 
                && !getModal().isShowing()){
            
            InputEvent input = new InputEvent();
            input.setType(InputEvent.Type.touchDown);
            input.setTarget(cancelButton);
            cancelButton.fire(input);
            
        }
        
    }
    
}
