package com.example.jumphop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase padre que contiene los metodos ejecutadas en el Hilo de juego
 * @author Diego Figueroa González
 */
public class Escena {
    /**
     * Escena actual
     */
    int idEscena;
    /**
     * Ancho de pantalla del dispositivo
     */
    int anchoPantalla;
    /**
     * Alto de pantalla del dispositivo
     */
    int altoPantalla;
    /**
     * Contexto actual de la aplicación
     */
    Context context;
    /**
     * Conctructor que inicia las propieades bases de Escena
     * @param idEscena int escena a mostrar
     * @param context context actual
     * @param anchoPantalla int pinxeles de ancho de pantalla del dispositivo
     * @param altoPantalla int pinxeles de alto de pantalla del dispositivo
     */
    public Escena(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        this.idEscena=idEscena;
        this.anchoPantalla=anchoPantalla;
        this.altoPantalla=altoPantalla;
        this.context=context;

    }

    /**
     * Método que dibuja en el lienzo de la aplicación
     * @param c Canvas de la aplicación
     */
    public void dibuja(Canvas c){

    }

    /**
     *Método que anima la aplicación
     */
    public void actualizaFisica(){


    }

    /**
     * Método que detecta las pulsaciones sobre la superficie de la aplicación
     * @param event MotionEvent gesto de la pulsación
     * @return idEscena actual
     */
    public int onTouchEvent(MotionEvent event){

        return idEscena;
    }
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is=context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }
    public Bitmap escalaAnchura(String rutaAssets, int nuevoAncho) {
        Bitmap bitmapAux=getBitmapFromAssets(rutaAssets);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
    }
}
