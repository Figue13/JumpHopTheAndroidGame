package com.example.jumphop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Esta clase define el contenido que habrá en la pantalla de creditos
 * @author Diego Figueroa González
 *
 */
public class EscenaCreditos extends Escena {
    /**
     * Pincel del fondo de pantalla
     */
    Paint paintFondo= new Paint();
    /**
     * Pincel del titulo de la escena
     */
    Paint paintTitulo=new Paint();
    /**
     * Pincel del apartado de los creditos
     */
    Paint paintApartado=new Paint();
    /**
     * Pincel del nombre propio
     */
    Paint paintNombre=new Paint();
    /**
     * Fuente de la aplicación
     */
    Fuente fuente;
    /**
     * Constructor que inicializa los Paint
     * @param idEscena int de escena actual
     * @param context context de la aplicación
     * @param anchoPantalla int numero de pixeles de ancho que tiene la pantalla del dispositivo
     * @param altoPantalla int numero de pixeles de alto que tiene la pantalla del dispositivo
     */

    public EscenaCreditos(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        fuente= new Fuente(context);
        paintFondo.setColor(Color.argb(100,196,202,206));
        paintTitulo.setTypeface(fuente.getFuente());
        paintTitulo.setTextSize(anchoPantalla/10);
        paintTitulo.setColor(Color.argb(100,167,105,16));
        paintApartado.setTextSize(anchoPantalla/15);
        paintNombre.setTypeface(fuente.getFuente());
        paintApartado.setTypeface(fuente.getFuente());
        paintNombre.setTextSize(anchoPantalla/20);
        paintApartado.setColor(Color.BLUE);
        paintNombre.setColor(Color.BLACK);
    }

    Bitmap mono=escalaAnchura("sprites/mono.png",anchoPantalla/6);
    /**
     * Método ejecutado por Hilo en Juego que dibuja en el canvas de la aplicación
     * @param c Canvas sobre el que se dibuja
     */
    @Override
    public void dibuja(Canvas c) {
        c.drawRect(0,0,anchoPantalla,altoPantalla,paintFondo);
        c.drawBitmap(mono,anchoPantalla-mono.getWidth()*2,altoPantalla-mono.getHeight(),null);
        c.drawBitmap(mono,anchoPantalla-mono.getWidth(),altoPantalla-mono.getHeight(),null);
        c.drawBitmap(mono,0,altoPantalla-mono.getHeight(),null);
        c.drawBitmap(mono,mono.getWidth(),altoPantalla-mono.getHeight(),null);
        c.drawBitmap(mono,anchoPantalla-mono.getWidth()*2,mono.getHeight(),null);
        c.drawBitmap(mono,anchoPantalla-mono.getWidth(),mono.getHeight(),null);
        c.drawBitmap(mono,0,mono.getHeight(),null);
        c.drawBitmap(mono,mono.getWidth(),mono.getHeight(),null);
        c.drawText(context.getResources().getString(R.string.Credits),anchoPantalla/3,paintTitulo.getTextSize(),paintTitulo);
        c.drawText(context.getResources().getString(R.string.programmer),anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize(),paintApartado);
        c.drawText("Diego Figueroa González",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()+paintNombre.getTextSize(),paintNombre);
        c.drawText(context.getResources().getString(R.string.Development),anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*2+paintNombre.getTextSize()*2,paintApartado);
        c.drawText("Diego Figueroa González",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*2+paintNombre.getTextSize()*3,paintNombre);
        c.drawText(context.getResources().getString(R.string.art),anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*3+paintNombre.getTextSize()*4,paintApartado);
        c.drawText("Diego Figueroa González",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*3+paintNombre.getTextSize()*5,paintNombre);
        c.drawText("BoxCat Games music",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*3+paintNombre.getTextSize()*6,paintNombre);
        c.drawText("Game-icons.net",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*3+paintNombre.getTextSize()*7,paintNombre);
        c.drawText("Gameart2d.com",anchoPantalla/3,mono.getHeight()*2+5+paintApartado.getTextSize()*3+paintNombre.getTextSize()*8,paintNombre);
    }
    /**
     * Método detecta pulsación
     * @param event MotionEvent
     * @return int 0 indica escena
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        return 0;
    }
}
