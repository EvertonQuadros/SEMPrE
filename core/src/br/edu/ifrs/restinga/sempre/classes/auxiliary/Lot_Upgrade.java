/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.auxiliary;

/**
 *
 * @author Not275
 */
public class Lot_Upgrade {
    
    //verificar o preço de cada upgrade (aumenta de acordo com o número de upgrades e do valor do upgrade atual;
    //métodos para calcular o impacto do upgrade nos valores atuais de cada característica;
    
    private int interior = 1; //impacts in minimal perception of service;
    private int depot = 1; //impacts in variety and number os products;
    private int exterior = 1; //impacts in minimal reputation;
    private int employeeTraining = 1; //impacts in minimal perception of service; 
    private int customerService = 1; //impacts in minimal reputation;
    private int provider = 1; //impacts in variety and quality;

    //private int totalCost; //structure cost (more structure = more cost);
    
    public Lot_Upgrade(){}
    
    public Lot_Upgrade(int interior, int depot, int exterior, int employeeTraining, int customerService, int provider){
        init(interior, depot, exterior, employeeTraining, customerService, provider);
    }
    
    private void init(int interior, int depot, int exterior, int employeeTraining, int customerService, int provider){
        
        setInterior(interior);
        setDepot(depot);
        setExterior(exterior);
        setEmployeeTraining(employeeTraining);
        setCustomerService(customerService);
        setProvider(provider);
        
    }
    
    private int checkValue(int value){
        
        if(value <= 0){
            value = 1;
        }
        else if(value > 10){
            value = 10;
        }
        
        return value; 
        
    }
    
    public int getTotalCost(){
        
        int cost = 0;
        
        cost += getInterior() * 250; //2500
        cost += getDepot() * 100; //1000
        cost += getExterior() * 100; //1000
        cost += getEmployeeTraining() * 200; //2000
        cost += getCustomerService() * 100; //1000
        cost += getProvider() * 500; //5000
        
        return cost; //max 12500 
        
    }
    
    public int getInterior() {
        return interior;
    }
    
    public int getInteriorSize(){ //interior stands for space for personas inside the lot (owner doesnt count to this limit); //Base = 5 + interior level * 2; 25 max.
        return (5 + (2 * interior));
    }
    
    public void setInterior(int interior) {
        this.interior = checkValue(interior);
    }

    public int getInteriorValue(){
        return ((interior + 1) * 2500);
    }
    
    public int getDepot() {
        return depot;
    }

    public int getDepotSize(){ //deposit size stands for space for products in the lot. Base = 50 + 10 * depot level
        return (50 + (10 * depot)); 
    }
    
    public void setDepot(int depot) {
        this.depot = checkValue(depot);
    }
    
    public int getDepotValue(){
        return ((depot + 1) * 1000);
    }

    public int getExterior() {
        return exterior;
    }
    
    public float getExteriorSize(){ //exterior level stands for attractibility for the lot. More likely to become a favorite lot for a walker (with reputation).
        return ((float)exterior * 0.3f) / 2f; //30% chance
    }

    public void setExterior(int exterior) {
        this.exterior = checkValue(exterior);
    }

    public int getExteriorValue(){
        return ((exterior + 1) * 1000);
    }
    
    public int getEmployeeTraining() {
        return employeeTraining;
    }
    
    public float getEmployeeTrainingSize() { //increases productivity gain and reduces moral loses for failing trades.
        return ((float)employeeTraining * 0.5f); //50% chance
    }

    public void setEmployeeTraining(int employeeTraining) {
        this.employeeTraining = checkValue(employeeTraining);
    }

    public int getEmployeeTrainingValue() {
        return ((employeeTraining + 1) * 2000);
    }
    
    public int getCustomerService() {
        return customerService;
    }
    
    public float getCustomerServiceSize() { //better chance of successful trades.
        return ((float)customerService * 0.5f); //50% chance
    }

    public int getCustomerServiceValue() {
        return ((customerService + 1) * 1000);
    }
    
    public void setCustomerService(int customerService) {
        this.customerService = checkValue(customerService);
    }

    public int getProvider() { //better quality, better trades (more profit);
        return provider;
    }
    
    public int getProviderValue(){
        return ((provider + 1) * 5000);
    }
    
    public void setProvider(int provider) {
        this.provider = checkValue(provider);
    }
    
    public Integer getValueByCode(int code){
        
        if(code == 0){
            return getInteriorValue();
        }
        else if(code == 1){
            return getDepotValue();
        }
        else if(code == 2){
            return getExteriorValue();
        }
        else if(code == 3){
            return getEmployeeTrainingValue();
        }
        else if(code == 4){
            return getCustomerServiceValue();
        }
        else if(code == 5){
            return getProviderValue();
        }
        
        return null;
        
    }
    
    public Integer getLevelByCode(int code){
        
        if(code == 0){
            return getInterior();
        }
        else if(code == 1){
            return getDepot();
        }
        else if(code == 2){
            return getExterior();
        }
        else if(code == 3){
            return getEmployeeTraining();
        }
        else if(code == 4){
            return getCustomerService();
        }
        else if(code == 5){
            return getProvider();
        }
        
        return null;
        
    }
    
    public boolean addUpgradeByCode(int code){
     
        if(code == 0){
            interior = checkValue(interior + 1);
        }
        else if(code == 1){
            depot = checkValue(depot + 1); 
        }
        else if(code == 2){
            exterior = checkValue(exterior + 1);
        }
        else if(code == 3){
            employeeTraining = checkValue(employeeTraining + 1);
        }
        else if(code == 4){
            customerService = checkValue(customerService + 1);
        }
        else if(code == 5){
            provider = checkValue(provider + 1);
        }
        else{
            return false;
        }
        
        return true;
        
    }
    
    public int getTotalValue(int modificator){
        return (getInterior() * (1000 * modificator) 
                + (getExterior() * (750 * modificator)) 
                + (getCustomerService() * (1000 * modificator))
                + (getDepot() * (500 * modificator))
                + (getEmployeeTraining() * (500 * modificator))
                + (getProvider() * (1000 * modificator)));
    }
    
}
