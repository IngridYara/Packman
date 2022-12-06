package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Sound;

import java.util.Iterator;

public class Ghost extends ApplicationAdapter{

    private Texture Ghost_img1;
    private Texture Ghost_img2;
    private Texture Ghost_img3;
    private Texture Ghost_img4;
    private Texture Ghost_img5;
    private Texture Ghost_img6;

    private Sound morre_Som;

    private  int posicao_x, posicao_y, quantidadeFantasmasComidos = 0,pontos= 0;
    Random random;
    public Dentadura dentadura = new Dentadura();

    public Ghost(int posicao_x, int posicao_y) {

        this.posicao_x = posicao_x;
        this.posicao_y = posicao_y;   
    }

    public Ghost() {

    }

    public void create(Array<Rectangle> ghostdrops) {

        //imagem escolhida do pack man
        Ghost_img1 = new Texture(Gdx.files.internal("Ghost_vermelho.png"));
        Ghost_img2 = new Texture(Gdx.files.internal("Ghost_rosa.png"));
        Ghost_img3 = new Texture(Gdx.files.internal("Ghost_azul.png"));
        Ghost_img4 = new Texture(Gdx.files.internal("Ghost_amarelo.png"));
        Ghost_img5 = new Texture(Gdx.files.internal("Ghost-morto1.png"));
        Ghost_img6 = new Texture(Gdx.files.internal("Ghost_morto2.png"));

        morre_Som = Gdx.audio.newSound(Gdx.files.internal("assets/Pack-morre.mp3"));
        random = new Random();
    }

    public void draw(Array<Rectangle> ghostdrops, SpriteBatch batch) {

        Iterator<Rectangle> iter = ghostdrops.iterator();

        Rectangle ghostdrop = iter.next();
        batch.draw(Ghost_img1, ghostdrop.x, ghostdrop.y);

        ghostdrop = iter.next();
        batch.draw(Ghost_img2, ghostdrop.x, ghostdrop.y);

        ghostdrop = iter.next();
        batch.draw(Ghost_img3, ghostdrop.x, ghostdrop.y);

        ghostdrop = iter.next();
        batch.draw(Ghost_img4, ghostdrop.x, ghostdrop.y);
    }

    public void drawEspecial(Array<Rectangle> ghostdrops, SpriteBatch batch){ /** Função que desenha os ghosts azuls **/

        Iterator<Rectangle> iter = ghostdrops.iterator();

        while (iter.hasNext()) {

            Rectangle ghostdrop = iter.next();    
            
            if (random.nextBoolean()){
                batch.draw(Ghost_img5, ghostdrop.x, ghostdrop.y);
                
            }else{
                batch.draw(Ghost_img6, ghostdrop.x, ghostdrop.y);
            }
        }
    }

    public void spawnghostdrop(Array<Rectangle> ghostdrops) {

        Rectangle ghostdrop = new Rectangle();

        ghostdrop.x = posicao_x;
        ghostdrop.y = posicao_y;
        ghostdrop.width = 64;
        ghostdrop.height = 64;
        ghostdrops.add(ghostdrop);
    }

    Boolean Direcoes[] = { true, false, true, false };
  
    public void render(Array<Rectangle> ghostdrops, Rectangle dentadura_rectangle, boolean comeu_comida_especial) {

        // move the ghostdrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
        Iterator<Rectangle> iter = ghostdrops.iterator();
        
        int i = 0, j = 0;
        int posicaoY = 0, posicao_atual = 0;

        while (iter.hasNext()) {

            Rectangle ghostdrop = iter.next();

            posicao_atual = MathUtils.random(1, 9 - 1);

            for (j = 0; j < ghostdrops.size; j++) {
            
                while (posicaoY == ghostdrops.get(j).y){

                    switch (posicao_atual) {

                        case 1:
                            posicaoY = 1200;
                            break;
    
                        case 2:
                            posicaoY = 1050;
                            break;
    
                        case 3:
                            posicaoY = 900;
                            break;
    
                        case 4:
                            posicaoY = 750;
                            break;
    
                        case 5:
                            posicaoY = 600;
                            break;
    
                        case 6:
                            posicaoY = 450;
                            break;
    
                        case 7:
                            posicaoY = 300;
                            break;
    
                        case 8:
                            posicaoY = 150;
                            break;
                    }
    
                    if (posicaoY == ghostdrops.get(j).y) {
                        posicao_atual = MathUtils.random(1, 9 - 1);
                        
                    }
                }   
            }
            
            //Vai fazer andar pra diteira 
            if (Direcoes[i]) {
                ghostdrop.x += 200 * Gdx.graphics.getDeltaTime();

                //Vai fazer andar pra esquerda
            } else {
                ghostdrop.x -= 200 * Gdx.graphics.getDeltaTime();
            }

            if (ghostdrop.x < 0) {

                //se bater na parede esquerda volta pra direita
                if ((MathUtils.random(0, 10000 - 1) / 2) == 0) {

                    Direcoes[i] = true;

                    //se bater na parede esquerda sai da borda
                } else {

                    //vai pra outra linha com a mesma direção, no inicio dela
                    if (random.nextBoolean() == true) {

                        ghostdrop.y =  posicaoY;
                        ghostdrop.x = 0;
                        Direcoes[i] = true;

                        //vai pra outra linha com a direção diferente, no final  dela
                    } else {

                        ghostdrop.y =  posicaoY;
                        ghostdrop.x = 1336;
                        Direcoes[i] = false;

                    }
                }
            }

            //NÃO ENTRA AQUI
            if (ghostdrop.x > 1400 - 64) {

                //se bater na parede direita volta pra esquerda
                if ((MathUtils.random(0, 10000 - 1) / 2) == 0) {

                    Direcoes[i] = false;

                    //se bater na parede direita sai da borda
                } else {

                    //vai pra outra linha com a mesma direção, no inicio dela
                    if (random.nextBoolean() == true) {

                        ghostdrop.y = posicaoY;
                        ghostdrop.x = 0;
                        Direcoes[i] = true;

                        //vai pra outra linha com a direção diferente, no final  dela
                    } else {

                        ghostdrop.y = posicaoY;
                        ghostdrop.x = 1336;
                        Direcoes[i] = false;
                    }
                }
            }

            if ((MathUtils.random(0, 10000 - 1) / 2) == 0) {

                //Vai fazer andar pra esquerda     
                if (Direcoes[i]) {

                    Direcoes[i] = false;
                    //Vai fazer andar pra diteira 
                } else {
                    Direcoes[i] = true;
                }
            }

            i++;

            if(ghostdrop.overlaps(dentadura_rectangle)) {
                
                if(comeu_comida_especial) { /** If que verifica se comeu a comida especial **/
             
                    iter.remove(); /** Remove o fantasma da arraylist **/
                   
                    quantidadeFantasmasComidos++;
                    pontos = pontos + 20;
                    morre_Som.play();

                }else{ /** Else pra verificar o contrário **/

                    dentadura.morre();
                    morre_Som.play();

                }
            }
        }       
    }

    public void dispose() {

        // dispose the native resources
        Ghost_img1.dispose();
        Ghost_img2.dispose();
        Ghost_img3.dispose();
        Ghost_img4.dispose();
        Ghost_img5.dispose();
        Ghost_img6.dispose();
    }

    public int getQuantidadeFantasmasComidos() {
        return quantidadeFantasmasComidos;
    }

    public void setQuantidadeFantasmasComidos(int quantidadeFantasmasComidos) {
        this.quantidadeFantasmasComidos = quantidadeFantasmasComidos;
    }
    public int getPontos() {
        return pontos;
    }
}