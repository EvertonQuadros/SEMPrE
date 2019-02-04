/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.sempre.examples;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Not275
 */
public class ExemploTeste extends Game{

    private Texture texture;
    private SpriteBatch batch;
    
    @Override
    public void create() {

        //texture = new Texture("logo_red.png"); 
        batch = new SpriteBatch();
       
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("logo_red.png"));
        Pixmap pixmap100 = new Pixmap(640, 480, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                            0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                            0, 0, pixmap100.getWidth(), pixmap100.getHeight());
        
        texture = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();
        
    }
    
    @Override
    public void render(){
        
        Gdx.gl.glClearColor(1, 1, 1, 1); //(red, green, blue, alpha)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        
    }
}
