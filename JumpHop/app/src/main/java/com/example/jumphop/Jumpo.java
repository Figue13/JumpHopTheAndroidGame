package com.example.jumphop;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

/**
 * Clase que define el personaje jugable y sus metodos
 */
public class Jumpo {
    /**
     * Imagen de Jumpo
     */
    public Bitmap imagen;
    /**
     * Rectagunlo para la colisión
     */
    public Rect rectangulo;
    /**
     * Posición
     */
    public PointF posicion;
    /**
     * Salto disponible
     */
    public boolean salto=true;
    /**
     * Altura actual del salto
     */
    public int alturaSalto;
    /**
     * Conctructor que inicia Jumpo
     * @param imagen Bitmap imagen de Jumpo
     * @param x float eje x
     * @param y float eje y
     */
    public Jumpo(Bitmap imagen, float x,float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x,y);
        this.alturaSalto=0;
        this.setRectangulo();
    }

    /**
     * Método que establece el rectangulo de Jumpo
     */
    public void setRectangulo() {
        int anchoImagen=imagen.getWidth();
        int altoImagen=imagen.getHeight();
        float x= posicion.x;
        float y=posicion.y+altoImagen-2;
        rectangulo=new Rect((int)x,(int)y,(int)x+anchoImagen,(int)y+1);
        Log.i("x "+(int)x,"y "+(int)y);
    }
    /**
     * Método que aumenta el eje x
     * @param ancho int limita eje x maximo
     */
    public void moverDerecha(int ancho){
        if(posicion.x+imagen.getWidth()<ancho){
            posicion.x+=imagen.getWidth()/10;
            this.setRectangulo();
        }
    }

    /**
     * Método que reduce el eje x siendo 0 el limite minimo
     */
    public void moverIzquierda(){
        if(posicion.x>0){
            posicion.x-=imagen.getWidth()/10;
            this.setRectangulo();
        }
    }

    /**
     * Método reduce eje 'y' y aumenta alturaSalto
     * @param velocidad int a utilizar
     */
    public void saltar(int velocidad){
        if(salto){
            posicion.y-=velocidad;
            alturaSalto+=velocidad;
            this.setRectangulo();
        }
    }

    /**
     * Método aumenta eje y
     * @param velocidad int a utiliazar
     */
    public void caer(int velocidad){
        posicion.y+=velocidad;
        this.setRectangulo();
    }

}
