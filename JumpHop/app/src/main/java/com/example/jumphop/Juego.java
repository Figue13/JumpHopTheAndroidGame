package com.example.jumphop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que hereda de SurfaceView y administra los hilos y escenas del juego
 * @author Diego Figueroa González
 */
public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Escena a inicializar
     */
    Escena escenaActual;
    /**
     * Superficie con la que trabajar
     */
    private SurfaceHolder surfaceHolder;
    /**
     *Contexto de la aplicación
     */
    private Context context;
    /**
     *Ancho de pantalla del dispositivo
     */
    private int anchoPantalla=1;
    /**
     *Alto de pantalla del dispositivo
     */
    private  int altoPantalla=1;
    /**
     *Hilo
     */
    private Hilo hilo;
    /**
     *Velocidad de la aplicación
     */
    private int velocidadJuego=3;
    /**
     *Si funciona la aplicación
     */
    private boolean funcionando=false;
    /**
     *Inicio de la aplicación
     */
    boolean inicio=true;

    /**
     * Constructor que inicializa Juego
     * @param context Context
     */
    public Juego(Context context) {
        super(context);
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);
        this.context=context;
        hilo=new Hilo();
        setFocusable(true);
    }

    /**
     *Método ejecutaod en al creación del Surface que inicia el hilo
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hilo.setFuncionando(true);
    }

    /**
     * Método detecta un cambio en el Surface e inicia la primera escena
     * @param holder SurfaceHolder
     * @param format int
     * @param width int
     * @param height int
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.altoPantalla=height;
        this.anchoPantalla=width;
        if(inicio){
            escenaActual=new EscenaMenuPrincipal(0,context,anchoPantalla,altoPantalla);
            inicio=false;
        }

        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();

        }
    }

    /**
     * Método detiene hilo al destruir el Surface
     *@param holder SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hilo.setFuncionando(false);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nuevaEscena=escenaActual.onTouchEvent(event);
        if(nuevaEscena!=escenaActual.idEscena) {
            cambioEscena(nuevaEscena);
        }
       return true;
    }

    /**
     * Clase que se utilizara para el funcionamiento multihilo del juego
     */
    public class Hilo extends Thread {
        public boolean funcionando = false;
        public Hilo() {

        }
        public boolean isFuncionando() {
            return funcionando;
        }

        public void setFuncionando(boolean funcionando) {
            this.funcionando = funcionando;
        }

        /**
         * Método que ejecuta continuamente los métodos de las escenas del juego
         */
        @Override
        public void run() {
            long tiempoDormido = 0;
            final int FPS = 50;
            final int TPS = 1000000000;
            final int FRAGMENTO_TEMPORAL = TPS / FPS;
            long tiempoReferencia = System.nanoTime();
            while (funcionando) {
                Canvas c = null;
                if (!surfaceHolder.getSurface().isValid()) {
                    continue;
                }
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas();
                    } else {
                        c = surfaceHolder.lockCanvas();
                    }
                    synchronized (surfaceHolder) {
                        escenaActual.actualizaFisica();
                        escenaActual.dibuja(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                tiempoReferencia += FRAGMENTO_TEMPORAL;
                tiempoDormido = tiempoReferencia - System.nanoTime();
                if (tiempoDormido > 0) {
                    try {
                        Thread.sleep(tiempoDormido / 1000000); //Convertimos a ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Método administra las escenas
     * @param idNuevaEscena int determina escena a inicializar
     */
    public void cambioEscena(int idNuevaEscena){
        switch(idNuevaEscena){
            case 0:
                escenaActual=new EscenaMenuPrincipal(0,context,anchoPantalla,altoPantalla);
                break;
            case 1:
                escenaActual=new EscenaJugar(1,context,anchoPantalla,altoPantalla,velocidadJuego);
                break;
            case 2:
                escenaActual=new EscenaJugar(2,context,anchoPantalla,altoPantalla,velocidadJuego);
                break;
            case 3:
                escenaActual=new EscenaRecords(3,context,anchoPantalla,altoPantalla);
                break;
            case 4:
                escenaActual=new EscenaOpciones(4,context,anchoPantalla,altoPantalla);
                break;
            case 5:
                escenaActual=new EscenaCreditos(5,context,anchoPantalla,altoPantalla);
                break;
            case 6:
                escenaActual=new EscenaInfo(6,context,anchoPantalla,altoPantalla);
                break;
            default:
                break;
        }
    }
}

