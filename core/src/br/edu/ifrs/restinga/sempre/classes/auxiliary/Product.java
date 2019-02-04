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
public class Product {
    
    private String path = "products".concat(System.getProperty("file.separator"));
    
    public enum Type_Product {PRODUCT_EMPTY, PRODUCT_JEWELLERY, 
                              PRODUCT_WEAPONS, PRODUCT_GROCERY, 
                              PRODUCT_FLORIST, PRODUCT_ELECTRONICS};
    private Type_Product type;
    private int price;
    private int cost; //cost per unit from provider (can change by event);
    private int size;
    private int quantity;
    
    public Product(Type_Product type, int price){
        
        this.type = type;

        init(price);
        
    }
    
    public Product(Type_Product type){
        
        this.type = type;
        init(0);
        
    }
    
    private void init(int price){ //default values;
        
        if(type.equals(Type_Product.PRODUCT_JEWELLERY)){
            
            size = 4;
            cost = 25;
            
        }
        else if(type.equals(Type_Product.PRODUCT_WEAPONS)){
            
            size = 10;
            cost = 50;
            
        }
        else if(type.equals(Type_Product.PRODUCT_GROCERY)){
            
            size = 6;
            cost = 8;
            
        }
        else if(type.equals(Type_Product.PRODUCT_FLORIST)){
            
            size = 4;
            cost = 5;
            
        }
        else if(type.equals(Type_Product.PRODUCT_ELECTRONICS)){ 
            
            size = 8;
            cost = 15;
            
        }
        else{
            size = 1;
            cost = 3; //empty spaces costs too.
        }
        
        quantity = 0;
        setPrice(price);
        
    }

    public Type_Product getType() {
        return type;
    }
    
    public String getTypeByName() {
        
        if(type.equals(Type_Product.PRODUCT_JEWELLERY)){
            return "Itens de joalheria"; //jewellery products;
        }
        else if(type.equals(Type_Product.PRODUCT_WEAPONS)){
            return "Itens militares"; //military products;
        }
        else if(type.equals(Type_Product.PRODUCT_GROCERY)){
            return "Itens alimentícios"; //food/grocery products;
        }
        else if(type.equals(Type_Product.PRODUCT_FLORIST)){
            return "Itens botânicos"; //florist products;
        }
        else if(type.equals(Type_Product.PRODUCT_ELECTRONICS)){
            return "Produtos eletrônicos"; //electronic products;
        }
        else{
            return "Espaço vazio";
        }
        
    }

    public String getTextureByName(){
        
        if(type.equals(Type_Product.PRODUCT_ELECTRONICS)){
            return path.concat("box_electronics.png");
        }
        else if(type.equals(Type_Product.PRODUCT_FLORIST)){
            return path.concat("box_flowers.png");
        }
        else if(type.equals(Type_Product.PRODUCT_GROCERY)){
            return path.concat("box_foods.png");
        }
        else if(type.equals(Type_Product.PRODUCT_JEWELLERY)){
            return path.concat("box_jewels.png");
        }
        else if(type.equals(Type_Product.PRODUCT_WEAPONS)){
            return path.concat("box_guns.png");
        }
        else{
            return path.concat("box_empty.png");
        }
        
    }
    
    public void setType(Type_Product type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        
        this.price = price;
        
        if(this.price <= 0){
            this.price = 1;
        }
        
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        
        this.cost = cost;
        
        if(this.cost <= 0){
            this.cost = 1;
        }
        
    }

    public int getSize() {
        return size;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity += quantity;
    }
    
}
