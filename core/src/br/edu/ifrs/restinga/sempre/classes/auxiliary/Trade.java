/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.classes.auxiliary;

import br.edu.ifrs.restinga.sempre.classes.actors.Persona;

/**
 *
 * @author Not275
 */
public class Trade {
    
    private final Product product;
    private final String number;
    private final float satisfaction;
    
    public Trade(Product product, Persona persona){
        
        this.product = product;
        number = persona.getId();
        satisfaction = persona.getSatisfactionLevel();
        
    }

    public String getTradeId(){
        return number;
    }
    
    public String getProductName() {
        return product.getType().toString();
    }

    public int getProductQuantity() {
        return product.getQuantity();
    }
    
    public int totalValue(){
        return product.getPrice() * product.getQuantity();
    }

    public String getNumber() {
        return number;
    }

    public Product getProduct() {
        return product;
    }

    public float getSatisfaction() {
        return satisfaction;
    }
    
}
