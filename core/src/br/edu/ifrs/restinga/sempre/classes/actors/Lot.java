/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.actors;

import br.edu.ifrs.restinga.sempre.classes.auxiliary.Lot_Upgrade;

import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product;
import br.edu.ifrs.restinga.sempre.classes.auxiliary.Product.Type_Product;

import br.edu.ifrs.restinga.sempre.classes.auxiliary.Trade;

import br.edu.ifrs.restinga.sempre.classes.base.BaseActor;
import br.edu.ifrs.restinga.sempre.classes.base.BaseGame;

import br.edu.ifrs.restinga.sempre.classes.effects.Icon_Lot;
import br.edu.ifrs.restinga.sempre.classes.effects.Icon_Lot.Duration;
import br.edu.ifrs.restinga.sempre.classes.effects.Icon_Lot.Type;

import br.edu.ifrs.restinga.sempre.classes.entities.Lot_Waypoint;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.maps.MapProperties;

import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import java.util.Map.Entry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Not275
 */
public class Lot extends BaseActor{
    
    //main Variables-----------------------------;
    
    public enum Type_Lot {LOT_BUILDING, LOT_EMPTY, 
                          LOT_EMPTY_ABANDONED, LOT_HOUSE, LOT_STORE};
    
    public enum Type_Status {STATUS_DEFAULT, STATUS_SELLING, STATUS_HIRING, STATUS_BUYING};
    
    private int id;
    
    private String orientation;
    
    private Lot_Waypoint entrance;
    
    private float timer = 0f;
    private float upgradeTimer = 1f;
    private float expandTimer = 0f;
    
    private MapProperties properties;
    
    //Lot Characteristics-----------------------
    
    private boolean updated = false;
    private boolean building = false;
    
    private Type_Lot type;
    private Type_Lot expandType = null;
    private Type_Status status;
    private Lot_Upgrade upgrade = null; //upgrades table;
    private Persona owner = null;
    
    private ArrayList<Product> productList = new ArrayList(); //items to sell from comercial lot;
    private ArrayList<Persona> tenants = new ArrayList(); //tenants (Product) from residential lot;
    private ArrayList<Persona> employees = new ArrayList(); //add wage for employees.
    private CopyOnWriteArrayList<Persona> population = new CopyOnWriteArrayList(); //walkers inside the lot.
    private ArrayList<Trade> tradeList = new ArrayList(); //trade history of the lot;
    private ConcurrentHashMap<Persona, Integer> offers = new ConcurrentHashMap(); //buy offer; //can serve as audiction;
    
    private float reputation = 0;  // value of atrractivity of the lot, lesser values have worst trade chance and walker priority.
    private int interiorSize; //interior size, more space for employees (limit 3); //walker also ocuppies space.
    private int depotSize; //size of the depot, every product unit occupy a number of space units;
    private float quality;  //product quality, better products are more expensive (higher cost);
    private float variety; //product variety, more type of produts lesser the variety of one product;

    private int basePrice;
    private int totalPrice; //lot minimum value for selling.
    private int cost; //maintenance cost.
   
    
    //Auxiliary Variables-------------------------
    
    private Icon_Lot eventIcon = null;
    private Icon_Lot statusIcon = null;
    private Icon_Lot additionalIcon = null;
    
    private boolean selected = false;
    
    private String fileNameTexture;

    public Lot(MapProperties properties, Stage stage) {
        
        super(properties, stage);
        
        this.properties = properties;
        
        init();
        
    }
    
    private void init(){
        
        id = (int)properties.get("id");
        orientation = (String)properties.get("orientation");
        
        for(BaseActor actor : BaseActor.getList(this.getStage(), Lot_Waypoint.class)){
            
            Lot_Waypoint waypoint = (Lot_Waypoint)actor;
            
            if(waypoint.getId() == id){
            
                entrance = waypoint;
                break;
                
            }
            
        }
        
        status = Type_Status.STATUS_DEFAULT;
        
        if(id != BaseGame.cheapLot){
            type = Type_Lot.values()[MathUtils.random(1, Type_Lot.values().length - 1)];
        }
        else{
            type = Type_Lot.values()[2];
        }
        
        if(type.equals(Type_Lot.LOT_HOUSE) || type.equals(Type_Lot.LOT_STORE)){
    
            if(MathUtils.random(0, 10) < 8){
                type = Type_Lot.values()[MathUtils.random(1, 2)];
            }
            else{
                upgrade = new Lot_Upgrade(MathUtils.random(1, 3), 
                                          MathUtils.random(1, 3), 
                                          MathUtils.random(1, 3), 
                                          MathUtils.random(1, 3), 
                                          MathUtils.random(1, 3), 
                                          MathUtils.random(1, 3));
            }
            
        }
        
        if(upgrade == null){ 
            upgrade = new Lot_Upgrade();
        }
        
        loadTexture(type);
        
        //loadTexture("lots".concat(System.getProperty("file.separator")).concat("building_lot.png"));
        
//        ArrayList<BaseActor> personas = BaseActor.getList(getStage(), Persona.class);
//        
//        if(MathUtils.randomBoolean()){
//            owner = (Persona) personas.get(MathUtils.random(0, personas.size() - 1));
//        }
        updated = true;
        setPrice();
        checkOwner();
        
//        Product prod = productList.get(0);
//                
//        if(prod.getType().equals(Type_Product.PRODUCT_EMPTY)){
//            productList.remove(prod);
//        }

//        if(productList.isEmpty()){
//            
//            Type_Product[] type2 = {Type_Product.PRODUCT_EMPTY,
//                                    Type_Product.PRODUCT_ELECTRONICS, 
//                                    Type_Product.PRODUCT_FLORIST, 
//                                    Type_Product.PRODUCT_GROCERY, 
//                                    Type_Product.PRODUCT_JEWELLERY, 
//                                    Type_Product.PRODUCT_WEAPONS};
//
//            Product product = new Product(type2[MathUtils.random(0, type2.length - 1)]);
//
//            int quant = 0;
//            int limit = 10;
//
//            do{
//
//                quant = MathUtils.random(1, 2);
//
//                if(quant * product.getSize() > upgrade.getDepotSize()){
//                    limit--;
//                }
//
//            }while(quant * product.getSize() > upgrade.getDepotSize());
//
//            product.setQuantity(quant);
//
//            buyProductTest(product);
//            
//        }
//       
    }

    public void loadTexture(Type_Lot type){

        this.type = type;
        
        fileNameTexture = "lots".concat(System.getProperty("file.separator"));
        
        if(type.equals(Type_Lot.LOT_BUILDING)){
            fileNameTexture = fileNameTexture.concat("building_lot.png");
        }
        else if(type.equals(Type_Lot.LOT_EMPTY)){
            fileNameTexture = fileNameTexture.concat("empty_lot_2.png");
        }
        else if(type.equals(Type_Lot.LOT_EMPTY_ABANDONED)){
            fileNameTexture = fileNameTexture.concat("empty_lot_1.png");
        }
        else if(type.equals(Type_Lot.LOT_HOUSE)){
            
            if(MathUtils.randomBoolean()){
                fileNameTexture = fileNameTexture.concat("house_1.png");
            }
            else{
                fileNameTexture = fileNameTexture.concat("house_2.png");
            }
            
        }
        else{
            
            int textureType = MathUtils.random(1, 3);
            
            if(textureType == 1){
                fileNameTexture = fileNameTexture.concat("store_1.png");
            }
            else if(textureType == 2){
                fileNameTexture = fileNameTexture.concat("store_2.png");
            }
            else{
                fileNameTexture = fileNameTexture.concat("store_3.png");
            }
            
        }
        
        super.loadTexture(fileNameTexture);
        
        setWidth((float)properties.get("width") + 5); //5
        setHeight((float)properties.get("height") + 5); //10

        setBoundaryRectangle();
        
    }
    
    private void updateValues(){
        
        int value = getProductVarietyNumber();
        
        if(value == 0){
            setVariety(0f); //no variety at all!
        }
        else{
            
            float varietyTemp = (1f / value);

            setVariety(varietyTemp * ((upgrade.getProvider()  / 10f) + (upgrade.getDepot() / 10f)) / 2f);
          
        }

        if(reputation == 0){
            setReputation(0.5f);
        }
  
        
        setQuality(1f * (upgrade.getProvider() / 10f));
       
       // setDepotSize(1f * (upgrade.getProvider() / 10f));
        depotSize = upgrade.getDepotSize();

        float cap = 1f * (((upgrade.getCustomerService() / 10f) + (upgrade.getExterior() / 10f)) / 5f); //max 0.4f;
        
        if(cap > getReputation()){
            setReputation(cap);
        }
        
        //setInteriorSize(1f * ((upgrade.getInterior() / 10f))); // interiorSize <= 0.2 = 1 | >= 0.4 && < 0.6 = 2......
        interiorSize = upgrade.getInteriorSize();
        
        value = 0;
        cost = 0;
        
        for(Product product : getProductList()){
            
            value += product.getPrice() * product.getQuantity();
            cost += product.getCost() * product.getQuantity();
            
            if(!product.getType().equals(Product.Type_Product.PRODUCT_EMPTY)){
                depotSize -= product.getSize() * product.getQuantity();
            }
            
        }
        
        if(depotSize > 0){
          
            boolean empty = false;
            
            for(Product prod : getProductList()){
                
                if(prod.getType().equals(Type_Product.PRODUCT_EMPTY)){
                    empty = true;
                }
                
            }
            
            if(!empty){
                
                Product product = new Product(Type_Product.PRODUCT_EMPTY);
                product.setQuantity(depotSize);
            
                getProductList().add(product);
                
            }
                  
        }
        
        for(Persona persona : getEmployees()){
            
            value += persona.getSalary();
            cost += persona.getSalary();
            
        }
        
        if(getType().equals(Type_Lot.LOT_STORE)){
            value += getUpgrade().getTotalValue(2);
        }
        else{
            value += getUpgrade().getTotalValue(1);
        }
        
        setPrice(basePrice + value);
        
    }
    
    public int getProductVarietyNumber(){
        
        int value = 0;
        
        if(!getProductList().isEmpty()){
            
            ArrayList<Type_Product> products = new ArrayList();
        
            for(Product product : getProductList()){
                products.add(product.getType());
            }
            
            if(products.contains(Type_Product.PRODUCT_ELECTRONICS)){
                value++;
            }
            
            if(products.contains(Type_Product.PRODUCT_FLORIST)){
                value++;
            }
            
            if(products.contains(Type_Product.PRODUCT_GROCERY)){
                value++;
            }
            
            if(products.contains(Type_Product.PRODUCT_JEWELLERY)){
                value++;
            }
            
            if(products.contains(Type_Product.PRODUCT_WEAPONS)){
                value++;
            }
            
        }
        
        return value;
        
    }
    
    public float getPerceptionCap(){
        return 1 * (((upgrade.getEmployeeTraining() / 10) + (upgrade.getInterior() / 10)) / 5); //max 0.4f;
    }
    
    public void checkOwner(){
        
        if(owner != null){
        
            if(owner instanceof Player){
                setColor(Color.GREEN);
            }
            else{
                setColor(Color.RED);
            }
        
        }
        else{
            setColor(Color.WHITE);
        }
        
    }
    
    public Persona getOwner(){
        return owner;
    }

    public int getPrice() {
        return totalPrice;
    }
    
    public void setPrice(int price) {
        totalPrice = price;
    }

    private void setPrice(){
        
        if(type.equals(Type_Lot.LOT_EMPTY_ABANDONED)){
            basePrice = 1750 * MathUtils.random(1, 3);
        }
        else if(type.equals(Type_Lot.LOT_EMPTY)){
            basePrice = 2500 * MathUtils.random(3, 5);
        }
        else if(type.equals(Type_Lot.LOT_HOUSE)){
            basePrice = 5000 * MathUtils.random(3, 15) + getUpgrade().getTotalValue(1);
        }
        else if(type.equals(Type_Lot.LOT_STORE)){
            basePrice = 4000 * MathUtils.random(5, 10) + getUpgrade().getTotalValue(2);
        }
        
    }

    public ArrayList<Persona> getTenants() {
        return tenants;
    }

    public void setTenants(ArrayList<Persona> tenants) {
        this.tenants = tenants;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public ArrayList<Persona> getEmployees() {
        return employees;
    }

    public CopyOnWriteArrayList<Persona> getPopulation() { //population currently inside the lot;
        return population;
    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }

    public Lot_Upgrade getUpgrade() {
        return upgrade;
    }
    
    public Type_Lot getType() {
        return type;
    }
    
    public int getOfferSize(){
        return offers.size();
    }
    
    public boolean getPersonaOffer(Persona persona){
        
        for(Entry entry : offers.entrySet()){
            
            if(entry.getKey() == persona){
                return true;
            }
            
        }
        
        return false;
    }
    
    private boolean removeAllEmptySpaces(){
       
        for(Product product : getProductList()){
            
            if(product.getType().equals(Type_Product.PRODUCT_EMPTY)){
                
                depotSize += product.getQuantity();
                
                getProductList().remove(product);
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
//    public int buyOneProduct(Product product){
//        
//        int value = 1;
//        
//        removeAllEmptySpaces();
//            
//        if(depotSize - product.getSize() < 0){
//            value = -1;
//        }
//        
//        if(getOwner().getMoney() - product.getPrice() < 0){
//            value = 0;
//        }
//        
//        if(!getProductList().contains(product)){
//            getProductList().add(product);
//        }
//        else{
//            
//            for(Product aux : getProductList()){
//            
//                if(aux.getType().equals(product.getType())){
//
//                    aux.setQuantity(aux.getQuantity() + 1);
//                    break;
//
//                }
//
//            }
//            
//        }
//     
//        updated = true;
//        
//        return value;
//        
//    }
    
    public int buyProduct(Product product){
       
        if(depotSize - (product.getSize() * product.getQuantity()) < 0){
            return -1;
        }
        
        int price = product.getPrice() * product.getQuantity();
        
        if((getOwner().getMoney() - price) < 0){
            return 0;
        }
        else{
            getOwner().subMoney(price);
        }
        
        removeAllEmptySpaces();
        
        if(!getProductList().contains(product)){
            getProductList().add(product);
        }
        else{
            
            for(int i = 0; i < getProductList().size()-1 ; i++){
            
            Product aux = getProductList().get(i);
            
                if(aux.getType().equals(product.getType())){
                    
                    aux.setQuantity(aux.getQuantity() + product.getQuantity());
                    
                    break;
                    
                }
            
            }
            
        }
        
        updated = true;
        
        return 1; //-1 no space, 0 no money, 1 - ok
        
    }
 
    public boolean addOffer(Persona persona, int value){
        
       // boolean greaterValue = false;
        
        if(persona.equals(owner)){
            return false;
        }
        
//        for(Entry entry : offers.entrySet()){
//        
//            if((Integer)entry.getValue() > value){
//                
//                greaterValue = true;
//                break;
//                
//            }
//            
//        }
        
        //if(greaterValue){
        //    return false;
        //}
        //else{
           
            //offers = new HashMap();
            offers.put(persona, value);
            
            if(!status.equals(Lot.Type_Status.STATUS_BUYING)){
                addBuyingStatus();
            }
            
            return true;
            
       // }
        
    }
    
    public Integer removeOffer(Persona persona){
        
        Integer value = null;
        
        for(Entry entry : offers.entrySet()){
            
            if(((Persona)entry.getKey()).equals(persona)){
                
                persona.addMoney((int)entry.getValue());
                value = (int)entry.getValue();
                offers.remove(persona);
                
            }
            
        }
        
        if(offers.isEmpty()){
            removeAdditional();
        }
        
        return value;
        
    }
    
    public void clearOffers(){
        
        removeAdditional();
        
        if(!offers.isEmpty()){
            
            for(Entry entry : getOffers()){
                
                Persona persona = (Persona)entry.getKey();
                persona.addMoney((int)entry.getValue());
                
            }
            
        }
        
        offers.clear();
        
    }
    
    public Entry getHighestOffer(){
        
        Entry entry = null;
        
        for(Entry values : offers.entrySet()){
            
            if(entry == null || (int)values.getValue() > (int)entry.getValue()){
                entry = values;
            }
            
        }
        
        return entry;
        
    }
    
    public ArrayList<Entry> getOffers(){
        
        ArrayList value = new ArrayList();
        
        for(Entry entry : offers.entrySet()){
            value.add(entry);
        }
        
        return value;
        
    }
    
    public String getTypeName(){
        
        if(type.equals(Type_Lot.LOT_BUILDING)){
            return "CONSTRUÇÃO"; //building lot;
        }
        else if(type.equals(Type_Lot.LOT_EMPTY)){
            return "VAZIO"; //empty lot;
        }
        else if(type.equals(Type_Lot.LOT_EMPTY_ABANDONED)){
            return "BALDIO"; //empty lot abandoned;
        }
        else if(type.equals(Type_Lot.LOT_HOUSE)){
            return "RESIDENCIAL";
        }
        else if(type.equals(Type_Lot.LOT_STORE)){
            return "COMERCIAL";
        }
        
        return "...";
        
    }

    public Icon_Lot getStatusIcon() {
        return statusIcon;
    }
    
    public Icon_Lot getEventIcon() {
        return eventIcon;
    }
    
    public Icon_Lot getAdditionalIcon(){
        return additionalIcon;
    }

    public int getId() {
        return id;
    }

    public String getOrientation() {
        return orientation;
    }

    public float getReputation() {
        return reputation;
    }

    public void setReputation(float reputation) {
        this.reputation = reputation;
    }

    public int getInteriorSize() {
        return interiorSize;
    }

    public int getDepotSize() {
        return depotSize;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public float getVariety() {
        return variety;
    }

    public void setVariety(float variety) {
        
        if(variety < 0.1f){
            variety = 0.1f;
        }
        
        this.variety = variety;

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public String getTexture(){
        return fileNameTexture;
    }

    public Type_Status getStatus() {
        return status;
    }

    public Lot_Waypoint getEntrance() {
        return entrance;
    }

    public void select() {
        
        if(!isSelected()){
            
            addAction(Actions.forever(Actions.sequence(Actions.color(getColor(), 0.5f), Actions.color(Color.GOLD, 0.5f))));
            selected = true;
            
        }
        
    }
    
    public void deselect(){
        
        if(isSelected()){
            
            getActions().removeIndex(0);
            checkOwner();
            selected = false;
            
        }
        
    }

    public boolean isSelected() {
        return selected;
    }
    
    private void addEventIcon(Type type, Duration duration){
       
        eventIcon = new Icon_Lot(0f, 0f, getStage(), type, duration);
        eventIcon.centerAtActor(this);
        eventIcon.setPosition(eventIcon.getX() - 20, eventIcon.getY());
        
    }
    
    private void addAdditionalIcon(Type type, Duration duration){

        additionalIcon = new Icon_Lot(0f, 0f, getStage(), type, duration);
        additionalIcon.centerAtActor(this);
        additionalIcon.setPosition(additionalIcon.getX(), additionalIcon.getY() + 10);
        
    }
    
    private void addStatusIcon(Type type, Duration duration){
   
        statusIcon = new Icon_Lot(0f, 0f, getStage(), type, duration);
        statusIcon.centerAtActor(this);
        statusIcon.setPosition(statusIcon.getX() + 20, statusIcon.getY());

    }
    
    private void addTemporaryIcon(Type type, Duration duration){ //centered icon
        
        Icon_Lot temp = new Icon_Lot(0f, 0f, getStage(), type, duration);
        temp.centerAtActor(this);
        
    }
    
    public boolean sell(){

        Entry entry = getHighestOffer();
        
        Persona persona = (Persona)entry.getKey();
        int value = (int)entry.getValue();

        clearOffers();
        
        return buy(persona, value);

    }
  
    public boolean buy(Persona persona, int value){
     
        if(value == 0){
            value = getPrice();
        }
        
        if(persona.getMoney() >= getPrice()){

            if(getOwner() != null){
                
               getOwner().getLots().remove(this);
               getOwner().addMoney(value);
               
            }
            
            owner = persona;
            
            persona.getLots().add(this);
            persona.subMoney(value);
           
            checkOwner();
            
            if(getStatusIcon() != null 
                    || getAdditionalIcon() != null){
                
                removeStatus();
                removeAdditional();
                
            }
            
            updated = true;
            
            return true; //Lot acquired with success!;"Lote comprado com sucesso!";
            
        }
        else{
            return false; // "Não é possível comprar! Você não possui dinheiro suficiente!"; //You can't buy it! you dont have enough money!.
        }
        
    }

    public boolean buyUpgrade(int code){
        
        if(code >= 0 && code < 6){
            
            if(getOwner().getMoney() >= getUpgrade().getValueByCode(code)){
      
                if(getUpgrade().addUpgradeByCode(code) 
                        && getUpgrade().getLevelByCode(code) <= 10){
                  
                    getOwner().subMoney(getUpgrade().getValueByCode(code));
                    
                    //updateValues();
                    addUpgradeIcon();
                    
                    return true;
                    
                }
                
            }
            
        }
        else{
            throw new IllegalArgumentException("Unable to find upgrade code = " + code);
        }
        
        return false; //no money to buy this upgrade.
        
    }
    
    public boolean addSellStatus(){
             
        if(!getOffers().isEmpty()){
            return sell();
        }
        else{
            
            if(!status.equals(Type_Status.STATUS_DEFAULT)){
                removeStatus();        
            }
   
            status = Type_Status.STATUS_SELLING;
            addStatusIcon(Type.ICON_SELL, Duration.FOREVER);
            updated = true;
            
        }
        
        return false;
        
    }
    
    public void addHiringStatus(){
        
        if(!status.equals(Type_Status.STATUS_DEFAULT)){
            removeStatus();
        }
        
        status = Type_Status.STATUS_HIRING;
        addStatusIcon(Type.ICON_HIRE, Duration.FOREVER);
        updated = true;
        
    }
    
    public void addBuyingStatus(){
  
        if(status.equals(Type_Status.STATUS_SELLING)){
            sell();
        }
        else{
            
            status = Type_Status.STATUS_BUYING;
            addAdditionalIcon(Type.ICON_BUYING, Duration.FOREVER);
            updated = true;
        
        }
        
    }
    
    public void addUpgradeIcon(){
        
        addTemporaryIcon(Type.ICON_UPGRADE, Duration.EXPAND);
        updated = true;
   
    }
    
    public void removeStatus(){
        removeIcon(statusIcon);
    }
    
    public void removeEvent(){
        removeIcon(eventIcon);
    }
    
    public void removeAdditional(){
        removeIcon(additionalIcon);
    }
    
    private void removeIcon(Icon_Lot icon){
        
        status = Type_Status.STATUS_DEFAULT;
        
        if(icon != null 
                && icon.getActions().size > 0){
            icon.removeIcon();
        }

        updated = true;
        
    }
    
    public boolean buildUpgrade(int cod){ //cod 0 = (expand comercial) 1 = (expand residential), 2= (change comercial), 3 = (change residential);
        
        if(cod < 0 || cod > 3){ 
            throw new IllegalArgumentException("Invalid cod!"); 
        }
        else if(type.equals(Type_Lot.LOT_BUILDING)){
            throw new IllegalArgumentException("Building a upgrade on top of another building!"); //if try to build a upgrade on top of another building.
        }
        
        int value = 0;
        
        if(type.equals(Type_Lot.LOT_EMPTY) 
                || type.equals(Type_Lot.LOT_EMPTY_ABANDONED)){
            
            if(cod == 0){
                
                value = (getPrice() / 10) + 1000;
                expandType = Type_Lot.LOT_STORE;
                
            }
            else{
            
                value = (getPrice() / 8) + 3000;
                expandType = Type_Lot.LOT_HOUSE;
            
            }
      
        }
        else if (type.equals(Type_Lot.LOT_HOUSE) && cod == 2){
            
            value = (getPrice() / 8) + 3000;
            expandType = Type_Lot.LOT_STORE;
            
        }
        else if (type.equals(Type_Lot.LOT_STORE) && cod == 3){
            
            value = (getPrice() / 5) + 5000;
            expandType = Type_Lot.LOT_HOUSE;
            
        }
       
        if(getOwner().getMoney() >= value){
            
            getOwner().subMoney(value);
            clearAnimation();
            
            if(cod == 0 || cod == 1){
                basePrice += value;
            }
            else{
                
                if(cod == 2){
                    getTenants().clear(); //kick out (let out) all tenants (walkers);
                }
                else{
                    getProductList().clear(); //remove all items.
                }
                
            }
            
            loadTexture(Type_Lot.LOT_BUILDING);
            
            getOwner().exitLot();
            
            for(Persona persona : getPopulation()){
                persona.exitLot();
            }
            
            updateValues();
            
            return true;
            
        }
  
        return false;
        
    }
    
    @Override
    public void act(float dt) {
        
        super.act(dt); //To change body of generated methods, choose Tools | Templates.
        
        timer += dt;
        if(timer > upgradeTimer){
            
            timer = 0;
            
            depotSize = MathUtils.random(10, 150);
            
        }
        
        if(updated){ //any change of variables, updateValues() is called from this lot;
            
            updateValues();
            updated = !updated;
            
        }
        
        if(expandType != null){
            
            expandTimer += dt;
            
            if(expandTimer > MathUtils.random(1, 3)){ //10, 30 default values
                
                expandTimer = 0;
                clearAnimation();
                
                loadTexture(expandType);
                
                expandType = null;
            
            }
            
        }
    
//        timer += dt;
        
//        if(timer > 3f){
//        
//            timer = 0f;
//            
//            System.out.printf("\nID: " + getId());
//            
//            for(Entry entry : getOffers()){       
//                System.out.printf("\nPERSONA: " + ((Persona)entry.getKey()).getPersonaName() + "\t VALUE: " + (int)entry.getValue());
//            }
//
//            System.out.printf("\n--------------------");
//            
//        }
        //CopyOnWriteArrayList
        
//        Iterator it = getOffers().iterator();
//
//        while(it.hasNext()){
//
//            for(Entry entry : ((Lot)it.next()).getOffers()){
//
//                System.out.printf("\nID: " + getId());
//                System.out.printf("\nPERSONA: " + ((Persona)entry.getKey()).getPersonaName() + "\t VALUE: " + (int)entry.getValue());
//
//            }
//
//        }
        
    }
  
}
