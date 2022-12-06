package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Obstaculo extends ApplicationAdapter {

    private Texture imagemObstaculo;
    public Rectangle obstaculoRect;

    private boolean colisao = true;

    public Obstaculo(int img, int x, int y) {

        switch (img) {

            case 1:
                imagemObstaculo = new Texture("obstaculo.png");
                break;

            case 2:
                imagemObstaculo = new Texture("obstaculo2.png");
                break;

            case 3:
                imagemObstaculo = new Texture("obstaculo3.png");
                break;
            
            case 4:
                imagemObstaculo = new Texture("obstaculo4.png");
                break;
        }

        obstaculoRect = new Rectangle(x, y, imagemObstaculo.getWidth(), imagemObstaculo.getHeight());

    }

    public void draw(SpriteBatch batch) {

        batch.draw(imagemObstaculo, obstaculoRect.x, obstaculoRect.y);
    }

    public boolean testaColisao(Dentadura dentaduraPlayer, Obstaculo obstaculoObject) {

        if(dentaduraPlayer.dentadura.overlaps(obstaculoObject.obstaculoRect)){

            colisao = true;
        }

        return colisao;
    }

    public int getBounds(){

        return imagemObstaculo.getHeight() * imagemObstaculo.getWidth();

    }

}
