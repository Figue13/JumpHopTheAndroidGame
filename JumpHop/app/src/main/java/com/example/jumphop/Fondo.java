package com.example.jumphop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que define el fondo con scroll
 * @author Diego Figueroa González
 */
public class Fondo {
    /**
     * Imagen de fondo
     */
    public Bitmap imagen;
    /**
     * Posición inicial del fondo
     */
    public PointF posicion;
    /**
     * Alto de pantalla del dispositivo
     */
    public int altoPantalla;

    /**
     * Conctructor que inicia Jumpo
     * @param imagen Bitmap imagen de Jumpo
     * @param y float eje y
     * @param x float eje x
     * @param altoPantalla int alto pantalla del dispositivo
     */
    public Fondo(Bitmap imagen, float y,float x,int altoPantalla) {
        this.imagen=imagen;
        this.posicion= new PointF(x,y);
        this.altoPantalla=altoPantalla;
    }

    /**
     * Método que desplaza el fondo aumentando el eje Y
     * @param velocidad
     */
    public void mover(int velocidad){
        posicion.y+=velocidad;
    }

}