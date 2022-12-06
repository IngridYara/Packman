package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.audio.Sound;

import java.util.Iterator;

public class Comida extends ApplicationAdapter{
  
    private Texture comidinha_imagem;
    private int posicaoY;
    private int posicaoX;
    private long lastDropTime, TempoComidaEspecial;
    private Sound dropSound;
    private boolean comeu_comida_especial =false, finalizouEspecial = false; 

    public Comida(int posicaoX, int posicaoY) {
 
        this.posicaoY = posicaoY;
        this.posicaoX = posicaoX;    
    }

    public Comida(long lastDropTime) {
        this.lastDropTime = lastDropTime;
    }
   
    public void create(Array<Rectangle> comidinhas) {

        comidinha_imagem = new Texture(Gdx.files.internal("comidinha.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("assets/comeu.mp3"));
    }

    public void draw(Array<Rectangle> comidinhas, SpriteBatch batch) {

        for (Rectangle comidinha : comidinhas) {
            batch.draw(comidinha_imagem, comidinha.x, comidinha.y);
    }
}

    public void spaw_comidinha(Array<Rectangle> comidinhas) {
        
        Rectangle comidinha = new Rectangle();

        comidinha.x = posicaoX;
        comidinha.y = posicaoY;
        comidinha.height = 35;
        comidinha.width = 35;      
        comidinhas.add(comidinha);
        lastDropTime = TimeUtils.millis();
    }

    public void render(Array<Rectangle> comidinhas, Rectangle comida_especial, Rectangle dentadura_rectangle,Dentadura dentadura, boolean zera_pontuacao, long tempo_comidaEspecial) {
        
        Iterator<Rectangle> iter = comidinhas.iterator();

        while (iter.hasNext()) {

            Rectangle comidinha = iter.next();

            if (comidinha.overlaps(dentadura_rectangle)) {

                dentadura.pontos++;
                soundPlay();
                iter.remove();
            }
        }

        if (comeu_comida_especial) {
            
            if (TimeUtils.millis() - TempoComidaEspecial > 6000) {

                comeu_comida_especial = false;
                finalizouEspecial = true;
            }
        }

        if (TimeUtils.millis() - tempo_comidaEspecial > 10000) {
                       
            if (!comeu_comida_especial) {
                
                if (comida_especial.overlaps(dentadura_rectangle)) {
                    
                    comeu_comida_especial = true; 
                    TempoComidaEspecial = TimeUtils.millis();
                    soundPlay();
                }     
            }
        }

        if (zera_pontuacao) {

            dentadura.pontos = 0;
        }
    }
    
    public void soundPlay() {
        
        dropSound.play();
    }
    
    public void dispose() {

        comidinha_imagem.dispose();
        dropSound.dispose();
    }
    
    public boolean comeu_comida_especial_get() {
        return comeu_comida_especial;
    }

    public void setComeu_comida_especial(boolean comeu_comida_especial) {
        this.comeu_comida_especial = comeu_comida_especial;
    }
    
    public boolean getFinalizouEspecial() {
        return finalizouEspecial;
    }

    public void setFinalizouEspecial(boolean finalizouEspecial) {
        this.finalizouEspecial = finalizouEspecial;
    }
}
