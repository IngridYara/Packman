package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ComidaEspecial extends Comida {
    
    private Texture comidinhaEspecial_imagem;
    private Rectangle comidinha;

    public ComidaEspecial(float x, float y) {

        super((int) x, (int) y);
        comidinha = new Rectangle();
    }

    public void create() {
        comidinhaEspecial_imagem = new Texture(Gdx.files.internal("comidinha_especial.png"));
    }

    public void draw(SpriteBatch batch) {

        batch.draw(comidinhaEspecial_imagem, 600, 600);
    }

    public void spaw_comidinha() {
        
        comidinha.x = 600;
        comidinha.y = 600;
        comidinha.width = 64;
        comidinha.height = 64;
    }

    public Rectangle getComidinha() {
        return comidinha;
    }
    
    public void dispose() {
        
        comidinhaEspecial_imagem.dispose();
    }
}