package com.example.jumphop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Esta clase define las plataformas del juego y sus metodos
 * @author Diego Figueroa González
 */
public class Plataforma {
    /**
     * Imagen de plataforma
     */
    public Bitmap imagen;
    /**
     * Rectangulo utilizado para la colisión de plataforma
     */
    public Rect rectangulo;
    /**
     * Posición de la paltaforma
     */
    public PointF posicion;
    /**
     * alto de la pantalla del dispositivo
     */
    public int altoPantalla;

    /**
     * Constructor para plataformas
     * @param imagen Bitmap imagen de la plataforma
     * @param x float posición x de plataforma
     * @param y float posición y de plataforma
     * @param altoPantalla int pixeles alto de pantalla del dispositivo
     */
    public Plataforma( Bitmap imagen,float x,float y,int altoPantalla) {
        this.imagen = imagen;
        this.posicion = new PointF(x,y);
        this.altoPantalla=altoPantalla;
        this.setRectangulo();
    }
    /**
     * Método que establece el rectangulo de la plataforma
     */
    public void setRectangulo() {
        int anchoImagen=imagen.getWidth();
        float x= posicion.x;
        float y=posicion.y;
        rectangulo=new Rect((int)x,(int)y,(int)x+anchoImagen,(int)y+6);
    }
    /**
     * Método que aumenta el eje y
     * @param velocidad int sumado a sumar a posicion y
     */
    public void caerPlataforma(int velocidad){
            this.posicion.y +=velocidad;
            setRectangulo();
    }
}
