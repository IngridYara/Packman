package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

public class MyGdxGame extends ApplicationAdapter {
	
	private final int quantidadeDeObstaculos = 18;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Rectangle dentadura_retangle;

	private Texture Jawbreaker_assusta_img;
	private Texture Nuvem_LGBTQIA;

	private Music rainMusic;

	private Array<Rectangle> ghostdrops;
	private Array<Rectangle> comidadrops;
	private Array<Rectangle> vidadrops;
	private Array<Obstaculo> obstaculos;

	private long Pausa_PosGanhar = TimeUtils.millis();
	private long Tempo_PosVida = TimeUtils.millis();
	private long Tempo_Jogo = TimeUtils.millis();
	private long tempo_comidaEspecial = TimeUtils.millis();

	private Sound assusta;
	private Sound ganha;

	private BitmapFont font;
	
	private Boolean ganhou = false;
	int l = 2, m = 1;

	Random random;

	public Dentadura dentadura = new Dentadura(Tempo_Jogo );
	public Ghost ghost = new Ghost();
	public Comida comida = new Comida(Tempo_Jogo);
	public ComidaEspecial comidaEspecial;

	ArrayList<Ghost> fantasminhas = new ArrayList<Ghost>();
	ArrayList<Dentadura> vidinhas = new ArrayList<Dentadura>();
	ArrayList<Comida> Comidinhas = new ArrayList<Comida>();

	ArrayList<Integer> vetorY = new ArrayList<Integer>();
	ArrayList<Integer> vetorX = new ArrayList<Integer>();

	@Override
	public void create() {

		obstaculos = new Array<Obstaculo>();

		random = new Random();

		Jawbreaker_assusta_img = new Texture(Gdx.files.internal("medo.png"));
		Nuvem_LGBTQIA = new Texture(Gdx.files.internal("Nuvem_arcoiris.png"));

		assusta = Gdx.audio.newSound(Gdx.files.internal("assets/medo-audio.mp3"));
		ganha = Gdx.audio.newSound(Gdx.files.internal("assets/ganhou (2).mp3"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/musica-inicial.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		int k = 1;
		
		for (int j = 1 ; j <= 100 ; j++){
			
			k = j * 100;
			
			Comidinhas.add(new Comida(k, 1090));
			Comidinhas.add(new Comida(k,940));
			Comidinhas.add(new Comida(k, 790));
			Comidinhas.add(new Comida(k, 645));
			Comidinhas.add(new Comida(k, 495));
			Comidinhas.add(new Comida(k, 345));
			Comidinhas.add(new Comida(k, 200));
			Comidinhas.add(new Comida(k, 50));
		}

		//OBSTACULOS ESQUERDOS
		obstaculos.add(new Obstaculo(4,100, 1150));
		obstaculos.add(new Obstaculo(3,100, 1000));
		obstaculos.add(new Obstaculo(2,100, 850));
		obstaculos.add(new Obstaculo(4,100, 700));
		obstaculos.add(new Obstaculo(1,100, 550));
		obstaculos.add(new Obstaculo(2,100, 400));
		obstaculos.add(new Obstaculo(4,100, 250));
		obstaculos.add(new Obstaculo(3,100, 100)); //

		//OBSTACULOS CENTRAIS
		obstaculos.add(new Obstaculo(2,800, 1150));
		obstaculos.add(new Obstaculo(1,400, 1000));
		obstaculos.add(new Obstaculo(4,575, 850));
		obstaculos.add(new Obstaculo(2,805, 700));
		obstaculos.add(new Obstaculo(2,800, 550));
		obstaculos.add(new Obstaculo(4,575, 400));
		obstaculos.add(new Obstaculo(2,800, 250));
		obstaculos.add(new Obstaculo(1,400, 100));//

		//OBSTACULOS DIREITOS
		obstaculos.add(new Obstaculo(3, 1025, 1000));
		
		//É essa aqui
		obstaculos.add(new Obstaculo(3,1025, 100));//

		fantasminhas.add(new Ghost(80, 1060));
		fantasminhas.add(new Ghost(380, 770));
		fantasminhas.add(new Ghost(760, 460));
		fantasminhas.add(new Ghost(1060, 140));

		vidinhas.add(new Dentadura(10, 0));
		vidinhas.add(new Dentadura(20, 0));
		vidinhas.add(new Dentadura(30, 0));

		comidaEspecial = new ComidaEspecial (100,100);
		// create a Rectangle to logically represent the Pack man
		dentadura.create();
		ghost.create(ghostdrops);
		comida.create(comidadrops);
		comidaEspecial.create();

		ghostdrops = new Array<Rectangle>();
		comidadrops = new Array<Rectangle>();
		vidadrops = new Array<Rectangle>();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1400, 1300);
		batch = new SpriteBatch();
	
		// create the ghostdrops array and spawn the first raindrop	
		dentadura_retangle = dentadura.spawnRaindrop();
		comidaEspecial.create();
		comidaEspecial.spaw_comidinha();

		for (int i = 0; i < 3; i++) {

			vidinhas.get(i).spawVidas(vidadrops);
		}

		for (int i = 0; i < 4; i++) {

			fantasminhas.get(i).spawnghostdrop(ghostdrops);
		}

		for (int i = 0; i < 96; i++) {

			Comidinhas.get(i).spaw_comidinha(comidadrops);
		}	

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(3);			
	}

	public void render() {

		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch an d draw the PackMan and
		// all drops
		batch.begin();
			
		if (comidadrops.size == 0){

			ganhou = true;
			comida.render(comidadrops,comidaEspecial.getComidinha(), dentadura_retangle, ((Dentadura) dentadura), true,tempo_comidaEspecial);

			batch.draw(Nuvem_LGBTQIA, 200, 100);			
		}

		if (ganhou == false /*&& m != 5*/){

			dentadura.drawVidas(batch,vidadrops);	
			dentadura.draw(batch);
			comida.draw(comidadrops, batch);

			if(comida.comeu_comida_especial_get()){

				ghost.drawEspecial(ghostdrops, batch); /** Função que chama a parte que desenha o fantasma azul **/

			} else { /** Else criado para desenhar o ghost padrão se não tiver comido **/
				
				ghost.draw(ghostdrops, batch);

				if(TimeUtils.millis() - tempo_comidaEspecial > 10000){
					comidaEspecial.draw(batch);
				}
			}

			for (int i = 0; i < quantidadeDeObstaculos; i++) {

				obstaculos.get(i).draw(batch);
			}

			for (int i = 0; i < quantidadeDeObstaculos; i++) {

				if(obstaculos.get(i).testaColisao(dentadura, obstaculos.get(0))){

					if (dentadura.dentadura.overlaps((obstaculos.get(i)).obstaculoRect)) {
				
						if ((dentadura.dentadura.x + dentadura.dentadura.width) >= (((obstaculos.get(i)).obstaculoRect.x) + ((obstaculos.get(i)).obstaculoRect.getWidth()))) {
							dentadura.dentadura.x = ((obstaculos.get(i)).obstaculoRect.x + (obstaculos.get(i)).obstaculoRect.getWidth());
						}

						if(dentadura.dentadura.x < ((obstaculos.get(i)).obstaculoRect.x)){
							dentadura.dentadura.x = (obstaculos.get(i)).obstaculoRect.x - dentadura.dentadura.width;
						}

						if ((dentadura.dentadura.y + dentadura.dentadura.height) >= (((obstaculos.get(i)).obstaculoRect.y) + ((obstaculos.get(i)).obstaculoRect.getHeight()))) {
							dentadura.dentadura.y = ((obstaculos.get(i)).obstaculoRect.y + (obstaculos.get(i)).obstaculoRect.getHeight());
						}

						if(dentadura.dentadura.y < ((obstaculos.get(i)).obstaculoRect.y)){
							dentadura.dentadura.y = (obstaculos.get(i)).obstaculoRect.y - dentadura.dentadura.height;
						}
					}
				}
			}
				
			font.draw(batch, "Pontos = " + (((Dentadura) dentadura).pontos + ghost.getPontos()), 500, 1250);	
		}

		if (ganhou == false) {

			ghost.render(ghostdrops, dentadura_retangle, comida.comeu_comida_especial_get()); //Variavel 
			dentadura.move();
			comida.render(comidadrops,comidaEspecial.getComidinha(),  dentadura_retangle, ((Dentadura) dentadura), false,tempo_comidaEspecial);	
			Boolean perdeu_vida = dentadura.render(vidadrops);
			
			int perdeu = 0;

			if (perdeu_vida) {

				for (int i = 0; i <= 2; i++) {

					//vidinhas.get(i).spawVidas(vidadrops);
					perdeu = 1;
				}

				dentadura.drawVidas(batch, vidadrops);
				
				if (perdeu == 1) {
					
					m++;
				
					perdeu = 0;
					Tempo_PosVida = TimeUtils.millis();
				}

				perdeu_vida = false;
			}		
			
			// if (m == 5) {

			// // 	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			// font.draw(batch, "GAME OVER : (", 500, 1250);

			// if (random.nextBoolean()){
			// 	batch.draw(Jawbreaker_assusta_img, 300, 400);		
			// }else{
			// 	batch.draw(Jawbreaker_assusta_img, 300, 600);		
			// }
				
				
			// 	soundPlay();		

			// 	// Gdx.app.exit();
			// 	// System.exit(-1);
			// }
		}else{
			
			comida.render(comidadrops,comidaEspecial.getComidinha(), dentadura_retangle, ((Dentadura) dentadura), true,tempo_comidaEspecial);
		}

		if (comida.getFinalizouEspecial()) {

			for (int i = (4 - ghost.getQuantidadeFantasmasComidos()); i < 4; i++) {
				fantasminhas.get(i).spawnghostdrop(ghostdrops);
			}

			comida.setFinalizouEspecial(false);
			tempo_comidaEspecial = TimeUtils.millis();
			ghost.setQuantidadeFantasmasComidos(0);
		}
		
		if (TimeUtils.millis() - Pausa_PosGanhar > 30000) {
			
			ganhou = false;

			if (comidadrops.size == 0) {

				for (int i = 0; i < 96; i++) {

					Comidinhas.get(i).spaw_comidinha(comidadrops);
				}
			}

			Pausa_PosGanhar = TimeUtils.millis();
		}
		
		batch.end();
	}

	public void soundPlay() {

		ganha.play();
		assusta.play();
    }

	@Override
	public void dispose () {

		// dispose of all the native resources
		batch.dispose();
		dentadura.dispose();
		ghost.dispose();
		comida.dispose();
		assusta.dispose();
	}
}