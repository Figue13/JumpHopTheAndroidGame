package com.example.jumphop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Esta clase define el contenido que habrá en las pantallas jugables
 * @author Diego Figueroa González
 */
public class EscenaJugar extends Escena {

    /**
     * Pincel de los botones
     */
    Paint paintBtns=new Paint();
    /**
     * Pincel del botón para empezar la partida
     */
    Paint paintStart=new Paint();
    /**
     * Pincel de la puntuación
     */
    Paint paintScore=new Paint();
    /**
     * Pincel de la puntuación final de la partida
     */
    Paint paintScoreFinal=new Paint();
    /**
     * Velocidad de la aplicación
     */
    int velocidad,velocidadPlat;
    /**
     * Condiciones de los botones y las colisiones
     */
    boolean bolSalto=false,bolIzq=false,bolDer=false,startGame=false,jumpPlat=false,jumpPdbl=false,jumpPerdio=false,stopScore=false;
    /**
     * Puntuación de la partida actual
     */
    int score=1;
    /**
     * Puntuación de la partida finalizada
     */
    int scorefinal=0;
    /**
     * Imagen a mostrar de Jumpo
     */
    int imgJump=0;
    /**
     * Vibrador del dispositivo
     */
    Vibrator vibrator;
    /**
     * Música del juego
     */
    MediaPlayer songDerrota,songBase;
    /**
     * Administrador de sonido del dispositivo
     */
    AudioManager audioManager;
    /**
     * Volumen de la musica
     */
    int v=0;
    /**
     * Lista de plataformas
     */
    ArrayList<Plataforma>listPlataforma=new ArrayList<>();
    /**
     * Ranking de puntuación
     */
    ArrayList<Integer>ranking=new ArrayList<>();
    /**
     * Imagenes de Jumpo
     */
    ArrayList<Bitmap>imgJumpList=new ArrayList<>();
    /**
     * Imagen de  fondo de pantalla
     */
    Bitmap imagenFondo=getBitmapFromAssets("fondos/cielonuboso.png");
    /**
     * Imagen invertida de  fondo de pantalla
     */
    Bitmap imagenFondo2=getBitmapFromAssets("fondos/cielonubosoreverse.png");
    /**
     * Imagen botón iniciar partida
     */
    Bitmap imagenPlay=escalaAnchura("fondos/play-button.png",anchoPantalla/4);
    /**
     * Imagen Jumpo en estatico
     */
    Bitmap imagenJumpBase=escalaAnchura("sprites/flyMan_still_stand.png",anchoPantalla/6);
    /**
     * Imagen Jumpo volando
     */
    Bitmap imagenJumpVuela=escalaAnchura("sprites/flyMan_fly.png",anchoPantalla/6);
    /**
     * Imagen Jumpo aterrizando
     */
    Bitmap imagenJumpAterriza=escalaAnchura("sprites/flyMan_stand.png",anchoPantalla/6);
    /**
     * Imagen plataforma normal
     */
    Bitmap imagenPlataforma=escalaAnchura("plataformas/ground_stone.png",anchoPantalla/5);
    /**
     * Imagen plataforma rompible
     */
    Bitmap imagenPDbl=escalaAnchura("plataformas/ground_wood.png",anchoPantalla/5);
    /**
     * Imagen plataforma rota
     */
    Bitmap imagenPDblRota=escalaAnchura("plataformas/ground_wood_broken.png",anchoPantalla/5);
    /**
     * Imagen botón salir al menú
     */
    Bitmap imagenSalir=escalaAnchura("botones/leaveplay.png",anchoPantalla/4);
    /**
     * Imagen botón de salto de Jumpo
     */
    Bitmap imagenBtnArriba=escalaAnchura("botones/btnArriba.png",anchoPantalla/5);
    /**
     * Imagen botón de dirección derecha de Jumpo
     */
    Bitmap imagenBtnDer=escalaAnchura("botones/btnDer.png",anchoPantalla/5);
    /**
     * Imagen botón de dirección izquierda de Jumpo
     */
    Bitmap imagenBtnIzq=escalaAnchura("botones/btnIzq.png",anchoPantalla/5);
    /**
     * Rectangulos de los botones
     */
    Rect btnSalto,btnIzq,btnDer,btnStart;
    /**
     * Fondo de pantalla
     */
    Fondo fondoJuego=new Fondo(imagenFondo,0,0,altoPantalla);
    /**
     * Fondo de pantalla invertido
     */
    Fondo fondoJuego2=new Fondo(imagenFondo2,0-imagenFondo.getHeight(),0,altoPantalla);
    /**
     * Jumpo
     */
    Jumpo jumpo=new Jumpo(imagenJumpBase,anchoPantalla/2-imagenJumpBase.getWidth(),0);
    /**
     * Fuente de texto utilizada
     */
    Fuente fuente;
    /**
     * Contructor para la creación de las escenas jugables
     * @param idEscena int escena actual
     * @param context Context de la aplicación
     * @param anchoPantalla int numero pixeles de ancho del dispositivo
     * @param altoPantalla int numero pixeles de alto del dispositivo
     * @param velocidad int velocidad movimiento en pixeles
     */
    public EscenaJugar(int idEscena, Context context, int anchoPantalla, int altoPantalla,int velocidad) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        fuente=new Fuente(context);
        paintStart.setColor(Color.GRAY);
        paintScore.setColor(Color.BLACK);
        paintScore.setTypeface(fuente.getFuente());
        paintScore.setTextSize(30);
        paintScoreFinal.setColor(Color.DKGRAY);
        paintScoreFinal.setTypeface(fuente.getFuente());
        paintScoreFinal.setTextSize(imagenSalir.getHeight()/4);
        imgJumpList.add(imagenJumpBase);
        imgJumpList.add(imagenJumpVuela);
        imgJumpList.add(imagenJumpAterriza);
        this.velocidad=velocidad;
        this.velocidadPlat=velocidad;
        btnIzq=new Rect(anchoPantalla/2+20,altoPantalla-altoPantalla/10
                ,anchoPantalla/2+anchoPantalla/4-10,altoPantalla-20);
        btnDer=new Rect(btnIzq.right,btnIzq.top,anchoPantalla-20,btnIzq.bottom);
        btnSalto= new Rect(20,altoPantalla-altoPantalla/10,anchoPantalla/4,altoPantalla-20);

        btnStart=new Rect(anchoPantalla/2-imagenPlay.getWidth()/2,0,
                anchoPantalla/2-imagenPlay.getWidth()/2+imagenPlay.getWidth(),0+imagenPlay.getHeight());
        paintBtns.setColor(Color.GRAY);
        paintBtns.setStyle(Paint.Style.STROKE);
        paintBtns.setStrokeWidth(15);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        v= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(idEscena==1){
            songBase=MediaPlayer.create(context,R.raw.battleend);
        }else{
            songBase=MediaPlayer.create(context,R.raw.battle2);
        }
        songBase.setVolume(v/2,v/2);
        songDerrota=MediaPlayer.create(context,R.raw.defeat);
        SharedPreferences prefOptions =context.getSharedPreferences("options",Context.MODE_PRIVATE);
        if(prefOptions.getBoolean("volum",true)){
            songDerrota.setVolume(v/2,v/2);
            songBase.setLooping(true);
            songBase.start();
        }
    }
    /**
     * Método que dibuja en el lienzo los elementos que componen EscenaJugar
     * @param c Canvas sobre el que se dibuja
     */
    public void dibuja(Canvas c){
                if(startGame && !jumpPerdio){
                    c.drawBitmap(fondoJuego.imagen,fondoJuego.posicion.x,fondoJuego.posicion.y,null);
                    c.drawBitmap(fondoJuego2.imagen,fondoJuego2.posicion.x,fondoJuego2.posicion.y,null);
                    for (int i=0;i<listPlataforma.size();i++) {
                        c.drawBitmap(listPlataforma.get(i).imagen, listPlataforma.get(i).posicion.x, listPlataforma.get(i).posicion.y, null);
                    }
                    c.drawBitmap(imgJumpList.get(imgJump),jumpo.posicion.x,jumpo.posicion.y,null);
                    c.drawText(context.getResources().getString(R.string.score)+": "+score+"!",5,40,paintScore);
                    c.drawBitmap(imagenBtnArriba,btnSalto.left+10,btnSalto.top,null);
                    c.drawBitmap(imagenBtnDer,btnDer.left+10,btnDer.top,null);
                    c.drawBitmap(imagenBtnIzq,btnIzq.left+20,btnIzq.top,null);
                }else{
                    if(!jumpPerdio){
                        c.drawRect(0,0,anchoPantalla,altoPantalla,paintScore);
                        c.drawRect(btnStart,paintStart);
                        c.drawBitmap(imagenPlay,anchoPantalla/2-imagenPlay.getWidth()/2,0,null);
                    }else{
                        paintStart.setColor(Color.RED);
                        c.drawRect(0,0,anchoPantalla,altoPantalla,paintStart);
                        c.drawBitmap(imagenSalir,anchoPantalla/2-imagenSalir.getWidth()/2,0,null);
                        if(idEscena==1){
                            paintScore.setTextSize(imagenSalir.getHeight()/6);
                            c.drawText(context.getResources().getString(R.string.score)+":"+scorefinal,anchoPantalla/2-imagenSalir.getWidth()/2,altoPantalla/2
                                    +imagenSalir.getHeight()+5,paintScore);
                            //Score guardado comparacion
                            SharedPreferences prefScore1 =context.getSharedPreferences("ranking",Context.MODE_PRIVATE);
                            c.drawText("1- "+prefScore1.getInt("record1",0000),anchoPantalla/2-imagenSalir.getWidth()/2,altoPantalla/2
                                    +imagenSalir.getHeight()+10+(imagenSalir.getHeight()/6*2),paintScoreFinal);
                            c.drawText("2- "+prefScore1.getInt("record2",0000),anchoPantalla/2-imagenSalir.getWidth()/2,altoPantalla/2
                                    +imagenSalir.getHeight()+15+(imagenSalir.getHeight()/6*3),paintScoreFinal);
                            c.drawText("3- "+prefScore1.getInt("record3",0000),anchoPantalla/2-imagenSalir.getWidth()/2,altoPantalla/2
                                    +imagenSalir.getHeight()+25+(imagenSalir.getHeight()/6*4),paintScoreFinal);
                        }
                    }
                }
    }

    /**
     * Método que anima los elementos jugables
     */
    public void actualizaFisica(){
        if(startGame && !jumpPerdio){
            controlsJumpo();
            //animacionPlataforma
            for (int i=0;i<listPlataforma.size();i++) {
                listPlataforma.get(i).caerPlataforma(velocidadPlat);
                if(listPlataforma.get(i).getClass()==PlataformaDebil.class){
                    if(((PlataformaDebil)listPlataforma.get(i)).caida){
                        listPlataforma.get(i).caerPlataforma(velocidadPlat);
                    }
                }
                if(listPlataforma.get(i).posicion.y>=altoPantalla){
                    listPlataforma.remove(i);
                }
            }
            if(idEscena==1){
                if(score%90==0 || score==1){
                    generaPlataforma(anchoPantalla);
                }
            }else{
                if(score%60==0 || score==1){
                    generaPlataformasMal(anchoPantalla);
                }
            }
            animacionFondo();
            //perder
            if(jumpo.posicion.y>altoPantalla+jumpo.rectangulo.height()){
                stopScore=true;
                scorefinal=score;
                if(idEscena==1){
                    SharedPreferences prefScore1 =context.getSharedPreferences("ranking",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefScore1.edit();
                    for(int i=0;i<4;i++){
                        ranking.add(prefScore1.getInt("record"+i,0));
                    }
                    Collections.sort(ranking,Collections.<Integer>reverseOrder());
                    boolean flagScoreInserted=false;
                    for(int i=0; i < 4; i++){
                        if(!flagScoreInserted){
                            if(ranking.get(i) < scorefinal){
                                ranking.remove(ranking.size()-1);
                                ranking.add(i,scorefinal);
                                flagScoreInserted=true;
                            }
                        }
                        editor.putInt("record"+i, ranking.get(i));
                    }
                    editor.commit();
                }
                jumpPerdio=true;
                SharedPreferences prefOptions =context.getSharedPreferences("options",Context.MODE_PRIVATE);
                if(prefOptions.getBoolean("vibrating",true)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                    }else {
                        vibrator.vibrate(300);
                    }
                    songBase.stop();
                    songDerrota.start();
                }

            }
        }
    }

    /**
     * Método detecta las pulsacion en los controles del juego
     * @param event MotionEvent del que se obtiene id y ejes X e Y
     * @return idEscena int escena a mostrar
     */
    public int onTouchEvent(MotionEvent event) {

        int index=event.getActionIndex();
        int id =event.getPointerId(index);
        int accion=event.getActionMasked();
        int x,y;
        switch (accion) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                Log.i("indice",""+index+" "+id);
                x = (int) event.getX(index);
                y = (int) event.getY(index);
                if(startGame && !jumpPerdio){
                    if (btnSalto.contains(x, y)) {
                        bolSalto = true;
                    }
                    if (btnIzq.contains(x, y)) {
                        bolIzq = true;
                    }
                    if (btnDer.contains(x, y)) {
                        bolDer = true;
                    }
                }
                if(btnStart.contains(x,y)){
                    startGame=true;
                }
                if(jumpPerdio && btnStart.contains(x,y)){
                    songDerrota.stop();
                    return 0;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                Log.i("indice",""+index+" "+id);
                x = (int) event.getX(index);
                y = (int) event.getY(index);
                //Reset condiciones salto
                if(startGame){
                    if (btnSalto.contains(x, y)) {
                        bolSalto = false;
                        jumpo.salto=false;
                        jumpo.alturaSalto=0;
                    }
                    //
                    bolIzq = false;
                    bolDer = false;
                }
                break;
        }
        return idEscena;
    }

    /**
     * Método anima los elementos del juego
     */
    public void animacionFondo(){
        fondoJuego.mover(velocidad);
        fondoJuego2.mover(velocidad);
        if(fondoJuego.posicion.y>=altoPantalla){jumpo.salto=true;
            fondoJuego.posicion.y=0-imagenFondo.getHeight();
        }
        if(fondoJuego2.posicion.y>=altoPantalla){
            fondoJuego2.posicion.y=0-imagenFondo.getHeight();
        }
        if (!stopScore) {
            score++;
        }
    }

    /**
     * Método que determina el funcionamiento de los controles al ser pulsados
     */
    public void controlsJumpo() {
        if (bolSalto && jumpo.alturaSalto < jumpo.imagen.getHeight() * 2 && jumpo.salto) {
            jumpo.saltar(velocidad*4);
            imgJump=1;
        }
        if(bolSalto && !jumpo.salto){
            jumpo.caer(velocidad*3);
            imgJump=2;
        }
        if (!bolSalto || jumpo.alturaSalto >= jumpo.imagen.getHeight() * 2 ) {
            jumpPdbl=false;
            jumpPlat=false;
            for(int i=0;i<listPlataforma.size();i++){
                if(listPlataforma.get(i).rectangulo.intersect(jumpo.rectangulo)){
                    jumpPlat=true;
                    //jumpo parado en plataforma
                    if(listPlataforma.get(i).getClass()==PlataformaDebil.class){
                        jumpPdbl=true;
                        jumpo.salto=true;
                        ((PlataformaDebil)listPlataforma.get(i)).granCaida();
                        //jumpo.caer(velocidad*2);
                        Log.i("choca","chocaaaa");
                    }else{
                        jumpo.salto=true;
                        //jumpo.caer(velocidad);
                        Log.i("choca","chocaaaa");
                    }
                }
            }
            if(jumpPlat && jumpPdbl){
                jumpo.caer(velocidad*2);
                imgJump=0;
            }else{
                if(jumpPlat){
                    jumpo.caer(velocidad);
                    imgJump=0;
                }else{
                    jumpo.caer(velocidad*3);
                    imgJump=2;
                }
            }
        }
        if (bolIzq) {
                jumpo.moverIzquierda();
        }
        if (bolDer) {
                jumpo.moverDerecha(anchoPantalla);
        }
    }

    /**
     * Método que genera plataformas de dos tipos
     * @param tamanhoX int rango maximo entre el que se generan plataformas
     */
    public void generaPlataforma(int tamanhoX){
        boolean inicioPlats=false;
        if(score==1){
            inicioPlats=true;
        }
        int randTipoPlat=(int)(Math.random()*1000+0);
        int rangoX=tamanhoX-imagenPlataforma.getWidth();
        int ranX=(int)(Math.random()*rangoX+0);
        if(inicioPlats){
            Plataforma plat0=new Plataforma(imagenPlataforma,anchoPantalla/2-imagenPlataforma.getWidth()/2,imagenPlataforma.getHeight()*2,velocidad);
            listPlataforma.add(plat0);
            Plataforma plat1=new Plataforma(imagenPlataforma,anchoPantalla/3+imagenPlataforma.getWidth(),0,velocidad);
            listPlataforma.add(plat1);
            Plataforma plat2=new Plataforma(imagenPlataforma,anchoPantalla/2-imagenPlataforma.getWidth(),imagenPlataforma.getHeight()*4,velocidad);
            listPlataforma.add(plat2);
        }else{
            if(randTipoPlat<=650){
                PlataformaDebil pdb=new PlataformaDebil(imagenPDbl,imagenPDblRota,ranX,0-imagenPDbl.getHeight(),velocidad);
                listPlataforma.add(pdb);
            }else{
                Plataforma plat=new Plataforma(imagenPlataforma,ranX,0-imagenPDbl.getHeight(),velocidad);
                listPlataforma.add(plat);
            }
        }
    }

    /**
     * Método que genera plataformas de madera
     * @param tamanhoX int rango maximo entre el que se generan plataformas
     */
    public void generaPlataformasMal(int tamanhoX){
        boolean inicioPlats=false;
        if(score==1){
            inicioPlats=true;
        }
        int rangoX=tamanhoX-imagenPlataforma.getWidth();
        int ranX=(int)(Math.random()*rangoX+0);
        if(inicioPlats){
            Plataforma plat0=new Plataforma(imagenPlataforma,anchoPantalla/2-imagenPlataforma.getWidth()/2,imagenPlataforma.getHeight()*2,velocidad);
            listPlataforma.add(plat0);
            Plataforma plat1=new Plataforma(imagenPlataforma,anchoPantalla/3+imagenPlataforma.getWidth(),0,velocidad);
            listPlataforma.add(plat1);
            Plataforma plat2=new Plataforma(imagenPlataforma,anchoPantalla/2-imagenPlataforma.getWidth(),imagenPlataforma.getHeight()*4,velocidad);
            listPlataforma.add(plat2);
        }else{
            PlataformaDebil pdb=new PlataformaDebil(imagenPDbl,imagenPDblRota,ranX,0-imagenPDbl.getHeight(),velocidad);
            listPlataforma.add(pdb);
        }
    }

    /**
     * Método reescala Bitmap a un nuevo ancho imagen
     * @param rutaAssets string ruta imagen en assets
     * @param nuevoAncho int numero pixeles del ancho deseado
     * @return Bitmap imagen con nuevo ancho
     */
    public Bitmap escalaAnchura(String rutaAssets, int nuevoAncho) {
        Bitmap bitmapAux=getBitmapFromAssets(rutaAssets);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
    }

}
