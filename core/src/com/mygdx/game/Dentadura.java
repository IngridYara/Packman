package com.mygdx.game;

import java.util.Iterator;
import java.lang.Thread;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Dentadura extends ApplicationAdapter{

    private Texture dentadura_img;
    private Texture vida_img;

    Rectangle dentadura = new Rectangle();

    static Boolean Morre = false;
    static Boolean Perdevida = false;

    private long Tempo_PosVida;

    public int pontos = 0, posicao_vidaX, posicao_vidaY;

    public Dentadura(long Tempo_PosVida) {

        this.Tempo_PosVida = Tempo_PosVida;
    }

    public Dentadura(int posicao_vidaX, int posicao_vidaY) {

        this.posicao_vidaX = posicao_vidaX;
        this.posicao_vidaY = posicao_vidaY;
    }
    
    public Dentadura() {

    }

    public void create() {

        //imagem escolhida do pack man
        dentadura_img = new Texture(Gdx.files.internal("packfofo.png"));
        vida_img = new Texture(Gdx.files.internal("vida.png"));    
    }          
    
    public void draw(SpriteBatch batch) {

        if (Morre) {

            Perdevida = Morre;
            dentadura.x = 1500;
            dentadura.y = 1500;
            
            // check if we need to create a new raindrop
            if (TimeUtils.millis() - Tempo_PosVida > 9000) {
             
                spawnRaindrop();
                Morre = false;  
                Tempo_PosVida = TimeUtils.millis() ;                
            }
        }    
       
        batch.draw(dentadura_img, dentadura.x, dentadura.y);
    }

    public void drawVidas(SpriteBatch batch, Array<Rectangle> vidadrops) {

        Iterator<Rectangle> iter = vidadrops.iterator();

        while (iter.hasNext()) {
            
            Rectangle vidadrop = iter.next();   
            batch.draw(vida_img, vidadrop.x, vidadrop.y);
        }
    }

    public Rectangle spawnRaindrop() {

        dentadura.x = 570;
        dentadura.y = 570;
        dentadura.width = 70;
        dentadura.height = 70;
    
        return dentadura;
    }

    public void spawVidas(Array<Rectangle> vidadrops) {
        
        Rectangle vidadrop = new Rectangle();

        vidadrop.x = posicao_vidaX  * 10;
        vidadrop.y = posicao_vidaY;
        vidadrop.width = 10; 
        vidadrop.height = 10;

        vidadrops.add(vidadrop);
    }

    public void move() {

        if (Gdx.input.isKeyPressed(Keys.LEFT)) dentadura.x -= 500 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))dentadura.x += 500 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.DOWN))dentadura.y -= 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.UP))dentadura.y += 500 * Gdx.graphics.getDeltaTime();
	  
		// make sure the bucket stays within the screen bounds
		if(dentadura.x < 0) dentadura.x = 0;
		if (dentadura.x > 1340 - 64)dentadura.x = 1340 - 64;
		
		// make sure the bucket stays within the screen bounds
		if(dentadura.y < 0) dentadura.y = 0;
        if (dentadura.y > 1200 - 64) dentadura.y = 1200 - 64;        
    }

    public Boolean morre() {

        Morre = true;
        return Morre;
    }

    public Boolean render(Array<Rectangle> vidadrops) {

        Iterator<Rectangle> iter = vidadrops.iterator();

        Boolean tira_coracao = false;

        while (iter.hasNext()) {

            iter.next();

            if (Perdevida) {

                iter.remove();
                tira_coracao = Perdevida;
                Perdevida = false;

            }
        }
     
        return tira_coracao;
    }

    public void dispose() {

        dentadura_img.dispose();
        vida_img.dispose();
    }
}